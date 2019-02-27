package com.musicfire.modular.merchant.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.ExcelUtil;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.merchant.dto.ExcelMerchant;
import com.musicfire.modular.merchant.dto.ImportMerchant;
import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.dto.MerchantVo;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.query.MerchantPage;
import com.musicfire.modular.merchant.service.IMerchantService;
import org.apache.http.HttpResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
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
@RequestMapping("/merchant")
public class MerchantController {


    @Autowired
    private IMerchantService service;

    @Value("${projectUrl.vendingmachine}")
    public String serverUrl;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody MerchantVo merchantVo) {
        service.save(merchantVo);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody @Validated(value = Update.class) MerchantVo merchantVo) {
        service.save(merchantVo);
        return new Result().ok();
    }

    /**
     * 给代理分配商家
     * @param merchantVo
     * @return
     */
    @PostMapping("/allotAgent")
    public Result allotAgent(@RequestBody  MerchantVo merchantVo) {
        if(null == merchantVo.getId()){
            return new Result().fail("信息有误,请检查");
        }
        service.allotAgent(merchantVo);
        return new Result().ok();
    }

    /**
     * 获取未分配商家
     */
    @GetMapping("/undistributedBusiness")
    public Result undistributedBusiness() {
        List<Merchant> merchants = service.undistributedBusiness();

        return new Result().ok(merchants);
    }
    /**
     *根据代理商查询商家
     * @param id
     * @return
     */
    @GetMapping("/queryByAgentId/{id}")
    public Result queryByAgentId(@PathVariable Integer id ) {
        List<Merchant> merchants = service.queryByAgentId(id);
        return new Result().ok(merchants);
    }
    /**
     * 获取代理商
     */
    @GetMapping("/agent")
    public Result agent() {

        List<Merchant> merchants = service.agent();
        return new Result().ok(merchants);
    }

    @GetMapping("/list")
    public Result list(MerchantPage merchantPage) {
        MerchantPage page = service.list(merchantPage);
        return new Result().ok(page);
    }

    @GetMapping("/delete/{ids}")
    public Result list(@PathVariable List<Integer> ids) {
        service.deleteByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/getAll")
    public Result getAll(String merchantName) {
        List<MerchantDto> list = service.getAll(merchantName );
        return new Result().ok(list);
    }

    @PostMapping("/importMerchant")
    public Result importMerchant(@RequestParam("excel") MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        ExcelUtil<ImportMerchant> excelUtil = new ExcelUtil<>(ImportMerchant.class);
        List<ImportMerchant> merchants = excelUtil.importExcel("", fileInputStream);
        merchants.forEach(importMerchant -> {
            MerchantVo merchantVo = new MerchantVo();
            BeanUtils.copyProperties(importMerchant,merchantVo);
            service.save(merchantVo);
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return new Result().ok();
    }

    @GetMapping("/exportMerchant")
    public Result exportMerchant(MerchantPage page, HttpServletResponse response) throws IOException {
        page.setPageSize(-1);
        List<MerchantDto> list = service.queryPageAll(page);
        List<ExcelMerchant> merchants = new ArrayList<>();
        list.forEach(merchantDto -> {
            ExcelMerchant excelMerchant = new ExcelMerchant();
            BeanUtils.copyProperties(merchantDto,excelMerchant);
            if(merchantDto.getType() ==1){
                excelMerchant.setTypeStr("普通商家");
            }else{
                excelMerchant.setTypeStr("代理商");
            }
            merchants.add(excelMerchant);
        });


        String fileName = "商家信息"+System.currentTimeMillis()+".xls";
        FileOutputStream out = new FileOutputStream(Conf.getValue("excelImportAddr")+"/"+fileName);
        ExcelUtil<ExcelMerchant> util = new ExcelUtil<>(ExcelMerchant.class);// 创建工具类.
        util.exportExcel(merchants, "商家信息", 65536, out);// 导出
        return new Result().ok(serverUrl+"/"+fileName);
    }

}

