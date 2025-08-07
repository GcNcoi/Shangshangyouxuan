package com.atguigu.ssyx.common.utils;

/**
 * 功能描述: 雪花算法生成订单号
 *
 * @author: Gxf
 * @date: 2025年08月07日 11:47
 */
public class SnowflakeIdGenerator {
    // 开始时间戳 (2020-01-01 00:00:00)
    private final long startTime = 1577808000000L;

    // 机器ID所占的位数
    private final long workerIdBits = 5L;
    // 数据中心ID所占的位数
    private final long dataCenterIdBits = 5L;
    // 支持的最大机器ID，结果是31 (0b11111)
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 支持的最大数据中心ID，结果是31 (0b11111)
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    // 序列在ID中占的位数
    private final long sequenceBits = 12L;

    // 机器ID向左移12位
    private final long workerIdShift = sequenceBits;
    // 数据中心ID向左移17位(12+5)
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    // 时间戳向左移22位(5+5+12)
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    // 生成序列的掩码，这里为4095 (0b111111111111)
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;        // 机器ID
    private long dataCenterId;    // 数据中心ID
    private long sequence = 0L;   // 毫秒内序列(0~4095)
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    /**
     * 构造函数
     * @param workerId 机器ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId不能大于%d或小于0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId不能大于%d或小于0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成下一个ID
     * @return 雪花算法生成的ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，这是不允许的
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("系统时钟回退，拒绝生成ID。时间差: %d 毫秒", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间戳(毫秒)
     * @return 当前时间戳(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
