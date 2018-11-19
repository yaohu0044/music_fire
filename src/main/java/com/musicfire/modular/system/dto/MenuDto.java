package com.musicfire.modular.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuDto implements Serializable {

    private Integer id;
    /**
     * 菜单名
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    private String index;

    private List<MenuDto> subs;

}
