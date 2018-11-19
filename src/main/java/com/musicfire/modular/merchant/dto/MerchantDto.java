package com.musicfire.modular.merchant.dto;

import com.musicfire.modular.merchant.entity.Merchant;
import lombok.Data;

@Data
public class MerchantDto extends Merchant {

    private String merchantName;

    private String phone;
    private String loginName;
    private String password;

}
