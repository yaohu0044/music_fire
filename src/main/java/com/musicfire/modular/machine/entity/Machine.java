package com.musicfire.modular.machine.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("machine")
public class Machine extends Model<Machine> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 机器名
     */
    private String name;
    /**
     * 机器code
     */
    private String code;
    /**
     * 经纬度
     */
    @TableField("lon_and_lat")
    private String lonAndLat;
    /**
     * 机器状态
     */
    private Integer state;
    /**
     * 商家id
     */
    @TableField("merchant_id")
    private Integer merchantId;
    private String address;
    @TableField("qr_code_url")
    private String qrCodeUrl;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_id")
    private Integer createId;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_ie")
    private Date updateIe;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLonAndLat() {
        return lonAndLat;
    }

    public void setLonAndLat(String lonAndLat) {
        this.lonAndLat = lonAndLat;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateIe() {
        return updateIe;
    }

    public void setUpdateIe(Date updateIe) {
        this.updateIe = updateIe;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Machine{" +
        ", id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", lonAndLat=" + lonAndLat +
        ", state=" + state +
        ", merchantId=" + merchantId +
        ", address=" + address +
        ", qrCodeUrl=" + qrCodeUrl +
        ", flag=" + flag +
        ", createTime=" + createTime +
        ", createId=" + createId +
        ", updateTime=" + updateTime +
        ", updateIe=" + updateIe +
        "}";
    }
}
