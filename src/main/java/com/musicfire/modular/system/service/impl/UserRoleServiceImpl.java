package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.system.dao.UserRoleMapper;
import com.musicfire.modular.system.entity.UserRole;
import com.musicfire.modular.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;


    @Override
    public void insertAll(Integer id, List<Integer> roles) {
        userRoleMapper.insertAll(id,roles);
    }
}
