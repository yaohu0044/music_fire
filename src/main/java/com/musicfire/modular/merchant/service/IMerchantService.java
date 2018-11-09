package com.musicfire.modular.merchant.service;

import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.entity.Merchant;
import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.merchant.query.MerchantPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IMerchantService extends IService<Merchant> {

    void save(Merchant merchant);

    MerchantPage list(MerchantPage merchantPage);

    void deleteByIds(List<Integer> ids);

    /**
     * 根据商家title 或者商家名字获取
     * @param merchantName 商家title 或者商家名字
     * @return 商家信息
     */
    List<MerchantDto> getAll(String merchantName);
}
