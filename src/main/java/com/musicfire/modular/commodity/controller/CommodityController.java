package com.musicfire.modular.commodity.controller;


import com.musicfire.common.utiles.ExcelUtil;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.dao.CommodityMapper;
import com.musicfire.modular.commodity.dao.ExcelCommodity;
import com.musicfire.modular.commodity.dao.ImportCommodity;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.entity.Dto.CommodityVo;
import com.musicfire.modular.commodity.query.CommodityPage;
import com.musicfire.modular.commodity.service.ICommodityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Resource
    private CommodityMapper commodityMapper;

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

    @PostMapping("/importCommodity")
    public Result importCommodity(@RequestParam("excel") MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        ExcelUtil<ImportCommodity> excelUtil = new ExcelUtil<>(ImportCommodity.class);
        List<ImportCommodity> commoditys = excelUtil.importExcel("", fileInputStream);
        commoditys.forEach(importCommodity -> {
            CommodityVo commodityVo = new CommodityVo();
            BeanUtils.copyProperties(importCommodity,commodityVo);
            commodityVo.setPrice(new BigDecimal(importCommodity.getPrice()));
            commodityVo.setPurchasePrice(new BigDecimal(importCommodity.getPurchasePrice()));
            commodityService.save(commodityVo);
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return new Result().ok();
    }

    @GetMapping("/exportCommodity")
    public void exportCommodity(CommodityPage page, HttpServletResponse response) throws IOException {
        page.setPageSize(-1);
        List<CommodityDto> list = commodityMapper.queryByCommodity(page);
        List<ExcelCommodity> commoditys = new ArrayList<>();
        list.forEach(commodityDto -> {
            ExcelCommodity excelCommodity = new ExcelCommodity();
            BeanUtils.copyProperties(commodityDto,excelCommodity);

            commoditys.add(excelCommodity);
        });

        String fileName = "商品信息"+System.currentTimeMillis()+".xls";
        ExcelUtil.setResponseHeader(response,fileName);
        OutputStream out = response.getOutputStream();
        ExcelUtil<ExcelCommodity> util = new ExcelUtil<>(ExcelCommodity.class);// 创建工具类.
        util.exportExcel(commoditys, "商品信息", 65536, out);// 导出
    }
}

