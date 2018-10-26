package com.musicfire.modular.commodity.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.inphase.edu.utils.C;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.dao.CommodityPicMapper;
import com.musicfire.modular.commodity.dao.CommodityStockMapper;
import com.musicfire.modular.commodity.entity.Commodity;
import com.musicfire.modular.commodity.dao.CommodityMapper;
import com.musicfire.modular.commodity.entity.CommodityPic;
import com.musicfire.modular.commodity.entity.CommodityStock;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jxl.common.BaseUnit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {
    @Resource
    CommodityMapper commodityMapper;
    @Resource
    CommodityPicMapper commodityPicMapper;
    @Resource
    CommodityStockMapper commodityStockMapper;
    @Override
    public String save(CommodityDto commodityDto) {
        Commodity commodity=new Commodity();
        BeanUtils.copyProperties(commodityDto,commodity);
        commodityMapper.insert(commodity);
        if (commodityDto.getPath() !=null && commodityDto.getPath()!=""){
            CommodityPic commodityPic=new CommodityPic();
            BeanUtils.copyProperties(commodityDto,commodityPic);
            commodityPic.setCommodityId(commodity.getId());
            commodityPicMapper.insert(commodityPic);
        }
        if (commodityDto.getPrice() !=null){
            CommodityStock commodityStock=new CommodityStock();
            BeanUtils.copyProperties(commodityDto,commodityStock);
            commodityStock.setCommodityId(commodity.getId());
            commodityStockMapper.insert(commodityStock);
        }
        return  "成功";
    }

    @Override
    public String edit(CommodityDto commodityDto) {
        Commodity commodity=new Commodity();
        BeanUtils.copyProperties(commodityDto,commodity);
        commodityMapper.updateById(commodity);
        if (commodityDto.getPath() !=null && commodityDto.getPath()!=""){
            CommodityPic commodityPic=new CommodityPic();
            BeanUtils.copyProperties(commodityDto,commodityPic);
            commodityPicMapper.updateById(commodityPic);
        }
        if (commodityDto.getPrice() !=null){
            CommodityStock commodityStock=new CommodityStock();
            BeanUtils.copyProperties(commodityDto,commodityStock);
            commodityStockMapper.updateById(commodityStock);
        }
        return  "成功";
    }

    @Override
    public int commDeleteBatch(String ids) {
        commodityMapper.deleteBatchIds(C.listLong(ids));
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.in("commodityId",C.listLong(ids));
        commodityPicMapper.delete(entityWrapper);
        Integer delete = commodityStockMapper.delete(entityWrapper);
        return delete;
    }
}
