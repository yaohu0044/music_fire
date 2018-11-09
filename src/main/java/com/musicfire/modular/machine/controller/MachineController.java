package com.musicfire.modular.machine.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.machine.dto.MachineVo;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/machine")
public class MachineController {

    @Autowired
    private IMachineService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody MachineVo vo){
        Machine machine = new Machine();
        BeanUtils.copyProperties(vo,machine);
        service.insert(machine);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) @RequestBody MachineVo vo){
        Machine machine = new Machine();
        BeanUtils.copyProperties(vo,machine);
        service.updateById(machine);
        return new Result().ok();
    }

    @GetMapping("/delete/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        service.updateByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(MachinePage page){
        MachinePage machinePage = service.queryByMachine(page);
        return new Result().ok(machinePage);
    }

    @GetMapping("/queryById/{id}")
    public Result queryById(@PathVariable Integer id){
        Machine machine = service.selectById(id);
        return new Result().ok(machine);
    }

    @GetMapping("/queryByMerchantId")
    public Result queryByMerchantId(Integer merchantId){
        List<Machine> machine = service.queryByMerchantId(merchantId);
        return new Result().ok(machine);
    }
}

