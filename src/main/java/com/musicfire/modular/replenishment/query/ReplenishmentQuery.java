package com.musicfire.modular.replenishment.query;

import com.musicfire.modular.commodity.query.CommodityPage;
import lombok.Data;

@Data
public class ReplenishmentQuery extends CommodityPage {

    //缺货表Id 针对.商家补货
    private Integer replenishmentId;

    /**
     * 商品名称
     */
    private String commodityName;
}
