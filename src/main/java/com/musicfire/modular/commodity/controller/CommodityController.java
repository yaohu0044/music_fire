package com.musicfire.modular.commodity.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.entity.Dto.CommodityVo;
import com.musicfire.modular.commodity.query.CommodityPage;
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

    @PostMapping("/save")
    public Result commSave(@Validated @RequestBody CommodityVo commodityVo) {
        commodityService.save(commodityVo);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody CommodityVo commodityVo) {
        commodityService.edit(commodityVo);
        return new Result().ok();
    }

    @GetMapping("/delete/{ids}")
    public Result commDeleteBatch(@PathVariable List<Integer> ids) {
        commodityService.commDeleteBatch(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result List(CommodityPage page) {
        return new Result().ok(commodityService.queryList(page));
    }

    @GetMapping("/queryCommodityByName")
    public Result queryCommodity(String name) {
        List<CommodityDto> list = commodityService.queryCommodityByName(name);
        return new Result().ok(list);
    }
}

