package com.musicfire.modular.system.dto;

import com.musicfire.modular.system.entity.Role;
import com.musicfire.modular.system.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto extends User {
    List<Integer> role;
}
