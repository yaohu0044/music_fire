package com.musicfire.modular.order.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReportParam {

    private String merchantName;

    private Date startTime;
    private Date endTime;
}
