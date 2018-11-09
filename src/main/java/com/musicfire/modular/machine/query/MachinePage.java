package com.musicfire.modular.machine.query;

import com.musicfire.common.utiles.BasePage;
import lombok.Data;

@Data
public class MachinePage extends BasePage {
    private String name;
    private String code;
    private String merchantName;
}
