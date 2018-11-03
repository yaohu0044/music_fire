package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("sys_setting")
public class Setting extends Model<Setting> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 系统名称
     */
    @NotBlank(message = "系统名不能为空")
    private String name;
    /**
     * 系统title
     */
    @NotBlank(message = "系统title不能为空")
    private String title;
    /**
     * 系统描述
     */
    @NotBlank(message = "系统描述不能为空")
    private String describe;
    /**
     * 客户电话
     */
    @NotBlank(message = "客户电话不能为空")
    @TableField("customer_phone")
    private String customerPhone;
    /**
     * 客户QQ
     */
    @NotBlank(message = "QQ不能为空")
    @TableField("customer_qq")
    private String customerQq;
    /**
     * 版权信息
     */
    @NotBlank(message = "版权不能为空")
    private String copyright;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerQq() {
        return customerQq;
    }

    public void setCustomerQq(String customerQq) {
        this.customerQq = customerQq;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Setting{" +
        ", id=" + id +
        ", name=" + name +
        ", title=" + title +
        ", describe=" + describe +
        ", customerPhone=" + customerPhone +
        ", customerQq=" + customerQq +
        ", copyright=" + copyright +
        "}";
    }
}
