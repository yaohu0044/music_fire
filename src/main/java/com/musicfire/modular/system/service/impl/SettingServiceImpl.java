package com.musicfire.modular.system.service.impl;

import com.musicfire.modular.system.entity.Setting;
import com.musicfire.modular.system.dao.SettingMapper;
import com.musicfire.modular.system.service.ISettingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

    @Resource
    private SettingMapper mapper;
    @Override
    public void save(Setting setting) {
        if(ObjectUtils.isEmpty(setting.getId())){
            mapper.insert(setting);
        }else {
            mapper.updateById(setting);
        }
    }
}
