package com.atguigu.ssyx.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 团长表
 * @TableName leader
 */
@TableName(value ="leader")
@Data
public class Leader implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 区域id
     */
    private Long regionId;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证
     */
    private String idNo;

    /**
     * 身份证图片路径
     */
    private String idNoUrl1;

    /**
     * 身份证图片路径
     */
    private String idNoUrl2;

    /**
     * 提货点名称
     */
    private String takeName;

    /**
     * 提货点类型；1->宝妈；2->便利店店主；3->快递站点；4->物业中心
     */
    private String takeType;

    /**
     * 省
     */
    private Long province;

    /**
     * 城市
     */
    private Long city;

    /**
     * 区域code
     */
    private Long district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 有无门店
     */
    private String haveStore;

    /**
     * 门店照片
     */
    private String storePath;

    /**
     * 营业时间
     */
    private String workTime;

    /**
     * 营业状态
     */
    private Integer workStatus;

    /**
     * 审核状态（0：提交审核 1：审核通过 -1：审核未通过）
     */
    private Integer checkStatus;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 审核用户
     */
    private String checkUser;

    /**
     * 审核内容
     */
    private String checkContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标记（0:不可用 1:可用）
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Leader other = (Leader) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRegionId() == null ? other.getRegionId() == null : this.getRegionId().equals(other.getRegionId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getIdNo() == null ? other.getIdNo() == null : this.getIdNo().equals(other.getIdNo()))
            && (this.getIdNoUrl1() == null ? other.getIdNoUrl1() == null : this.getIdNoUrl1().equals(other.getIdNoUrl1()))
            && (this.getIdNoUrl2() == null ? other.getIdNoUrl2() == null : this.getIdNoUrl2().equals(other.getIdNoUrl2()))
            && (this.getTakeName() == null ? other.getTakeName() == null : this.getTakeName().equals(other.getTakeName()))
            && (this.getTakeType() == null ? other.getTakeType() == null : this.getTakeType().equals(other.getTakeType()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getDetailAddress() == null ? other.getDetailAddress() == null : this.getDetailAddress().equals(other.getDetailAddress()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getHaveStore() == null ? other.getHaveStore() == null : this.getHaveStore().equals(other.getHaveStore()))
            && (this.getStorePath() == null ? other.getStorePath() == null : this.getStorePath().equals(other.getStorePath()))
            && (this.getWorkTime() == null ? other.getWorkTime() == null : this.getWorkTime().equals(other.getWorkTime()))
            && (this.getWorkStatus() == null ? other.getWorkStatus() == null : this.getWorkStatus().equals(other.getWorkStatus()))
            && (this.getCheckStatus() == null ? other.getCheckStatus() == null : this.getCheckStatus().equals(other.getCheckStatus()))
            && (this.getCheckTime() == null ? other.getCheckTime() == null : this.getCheckTime().equals(other.getCheckTime()))
            && (this.getCheckUser() == null ? other.getCheckUser() == null : this.getCheckUser().equals(other.getCheckUser()))
            && (this.getCheckContent() == null ? other.getCheckContent() == null : this.getCheckContent().equals(other.getCheckContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRegionId() == null) ? 0 : getRegionId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getIdNo() == null) ? 0 : getIdNo().hashCode());
        result = prime * result + ((getIdNoUrl1() == null) ? 0 : getIdNoUrl1().hashCode());
        result = prime * result + ((getIdNoUrl2() == null) ? 0 : getIdNoUrl2().hashCode());
        result = prime * result + ((getTakeName() == null) ? 0 : getTakeName().hashCode());
        result = prime * result + ((getTakeType() == null) ? 0 : getTakeType().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getDetailAddress() == null) ? 0 : getDetailAddress().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getHaveStore() == null) ? 0 : getHaveStore().hashCode());
        result = prime * result + ((getStorePath() == null) ? 0 : getStorePath().hashCode());
        result = prime * result + ((getWorkTime() == null) ? 0 : getWorkTime().hashCode());
        result = prime * result + ((getWorkStatus() == null) ? 0 : getWorkStatus().hashCode());
        result = prime * result + ((getCheckStatus() == null) ? 0 : getCheckStatus().hashCode());
        result = prime * result + ((getCheckTime() == null) ? 0 : getCheckTime().hashCode());
        result = prime * result + ((getCheckUser() == null) ? 0 : getCheckUser().hashCode());
        result = prime * result + ((getCheckContent() == null) ? 0 : getCheckContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", regionId=").append(regionId);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", idNo=").append(idNo);
        sb.append(", idNoUrl1=").append(idNoUrl1);
        sb.append(", idNoUrl2=").append(idNoUrl2);
        sb.append(", takeName=").append(takeName);
        sb.append(", takeType=").append(takeType);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", detailAddress=").append(detailAddress);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", haveStore=").append(haveStore);
        sb.append(", storePath=").append(storePath);
        sb.append(", workTime=").append(workTime);
        sb.append(", workStatus=").append(workStatus);
        sb.append(", checkStatus=").append(checkStatus);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkUser=").append(checkUser);
        sb.append(", checkContent=").append(checkContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}