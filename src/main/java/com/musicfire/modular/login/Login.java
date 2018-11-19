package com.musicfire.modular.login;

import com.musicfire.modular.system.dto.MenuDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Login implements Serializable {

    private String userName;
    private String password;
    private List<MenuDto>menuDTos;
    private String roles;
    //如果是是商家则返回商家的title
    private String title;
    private Integer merchantId;
}
