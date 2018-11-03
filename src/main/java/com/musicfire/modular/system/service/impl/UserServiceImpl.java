package com.musicfire.modular.system.service.impl;

import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.dao.UserMapper;
import com.musicfire.modular.system.query.UserPage;
import com.musicfire.modular.system.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.org.apache.xml.internal.security.signature.ObjectContainer;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper mapper;
    @Override
    public void save(User user) {
        if(ObjectUtils.isEmpty(user.getId())){
            mapper.insert(user);
        }else {
            mapper.updateById(user);
        }
    }

    @Override
    public UserPage list(UserPage userPage) {

        return null;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {

    }

    @Override
    public User queryByUserName(String name) {
        return null;
    }
}
