package com.musicfire.modular.commodity.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.room.query.RoomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;

    @PostMapping("/commSave")
    public Result commSave(@Validated CommodityDto commodityDto) {
        commodityService.save(commodityDto);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CommodityDto commodityDto) {
        commodityService.edit(commodityDto);
        return new Result().ok();
    }

    @GetMapping("/delete")
    public Result commDeleteBatch(String ids) {
        commodityService.commDeleteBatch(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public List<CommodityDto> List(RoomPage page) {
        List<CommodityDto> list = commodityService.queryList(page);
        return list;
    }

    @GetMapping("/queryCommodityByName")
    public List<CommodityDto> queryCommodity(String name) {
        List<CommodityDto> list = commodityService.queryCommodityByName(name);
        return list;
    }
}

