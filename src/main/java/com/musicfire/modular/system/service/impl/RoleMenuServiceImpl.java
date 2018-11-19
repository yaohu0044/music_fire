package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.system.dao.RoleMenuMapper;
import com.musicfire.modular.system.dto.RoleMenuDto;
import com.musicfire.modular.system.entity.RoleMenu;
import com.musicfire.modular.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    private RoleMenuMapper mapper;

    @Override
    public RoleMenuDto queryByRoleId(Integer roleId) {
        EntityWrapper<RoleMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id",roleId);
        List<RoleMenu> roleMenus = mapper.selectList(wrapper);
        List<Integer> menuIds = new ArrayList<>();
        roleMenus.forEach(m ->
            menuIds.add(m.getMenuId()));
        RoleMenuDto dto = new RoleMenuDto();
        dto.setRoleId(roleId);
        dto.setMenuId(menuIds);
        return dto;
    }

    @Transactional
    @Override
    public void save(RoleMenuDto dto) {
        EntityWrapper<RoleMenu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_id",dto.getRoleId());
        mapper.delete(entityWrapper);

        List<RoleMenu> roleMenus = new ArrayList<>();
        List<Integer> menuIds = dto.getMenuId();

        menuIds.forEach(menuId->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(dto.getRoleId());
            roleMenus.add(roleMenu);
        });
        mapper.insertAll(roleMenus);
    }

    @Override
    public void insertList(List<RoleMenu> roleMenus) {
        mapper.insertAll(roleMenus);
    }
}
