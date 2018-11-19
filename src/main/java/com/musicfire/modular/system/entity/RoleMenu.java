package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("sys_role_menu")
@Data
public class RoleMenu{

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId("role_id")
    private Integer roleId;
    /**
     * 菜单Id
     */
    @TableField("menu_id")
    private Integer menuId;

}
