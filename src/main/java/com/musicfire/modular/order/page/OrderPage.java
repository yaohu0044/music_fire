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
}
