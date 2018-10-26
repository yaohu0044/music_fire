package com.musicfire.modular.commodity.controller;


import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityPicService;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.commodity.service.impl.CommodityPicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    ICommodityService commodityService;
    @PostMapping("/commSave")
    public String commSave(@RequestBody CommodityDto commodityDto){
        commodityService.save(commodityDto);
       return "添加成功";
    }
    public String commedit(@RequestBody CommodityDto commodityDto){
        commodityService.edit(commodityDto);
        return "修改成功";
    }

    public String commDeleteBatch(@PathVariable String ids){
        commodityService.commDeleteBatch(ids);
        return "删除成功";
    }

}

