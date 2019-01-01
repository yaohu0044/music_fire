package com.musicfire.modular.machine.query;

import com.musicfire.common.utiles.BasePage;
import lombok.Data;

import java.util.Date;

@Data
public class MachinePage extends BasePage {
    private String name;
    private String code;
    private String merchantName;
    private Boolean allocate; //true : 分配，false：没分配

    private Date startTime;
    private Date endTime;
}
