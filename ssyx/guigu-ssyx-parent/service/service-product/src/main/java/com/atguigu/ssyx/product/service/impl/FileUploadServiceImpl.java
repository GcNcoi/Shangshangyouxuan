package com.atguigu.ssyx.product.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ssyx.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月23日 11:19
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${aliyun.endpoint}")
    private String endPoint;

    @Value("${aliyun.accessid}")
    private String accessKey;

    @Value("${aliyun.accesssecret}")
    private String secretKey;

    @Value("${aliyun.bucketname}")
    private String bucketName;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, secretKey);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            //生成随机唯一值，使用uuid，添加到文件名称里面
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;
            //按照当前日期，创建文件夹，上传到创建文件夹里面
            // 2025/07/23/01.jpg
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = timeUrl + "/" + fileName;
            // 调用方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 上传之后文件路径
            // https://ssyx-atguigu.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            // 返回路径
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
