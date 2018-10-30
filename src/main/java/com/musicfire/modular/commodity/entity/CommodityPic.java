package com.musicfire.modular.commodity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("commodity_pic")
public class CommodityPic extends Model<CommodityPic> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 商品Id
     */
    @TableField("commodity_id")
    private Integer commodityId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CommodityPic{" +
        ", id=" + id +
        ", path=" + path +
        ", commodityId=" + commodityId +
        "}";
    }
}
