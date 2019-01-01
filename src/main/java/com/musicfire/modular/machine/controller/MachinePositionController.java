package com.musicfire.modular.machine.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.dto.MachinePositionVo;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.query.MachinePositionPage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
@RequestMapping("/machinePosition")
public class MachinePositionController {

    @Autowired
    private IMachinePositionService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody MachinePositionVo vo){
        MachinePosition machinePosition = new MachinePosition();
        BeanUtils.copyProperties(vo,machinePosition);
        service.save(machinePosition);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) @RequestBody MachinePositionVo vo){
        MachinePosition machinePosition = new MachinePosition();
        BeanUtils.copyProperties(vo,machinePosition);
        service.save(machinePosition);
        return new Result().ok();
    }

    @GetMapping("/delete/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        service.updateByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(MachinePositionPage page){
        MachinePositionPage machinePositionPage = service.queryByMachinePosition(page);
        return new Result().ok(machinePositionPage);
    }

    @GetMapping("/queryById/{id}")
    public Result queryById(@PathVariable Integer id){
        MachinePosition machinePosition = service.selectById(id);
        return new Result().ok(machinePosition);
    }

    @GetMapping("/openPosition")
    public Result openPosition(String code,Integer num){

       service.openPosition(code,num);
       return new Result().ok();
    }

    /**
     * 购买失败打开仓门
     * @param code
     * @param num
     * @return
     */
    @GetMapping("/purchaseErrOpenPosition")
    public Result purchaseErrOpenPosition(String code,Integer num,String unifiedNum){

        service.purchaseErrOpenPosition(code,num,unifiedNum);
        return new Result().ok();
    }

    @GetMapping("/queryByMachineCode/{code}")
    public Result queryByMachineCode(@PathVariable String code){
        List<MachinePositionDto> dto = service.queryByMachineCode(code);
        dto.sort(Comparator.comparingInt(MachinePositionDto::getNum));
        return new Result().ok(dto);
    }
}

