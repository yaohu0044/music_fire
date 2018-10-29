package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("sys_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 父级Id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    /**
     * 菜单默认资源
     */
    private String url;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建者Id
     */
    @TableField("create_id")
    private Integer createId;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改者Id
     */
    @TableField("update_id")
    private Integer updateId;


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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Menu{" +
        ", id=" + id +
        ", name=" + name +
        ", parentId=" + parentId +
        ", icon=" + icon +
        ", flag=" + flag +
        ", createTime=" + createTime +
        ", createId=" + createId +
        ", updateTime=" + updateTime +
        ", updateId=" + updateId +
        "}";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
