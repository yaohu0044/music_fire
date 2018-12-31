package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.modular.system.dao.MenuMapper;
import com.musicfire.modular.system.dao.RoleMapper;
import com.musicfire.modular.system.dto.RoleDTo;
import com.musicfire.modular.system.entity.Menu;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper mapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IRoleMenuService roleMenuService;

    @Transactional
    @Override
    public void save(Role role,List<Integer> menuIds) {
        if(ObjectUtils.isEmpty(role.getId())){
            if(!StringUtils.isEmpty(queryByRoleName(role.getName()))){
                throw new BusinessException(ErrorCode.ROLE_NAME_REPEAT);
            }
            mapper.insert(role);
            List<RoleMenu> roleMenus = new ArrayList<>();
            addRoleMenu(role, menuIds, roleMenus);
            roleMenuService.insertList(roleMenus);
        }else{
            mapper.updateById(role);
            //清空原来角色和菜单
            EntityWrapper<RoleMenu> roleMenuEntityWrapper = new EntityWrapper<>();
            roleMenuEntityWrapper.eq("role_id",role.getId());
            roleMenuService.delete(roleMenuEntityWrapper);
            //重新添加角色
            List<RoleMenu> roleMenus = new ArrayList<>();
            addRoleMenu(role, menuIds, roleMenus);
            roleMenuService.insertList(roleMenus);
        }

    }

    private void addRoleMenu(Role role, List<Integer> menuIds, List<RoleMenu> roleMenus) {
        menuIds.forEach(menuId->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        });
    }

    @Override
    public RolePage list(RolePage rolePage) {
        Integer count = mapper.countByPage(rolePage);
        if(count<1){
            return rolePage;
        }
        List<RoleDTo> roles = mapper.queryByPage(rolePage);
        roles.forEach(r->{
            EntityWrapper<RoleMenu> roleMenuEntityWrapper = new EntityWrapper<>();
            roleMenuEntityWrapper.eq("role_id",r.getId());
            r.setMenuIds(roleMenuService.selectList(roleMenuEntityWrapper).stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
            EntityWrapper<Menu> menuEntityWrapper = new EntityWrapper<>();
            menuEntityWrapper.in("id",r.getMenuIds());
            r.setMenuNames(menuMapper.selectList(menuEntityWrapper).stream().map(Menu::getName).collect(Collectors.toList()));
        });
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
