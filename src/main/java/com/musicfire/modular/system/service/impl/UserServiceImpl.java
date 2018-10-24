package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.system.dao.UserMapper;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
