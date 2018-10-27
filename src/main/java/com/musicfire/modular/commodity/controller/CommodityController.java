package com.musicfire.modular.commodity.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityPicService;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.commodity.service.impl.CommodityPicServiceImpl;
import com.musicfire.modular.room.query.RoomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

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
    public Result commSave(@RequestBody CommodityDto commodityDto){
        commodityService.save(commodityDto);
       return new Result().ok();
    }
    public Result commedit(@RequestBody CommodityDto commodityDto){
        commodityService.edit(commodityDto);
        return new Result().ok();
    }
    @GetMapping("/delete")
    public Result commDeleteBatch(String ids){
        commodityService.commDeleteBatch(ids);
        return new Result().ok();
    }
    @GetMapping("/list")
    public List<CommodityDto> List(RoomPage page){
        List<CommodityDto> list = commodityService.queryList(page);
        return list;
    }
}

