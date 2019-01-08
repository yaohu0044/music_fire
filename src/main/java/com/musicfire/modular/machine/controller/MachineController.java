package com.musicfire.modular.machine.controller;


import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.handler.RequestHolder;
import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.DateTool;
import com.musicfire.common.utiles.ExcelUtil;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.login.Login;
import com.musicfire.modular.machine.dto.ExcelMachine;
import com.musicfire.modular.machine.dto.ImportMachine;
import com.musicfire.modular.machine.dto.MachineVo;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.merchant.service.IMerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/machine")
public class MachineController {

    private final IMachineService service;

    private final IMerchantService service1;

    @Autowired
    public MachineController(IMachineService service, IMerchantService service1) {
        this.service = service;
        this.service1 = service1;
    }


    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody MachineVo vo) {
        Machine machine = new Machine();
        BeanUtils.copyProperties(vo, machine);
        service.save(machine);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) @RequestBody MachineVo vo) {
        Machine machine = new Machine();
        BeanUtils.copyProperties(vo, machine);
        service.save(machine);
        return new Result().ok();
    }

    @GetMapping("/delete/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        service.updateByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(MachinePage page) {
        MachinePage machinePage = service.queryByMachine(page);
        return new Result().ok(machinePage);
    }

    @GetMapping("/queryById/{id}")
    public Result queryById(@PathVariable Integer id) {
        Machine machine = service.selectById(id);
        return new Result().ok(machine);
    }

    @GetMapping("/queryByMerchantId")
    public Result queryByMerchantId(Integer merchantId) {
        List<Machine> machine = service.queryByMerchantId(merchantId);
        return new Result().ok(machine);
    }

    /**
     * 针对商家
     *
     * @return
     */
    @GetMapping("/queryMerchantId")
    public Result queryMerchantId() {
        Login currentUser = RequestHolder.getCurrentUser();
        if (null == currentUser) {
            throw new BusinessException(ErrorCode.NOT_LOGGED_IN);
        }
        List<Machine> machine = service.queryByMerchantId(currentUser.getMerchantId());
        return new Result().ok(machine);
    }


    /**
     * 如果机器多的,考虑使用局部加载
     *
     * @return
     */
    @GetMapping("/getLonAndLatAll")
    public Result getLonAndLatAll() {
        List<Machine> machine = service.getLonAndLatAll();
        List<Map<String, Double>> list = new ArrayList<>();
        machine.forEach(machine1 -> {
            Map<String, Double> map = new HashMap<>();
            map.put("lon", Double.valueOf(machine1.getLonAndLat().split(",")[0]));
            map.put("lat", Double.valueOf(machine1.getLonAndLat().split(",")[1]));
            list.add(map);
        });

        return new Result().ok(list);
    }

    @RequestMapping(value = "/openMachine", method = RequestMethod.GET)
    @ResponseBody
    public Result openMachine(Integer id) {
        service.openMachine(id);
        return new Result().ok();
    }

    @GetMapping("/exportMachine")
    public void exportMerchant(MachinePage page, HttpServletResponse response) throws IOException {
        page.setPageSize(-1);
        List<?> list = service.queryByMachine(page).getList();
        List<ExcelMachine> merchants = new ArrayList<>();
        list.forEach(merchantDto -> {
            ExcelMachine excelMachine = new ExcelMachine();
            BeanUtils.copyProperties(merchantDto, excelMachine);
            merchants.add(excelMachine);
        });
        String fileName = "机器信息" + System.currentTimeMillis() + ".xls";
        ExcelUtil.setResponseHeader(response, fileName);
        OutputStream out = response.getOutputStream();
        ExcelUtil<ExcelMachine> util = new ExcelUtil<>(ExcelMachine.class);// 创建工具类.
        util.exportExcel(merchants, "机器信息", 65536, out);// 导出
    }

    @PostMapping("/importMachine")
    public void importMachine(@RequestParam("excel") MultipartFile file) throws IOException {
        InputStream fis = file.getInputStream();
        ExcelUtil<ImportMachine> util = new ExcelUtil<>(ImportMachine.class);
        List<ImportMachine> lists = util.importExcel("机器基本导入", fis);
        List<Machine> machines = new ArrayList<>();
        lists.forEach(importMiachine -> {
            Machine merchant = new Machine();
            BeanUtils.copyProperties(importMiachine, merchant);
            machines.add(merchant);
        });

        service.insertBatch(machines);
    }

    @PostMapping("/importMachineTemplate")
    public void importMachineTemplate(HttpServletResponse response, String fileName) throws IOException {

        File file = new File(Conf.getValue("excelMachineTemp"));
        ExcelUtil.setResponseHeader(response, file.getName());
        PrintWriter writer = response.getWriter();
        writer.println(file);
        writer.close();
    }

    @GetMapping("/notOrderMachine")
    public Result notOrderMachine(MachinePage page) {
        if (page.getStartTime() == null) {
            page.setStartTime(DateTool.getFormat(DateTool.getBefDay(7)));
        }
        if (page.getEndTime() == null) {
            page.setEndTime(DateTool.getFormat(new Date()));
        }

        MachinePage machinePage = service.notOrderMachine(page);

        return new Result().ok(machinePage);
    }
}

