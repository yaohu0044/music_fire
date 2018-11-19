package com.musicfire.modular.login.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Md5;
import com.musicfire.modular.login.Login;
import com.musicfire.modular.login.service.LoginService;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.service.IMerchantService;
import com.musicfire.modular.system.dto.MenuDto;
import com.musicfire.modular.system.entity.Role;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.entity.UserRole;
import com.musicfire.modular.system.service.IMenuService;
import com.musicfire.modular.system.service.IRoleService;
import com.musicfire.modular.system.service.IUserRoleService;
import com.musicfire.modular.system.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private IUserService userService;

    @Resource
    private IMenuService menuService;

    @Resource
    private RedisDao redisDao;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IMerchantService merchantService;

    @Override
    public Login verifyLogin(String loginName, String password) {
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("login_name", loginName);
            User user = userService.selectOne(userEntityWrapper);
            if(null == user){
                throw new BusinessException(ErrorCode.LOGIN_NAME_OR_PASSWORD_ERR);
            }
            boolean verify = Md5.verify(password, user.getPassword());
            if (verify) {
                //登录验证通过. 验证其权限
                EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<>();
                userRoleEntityWrapper.eq("user_id", user.getId());
                List<UserRole> userRoles = userRoleService.selectList(userRoleEntityWrapper);
                List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

                List<MenuDto> menuDtos = menuService.queryMenDtoByRoles(roleIds);
                //获取角色名称,
                EntityWrapper<Role> roleEntityWrapper = new EntityWrapper<>();
                roleEntityWrapper.in("id",roleIds);
                List<Role> roles = roleService.selectList(roleEntityWrapper);
                String roleNames = String.join(",", roles.stream().map(Role::getName).collect(Collectors.toList()));

                Login login = new Login();
                login.setUserName(loginName);
                login.setMenuDTos(menuDtos);
                login.setRoles(roleNames);
                //如果是商家,则角色只能是一个.返回商家的title
                if(roles.get(0).getId()==3){
                    EntityWrapper<Merchant> entityWrapper = new EntityWrapper<>();
                    entityWrapper.eq("user_id",user.getId());
                    Merchant merchant = merchantService.selectOne(entityWrapper);
                    login.setTitle(merchant.getTitle());
                    login.setMerchantId(merchant.getId());
                }

                return login;
            }else{
                throw new BusinessException(ErrorCode.LOGIN_NAME_OR_PASSWORD_ERR);
        }
    }
}
