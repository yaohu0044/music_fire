package com.musicfire.modular.merchant.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
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
        BeanUtils.copyProperties(merchantVo, merchant);
        User user = new User();
        BeanUtils.copyProperties(merchantVo, user);

        user.setName(merchantVo.getMerchantName());
        if (null == merchant.getId()) {
            //验证登录名是否重复
            User user1 = new User();
            user1.setFlag(false);
            user1.setLoginName(merchantVo.getLoginName());
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.setEntity(user1);
            List<User> users = userService.selectList(userEntityWrapper);
            if (users.size() > 0) {
                throw new BusinessException(ErrorCode.LOGO_NAME_EXIST);
            }
            user.setPassword(Md5.generate(merchantVo.getPassword()));
            userService.insert(user);
            merchant.setUserId(user.getId());
            mapper.insert(merchant);
        } else {
            User u = userService.selectById(merchantVo.getUserId());
            if (null != u) {
                if (u.getPassword().equals(merchantVo.getPassword())) {
                    user.setPassword(u.getPassword());
                } else {
                    user.setPassword(Md5.generate(merchantVo.getPassword()));
                }
                if (!u.getLoginName().equals(merchantVo.getLoginName())) {
                    //验证登录名是否重复
                    User user1 = new User();
                    user1.setFlag(false);
                    user1.setLoginName(merchantVo.getLoginName());
                    EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
                    userEntityWrapper.setEntity(user1);
                    List<User> users = userService.selectList(userEntityWrapper);
                    if (users.size() > 0) {
                        throw new BusinessException(ErrorCode.LOGO_NAME_EXIST);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.NOT_EXIST);
            }

            user.setId(merchantVo.getUserId());
            userService.updateById(user);
            merchant.setUserId(user.getId());
            mapper.updateById(merchant);
        }
        //清楚商家原来权限
        EntityWrapper<UserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", user.getId());
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
        merchantEntityWrapper.in("id", ids);
        mapper.update(merchant, merchantEntityWrapper);

        List<Merchant> merchants = mapper.selectList(merchantEntityWrapper);
        List<Integer> userId = merchants.stream().map(Merchant::getUserId).collect(Collectors.toList());
        User user = new User();
        user.setFlag(true);
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.in("id", userId);
        userService.update(user, userEntityWrapper);


    }

    @Override
    public List<MerchantDto> getAll(String merchantName) {
        MerchantPage merchantPage = new MerchantPage();
        merchantPage.setMerchantName(merchantName);
        return mapper.merchantByPage(merchantPage);
    }

    @Override
    public List<MerchantDto> queryPageAll(MerchantPage page) {
        List<MerchantDto> dtos = mapper.merchantByPage(page);
        return dtos;
    }

    @Override
    public void allotAgent(MerchantVo merchantVo) {
//        清空原有分配
        EntityWrapper<Merchant> entityWrapper = new EntityWrapper<>();
        Wrapper<Merchant> entityWra = entityWrapper.eq("agent_id", merchantVo.getId());
        Merchant merchant = new Merchant();
        merchant.setAgentId(0);
        mapper.update(merchant, entityWra);

        if(null != merchantVo.getMerchantId() &&merchantVo.getMerchantId().size()>0){
            //重新分配
            EntityWrapper<Merchant> wrapper = new EntityWrapper<>();
            Merchant m = new Merchant();
            wrapper.in("id", merchantVo.getMerchantId());
            m.setAgentId(merchantVo.getId());
            mapper.update(m, wrapper);
        }
    }

    @Override
    public List<Merchant> undistributedBusiness() {

        return mapper.undistributedBusiness();
    }

    @Override
    public List<Merchant> agent() {
        return mapper.agent();
    }

    @Override
    public List<Merchant> queryByAgentId(Integer id) {
        return mapper.queryByAgentId(id);
    }
}
