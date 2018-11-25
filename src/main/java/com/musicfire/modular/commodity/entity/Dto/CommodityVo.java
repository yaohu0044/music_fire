package com.musicfire.modular.commodity.entity.Dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.commodity.entity.Commodity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommodityVo extends Commodity {

    @NotBlank(message = "商品id",groups = Update.class)
    private Integer id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名不能为空",groups = {Insert.class,Update.class})
    private String name;

    /**
     * 描述
     */
    @NotBlank(message = "商品描述不能为空",groups = {Insert.class,Update.class})
    private String describe;
    /**
     * 简介
     */
    private String introduce;
    /**
     * 进价
     */
    @NotBlank(message = "商品进价",groups = {Insert.class,Update.class})
    private BigDecimal purchasePrice;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    private Date createTime;
    private Date updateTime;
    /**
     * 图片路径
     */
    @NotBlank(message = "图片路径不能为空",groups = {Insert.class,Update.class})
    private String path;
    /**
     * 库存量
     */
    @NotBlank(message = "商品库存量不能为空",groups = {Insert.class,Update.class})
    private Integer num;
    /**
     * 价格
     */
    @NotBlank(message = "商品售价",groups = {Insert.class,Update.class})
    private BigDecimal price;
}
