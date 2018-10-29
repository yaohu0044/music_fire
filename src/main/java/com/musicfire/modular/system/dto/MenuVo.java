package com.musicfire.modular.system.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuVo {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;
    /**
     * 菜单名
     */
    @NotBlank(groups = {Update.class,Insert.class},message="菜单名不能为空")
    private String name;
    /**
     * 父级Id
     */
    private Integer parentId;
    /**
     * 图标
     */
    @NotBlank(groups = {Update.class,Insert.class},message="图标不能为空")
    private String icon;

    /**
     * 菜单默认资源
     */
    private String url;
}
