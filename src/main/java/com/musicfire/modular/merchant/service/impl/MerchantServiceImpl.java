package com.musicfire.modular.merchant.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.utiles.Md5;
import com.musicfire.modular.merchant.dao.MerchantMapper;
import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.dto.MerchantVo;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.query.MerchantPage;
import com.musicfire.modular.merchant.service.IMerchantService;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.entity.UserRole;
import com.musicfire.modular.system.service.IUserRoleService;
import com.musicfire.modular.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private IUserService userService;

    @Resource
    private IUserRoleService userRoleService;


    @Transactional
    @Override
    public void save(MerchantVo merchantVo) {
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantVo,merchant);
        User user = new User();
        BeanUtils.copyProperties(merchantVo,user);
        user.setPassword(Md5.generate(merchantVo.getPassword()));
        user.setName(merchantVo.getMerchantName());
        if (ObjectUtils.isEmpty(merchant.getId())) {
            userService.insert(user);
            merchant.setUserId(user.getId());
            mapper.insert(merchant);
        } else {
            user.setId(merchantVo.getUserId());
            userService.updateById(user);
            merchant.setUserId(user.getId());
            mapper.updateById(merchant);
        }
        //清楚商家原来权限
        EntityWrapper<UserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id",user.getId());
        userRoleService.delete(entityWrapper);
        //给商家分权限为商户
        UserRole userRole = new UserRole();
        //3表示商户禁止改动（后续考虑做成数据字典）
        userRole.setRoleId(3);
        userRole.setUserId(user.getId());
        userRoleService.insert(userRole);
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

    @Transactional
    @Override
    public void deleteByIds(List<Integer> ids) {
        Merchant merchant = new Merchant();
        merchant.setFlag(true);
        EntityWrapper<Merchant> merchantEntityWrapper = new EntityWrapper<>();
        merchantEntityWrapper.in("id",ids);
        mapper.update(merchant,merchantEntityWrapper);

        List<Merchant> merchants = mapper.selectList(merchantEntityWrapper);
        List<Integer> userId = merchants.stream().map(Merchant::getUserId).collect(Collectors.toList());
        User user = new User();
        user.setFlag(true);
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.in("id",userId);
        userService.update(user,userEntityWrapper);


    }

    @Override
    public List<MerchantDto> getAll(String merchantName) {
        MerchantPage merchantPage = new MerchantPage();
        merchantPage.setMerchantName(merchantName);
        return mapper.merchantByPage(merchantPage);
    }
}
