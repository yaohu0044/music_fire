package com.musicfire.modular.merchant.dao;

import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.entity.Merchant;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.merchant.query.MerchantPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {

    Integer countByPage(MerchantPage merchantPage);

    List<MerchantDto> merchantByPage(MerchantPage merchantPage);

    List<Merchant> undistributedBusiness();

    List<Merchant> agent();

    List<Merchant> queryByAgentId(Integer id);
}
