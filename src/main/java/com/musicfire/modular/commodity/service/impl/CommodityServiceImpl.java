package com.musicfire.modular.commodity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.utiles.ObjUtil;
import com.musicfire.modular.commodity.dao.CommodityMapper;
import com.musicfire.modular.commodity.dao.CommodityPicMapper;
import com.musicfire.modular.commodity.dao.CommodityStockMapper;
import com.musicfire.modular.commodity.entity.Commodity;
import com.musicfire.modular.commodity.entity.CommodityPic;
import com.musicfire.modular.commodity.entity.CommodityStock;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.room.query.RoomPage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private CommodityPicMapper commodityPicMapper;

    @Resource
    private CommodityStockMapper commodityStockMapper;

    @Transactional
    @Override
    public void save(CommodityDto commodityDto) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityDto, commodity);
        commodityMapper.insert(commodity);
        if (ObjectUtils.isEmpty(commodityDto.getPath())) {
            CommodityPic commodityPic = new CommodityPic();
            BeanUtils.copyProperties(commodityDto, commodityPic);
            commodityPic.setCommodityId(commodity.getId());
            commodityPicMapper.insert(commodityPic);
        }
        if (commodityDto.getPrice() != null) {
            CommodityStock commodityStock = new CommodityStock();
            BeanUtils.copyProperties(commodityDto, commodityStock);
            commodityStock.setCommodityId(commodity.getId());
            commodityStockMapper.insert(commodityStock);
        }
    }

    @Override
    public void edit(CommodityDto commodityDto) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityDto, commodity);
        commodityMapper.updateById(commodity);
        if (ObjectUtils.isEmpty(commodityDto.getPath())) {
            CommodityPic commodityPic = new CommodityPic();
            BeanUtils.copyProperties(commodityDto, commodityPic);
            commodityPicMapper.updateById(commodityPic);
        }
        if (commodityDto.getPrice() != null) {
            CommodityStock commodityStock = new CommodityStock();
            BeanUtils.copyProperties(commodityDto, commodityStock);
            commodityStockMapper.updateById(commodityStock);
        }
    }

    @Transactional
    @Override
    public int commDeleteBatch(String ids) {
        commodityMapper.deleteBatchIds(ObjUtil.listLong(ids));
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.in("commodity_id", ObjUtil.listLong(ids));
        commodityPicMapper.delete(entityWrapper);
        Integer delete = commodityStockMapper.delete(entityWrapper);
        return delete;
    }

    @Override
    public List<CommodityDto> queryList(RoomPage page) {
        List<CommodityDto> list = new ArrayList<>();
        CommodityDto commodityDto = new CommodityDto();
        List<Commodity> commodities = commodityMapper.queryByCommodity(page);
        if (commodities.size() != 0) {
            for (Commodity commodity : commodities) {
                BeanUtils.copyProperties(commodity, commodityDto);
                list.add(commodityDto);
                HashMap<String, Object> map = new HashMap<>();
                map.put("commodity_id", commodity.getId());
                List<CommodityPic> picList = commodityPicMapper.selectByMap(map);
                for (CommodityPic commodityPic : picList) {
                    BeanUtils.copyProperties(commodityPic, commodityDto);
                }
                List<CommodityStock> stockList = commodityStockMapper.selectByMap(map);
                for (CommodityStock commodityStock : stockList) {
                    BeanUtils.copyProperties(commodityStock, commodityDto);
                }

            }
        }
        return list;
    }

    @Override
    public List<CommodityDto> queryCommodityByName(String name) {
        List<CommodityDto> list1 = new ArrayList<>();
        CommodityDto commodityDto = new CommodityDto();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Commodity> list = commodityMapper.selectByMap(map);
        if (ObjectUtils.isEmpty(list.size())) {
            for (Commodity commodity : list) {
                BeanUtils.copyProperties(commodity, commodityDto);
                HashMap<String, Object> picMap = new HashMap<>();
                picMap.put("commodity_id", commodity.getId());
                List<CommodityPic> picList = commodityPicMapper.selectByMap(picMap);
                for (CommodityPic commodityPic : picList) {
                    BeanUtils.copyProperties(commodityPic, commodityDto);
                }
                List<CommodityStock> stock = commodityStockMapper.selectByMap(picMap);
                for (CommodityStock commodityStock : stock) {
                    BeanUtils.copyProperties(commodityStock, commodityDto);
                }
                list1.add(commodityDto);
            }
        }
        return list1;
    }
}
