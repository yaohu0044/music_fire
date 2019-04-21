package com.musicfire.modular.machine.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.ExcelUtil;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.machine.dto.ImportMachinePosition;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.dto.MachinePositionVo;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.query.MachinePositionPage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.merchant.dto.ImportMerchant;
import com.musicfire.modular.merchant.dto.MerchantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

    @Autowired
    private IMachineService machineService;

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

    @PostMapping("/importMachinePosition")
    public Result importMerchant(@RequestParam("excel") MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        ExcelUtil<ImportMachinePosition> excelUtil = new ExcelUtil<>(ImportMachinePosition.class);
        List<ImportMachinePosition> positions = excelUtil.importExcel("", fileInputStream);
        positions.stream().filter(position -> null != position.getMachineCode()).forEach(position -> {
            EntityWrapper<Machine> machineEntityWrapper = new EntityWrapper<>();
            machineEntityWrapper.eq("code",position.getMachineCode());
            machineEntityWrapper.eq("flag",false);
            Machine machine = machineService.selectOne(machineEntityWrapper);
            if(null != machine){
                MachinePosition machinePosition = new MachinePosition();
                BeanUtils.copyProperties(position, machinePosition);
                EntityWrapper<MachinePosition> entityWrapper = new EntityWrapper<>();
                machinePosition.setMachineId(machine.getId());
                entityWrapper.eq("commodity_id",position.getCommodityId());
                entityWrapper.eq("machine_id",machine.getId());
                MachinePosition machinePosition1 = service.selectOne(entityWrapper);
                if(null == machinePosition1){
                    service.save(machinePosition);
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return new Result().ok();
    }
}

