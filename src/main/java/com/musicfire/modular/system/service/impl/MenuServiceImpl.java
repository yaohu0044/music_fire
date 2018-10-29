package com.musicfire.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.modular.system.dao.MenuMapper;
import com.musicfire.modular.system.dto.MenuDto;
import com.musicfire.modular.system.entity.Menu;
import com.musicfire.modular.system.service.IMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public void save(Menu menu) {
        if (ObjectUtils.isEmpty(menu.getParentId())) {
            if (!StringUtils.isEmpty(menu.getUrl())) {
                throw new BusinessException(ErrorCode.MENU_PARENT_URL_IS_NULL);
            }
        }
        String url = menu.getUrl();
        if (StringUtils.isEmpty(url)) {
            throw new BusinessException(ErrorCode.MENU_URL_IS_NULL);
        }
        if (ObjectUtils.isEmpty(menu.getId())) {
            menuMapper.insert(menu);
        } else {
            menuMapper.updateById(menu);
        }
    }

    @Override
    public List<MenuDto> getMenuTree() {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("flag", false);
        List<Menu> menus = menuMapper.selectList(entityWrapper);
        List<MenuDto> menuDtos = new ArrayList<>();
        menus.stream().filter(menu -> menu.getParentId() == 0).forEach(menu -> {
            MenuDto dto = new MenuDto();
            dto.setIcon(menu.getIcon());
            dto.setId(menu.getId());
            dto.setIndex(menu.getUrl());
            dto.setTitle(menu.getName());
            menuDtos.add(dto);
        });
        List<Menu> menuList = menus.stream().filter(menu -> menu.getParentId() != 0).collect(Collectors.toList());
        return getMenuDtos(menuDtos, menuList);
    }

    private List<MenuDto> getMenuDtos(List<MenuDto> menuDtos, List<Menu> menus) {
        menuDtos.forEach(menuDto -> getSubs(menuDto, menus));
        return menuDtos;
    }

    private void getSubs(MenuDto menuDto, List<Menu> menus) {
        //待优化
        menus.forEach(menu -> {
            if (menuDto.getId().equals(menu.getParentId())) {
                MenuDto dto = new MenuDto();
                dto.setIcon(menu.getIcon());
                dto.setId(menu.getId());
                dto.setIndex(menu.getUrl());
                dto.setTitle(menu.getName());
                if (ObjectUtils.isEmpty(menuDto.getSubs())) {
                    menuDto.setSubs(new ArrayList<>());
                }
                menuDto.getSubs().add(dto);
                getSubs(dto, menus);
            }
        });
    }
}
