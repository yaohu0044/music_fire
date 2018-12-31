package com.musicfire.modular.system.dto;

import com.musicfire.modular.system.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTo extends Role {

    private List<Integer> menuIds;
    private List<String>  menuNames;
}
