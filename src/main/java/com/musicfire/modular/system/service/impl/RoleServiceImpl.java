package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.modular.system.dao.RoleMapper;
import com.musicfire.modular.system.entity.Role;
import com.musicfire.modular.system.entity.RoleMenu;
import com.musicfire.modular.system.query.RolePage;
import com.musicfire.modular.system.service.IRoleMenuService;
import com.musicfire.modular.system.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper mapper;

    @Resource
    private IRoleMenuService roleMenuService;

    @Transactional
    @Override
    public void save(Role role,List<Integer> menuIds) {
        if(!StringUtils.isEmpty(queryByRoleName(role.getName()))){
            throw new BusinessException(ErrorCode.ROLE_NAME_REPEAT);
        }
        if(ObjectUtils.isEmpty(role.getId())){
            mapper.insert(role);
        }else{
            mapper.updateById(role);
        }
        List<RoleMenu> roleMenus = new ArrayList<>();
        menuIds.forEach(menuId->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        });

        roleMenuService.insertOrUpdateBatch(roleMenus);
    }

    @Override
    public RolePage list(RolePage rolePage) {
        Integer count = mapper.countByPage(rolePage);
        if(count<1){
            return rolePage;
        }
        List<Role> roles = mapper.queryByPage(rolePage);
        rolePage.setList(roles);
        rolePage.setTotalCount(count);
        return rolePage;
    }

    @Override
    public void deleteByIds(List<Integer> id) {
        Role role = new Role();
        role.setFlag(true);
        EntityWrapper<Role> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id",id);
        mapper.update(role,entityWrapper);
    }

    @Override
    public Role queryByRoleName(String name) {
        Role role = new Role();
        role.setName(name);
        return mapper.selectOne(role);
    }
}
