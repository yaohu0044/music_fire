package com.musicfire.modular.machine.controller;


import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.handler.RequestHolder;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.login.Login;
import com.musicfire.modular.machine.dto.MachineVo;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        service.save(machine);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) @RequestBody MachineVo vo){
        Machine machine = new Machine();
        BeanUtils.copyProperties(vo,machine);
        service.save(machine);
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

    /**
     *
     * 针对商家
     * @return
     */
    @GetMapping("/queryMerchantId")
    public Result queryMerchantId(){
        Login currentUser = RequestHolder.getCurrentUser();
        if(null == currentUser){
            throw new BusinessException(ErrorCode.NOT_LOGGED_IN);
        }
        List<Machine> machine = service.queryByMerchantId(currentUser.getMerchantId());
        return new Result().ok(machine);
    }


    /**
     * 如果机器多的,考虑使用局部加载
     * @return
     */
    @GetMapping("/getLonAndLatAll")
    public Result getLonAndLatAll(){
        List<Machine> machine = service.getLonAndLatAll();
        List<Map<String,Double>> list = new ArrayList<>();
        machine.forEach(machine1 -> {
            Map<String,Double> map = new HashMap<>();
            map.put("lon",Double.valueOf(machine1.getLonAndLat().split(",")[0]));
            map.put("lat",Double.valueOf(machine1.getLonAndLat().split(",")[1]));
            list.add(map);
        });

        return new Result().ok(list);
    }
    @RequestMapping(value="/openMachine",method= RequestMethod.GET)
    @ResponseBody
    public Result openMachine(Integer id) {
        service.openMachine(id);
        return new Result().ok();
    }

}

