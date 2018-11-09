package com.musicfire.modular.merchant.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.utiles.Md5;
import com.musicfire.modular.merchant.dao.MerchantMapper;
import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.query.MerchantPage;
import com.musicfire.modular.merchant.service.IMerchantService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {

    @Resource
    private MerchantMapper mapper;

    @Override
    public void save(Merchant merchant) {
        merchant.setPassword(Md5.EncoderByMd5(merchant.getPassword()));
        if (ObjectUtils.isEmpty(merchant.getId())) {
            mapper.insert(merchant);
        } else {
            mapper.updateById(merchant);
        }
    }

    @Override
    public MerchantPage list(MerchantPage merchantPage) {
        Integer count = mapper.countByPage(merchantPage);
        if (count < 1) {
            return merchantPage;
        }
        List<MerchantDto> page = mapper.merchantByPage(merchantPage);
        merchantPage.setList(page);
        merchantPage.setTotalCount(count);
        return merchantPage;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        Merchant merchant = new Merchant();
        merchant.setFlag(true);
        EntityWrapper<Merchant> merchantEntityWrapper = new EntityWrapper<>();
        merchantEntityWrapper.in("id",ids);
        mapper.update(merchant,merchantEntityWrapper);
    }

    @Override
    public List<MerchantDto> getAll(String merchantName) {
        MerchantPage merchantPage = new MerchantPage();
        merchantPage.setMerchantName(merchantName);
        return mapper.merchantByPage(merchantPage);
    }
}
