package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.utiles.Md5;
import com.musicfire.modular.system.dao.UserMapper;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.entity.UserRole;
import com.musicfire.modular.system.query.UserPage;
import com.musicfire.modular.system.service.IUserRoleService;
import com.musicfire.modular.system.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private IUserRoleService roleService;

    @Transactional
    @Override
    public void save(User user,List<Integer> roles) {
        user.setPassword(Md5.generate(user.getPassword()));
        if (ObjectUtils.isEmpty(user.getId())) {
            mapper.insert(user);
            roleService.insertAll(user.getId(),roles);
        } else {
            mapper.updateById(user);
            EntityWrapper<UserRole> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("user_id",user.getId());
            roleService.delete(entityWrapper);
            roleService.insertAll(user.getId(),roles);
        }
    }

    @Override
    public UserPage list(UserPage userPage) {
        Integer count = mapper.countByPage(userPage);
        if (count < 1) {
            return userPage;
        }
        List<User> page = mapper.userByPage(userPage);
        userPage.setList(page);
        userPage.setTotalCount(count);
        return userPage;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        User user = new User();
        user.setFlag(true);
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.in("id",ids);
        mapper.update(user,userEntityWrapper);
    }

    @Override
    public List<User> queryByUserName(String name) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("flag", false);
        entityWrapper.like("name", name);
        return mapper.selectList(entityWrapper);
    }

    @Override
    public List<User> queryUserByName(String name) {

        return  mapper.queryUserByName(name);
    }
}
