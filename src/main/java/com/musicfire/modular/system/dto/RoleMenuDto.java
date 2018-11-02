package com.musicfire.modular.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuDto {

    private Integer roleId;
    private List<Integer> menuId;
}
