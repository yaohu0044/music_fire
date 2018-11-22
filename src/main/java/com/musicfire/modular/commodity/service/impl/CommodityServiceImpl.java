package com.musicfire.modular.commodity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.modular.commodity.dao.CommodityMapper;
import com.musicfire.modular.commodity.dao.CommodityPicMapper;
import com.musicfire.modular.commodity.dao.CommodityStockMapper;
import com.musicfire.modular.commodity.entity.Commodity;
import com.musicfire.modular.commodity.entity.CommodityPic;
import com.musicfire.modular.commodity.entity.CommodityStock;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.entity.Dto.CommodityVo;
import com.musicfire.modular.commodity.query.CommodityPage;
import com.musicfire.modular.commodity.service.ICommodityService;
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
    public void save(CommodityVo commodityVo) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityVo, commodity);
        commodityMapper.insert(commodity);

        if (commodityVo.getPrice() != null) {
            CommodityStock commodityStock = new CommodityStock();
            BeanUtils.copyProperties(commodityVo, commodityStock);
            commodityStock.setCommodityId(commodity.getId());
            commodityStockMapper.insert(commodityStock);
        }
    }

    @Override
    public void edit(CommodityVo commodityVo) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityVo, commodity);
        commodityMapper.updateById(commodity);
        if (commodityVo.getPrice() != null) {
            CommodityStock commodityStock = new CommodityStock();
            BeanUtils.copyProperties(commodityVo, commodityStock);
            commodityStockMapper.updateById(commodityStock);
        }
    }

    @Transactional
    @Override
    public void commDeleteBatch(List<Integer> ids) {
        EntityWrapper<Commodity> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id",ids);
        Commodity commodity = new Commodity();
        commodity.setFlag(true);
        commodityMapper.update(commodity,entityWrapper);
        EntityWrapper<CommodityPic> entityWrapperPic = new EntityWrapper<>();
        entityWrapper.in("commodity_id", ids);
        commodityPicMapper.delete(entityWrapperPic);
        EntityWrapper<CommodityStock> entityWrapperStock = new EntityWrapper<>();
        commodityStockMapper.delete(entityWrapperStock);
    }

    @Override
    public CommodityPage queryList(CommodityPage page) {
        Integer count = commodityMapper.queryCount(page);
        if(ObjectUtils.isEmpty(count)){
            throw new BusinessException(ErrorCode.IS_NOT_DATA);
        }
        List<Commodity> commodities = commodityMapper.queryByCommodity(page);
        page.setTotalCount(count);
        page.setList(commodities);
        return page;
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

    @Override
    public List<CommodityDto> queryByIds(List<Integer> commodityId) {

        return  commodityMapper.queryByIds(commodityId);
    }
}
