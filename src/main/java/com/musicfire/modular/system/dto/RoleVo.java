package com.musicfire.modular.system.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleVo {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;
    /**
     * 角色名称
     */
    @NotBlank(groups = {Update.class,Insert.class},message="角色名不能为空")
    private String name;
    /**
     * 描述
     */
    private String describe;

    /**
     * 菜单Id
     */
    @NotEmpty(groups = {Update.class,Insert.class},message="权限名不能为空")
    private List<Integer> menuIds;
}
