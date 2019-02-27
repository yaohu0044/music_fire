package com.musicfire.modular.order.page;

import com.musicfire.common.utiles.BasePage;
import lombok.Data;

@Data
public class OrderPage extends BasePage {
    private String merchantName;
    private String paymentMethod ;
    private String endTime;
    private String startTime;
    private Integer userId;
    private Integer merchantId;
    private Integer state;
    //是否是代理 true是 false 不是
    private boolean isAgents = false;
    //代理Id
    private Integer agentsId;
}
