package com.musicfire.modular.merchant.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.merchant.dto.MerchantDto;
import com.musicfire.modular.merchant.dto.MerchantVo;
import com.musicfire.modular.merchant.query.MerchantPage;
import com.musicfire.modular.merchant.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

}

