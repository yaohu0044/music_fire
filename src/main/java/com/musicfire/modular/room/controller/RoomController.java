package com.musicfire.modular.room.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.room.dto.RoomVo;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private IRoomService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class)@RequestBody RoomVo vo){
        Room room = new Room();
        BeanUtils.copyProperties(vo,room);
        service.save(room);
       return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) @RequestBody RoomVo vo){
        Room room = new Room();
        BeanUtils.copyProperties(vo,room);
        service.save(room);
        return new Result().ok();
    }

    @GetMapping("/delete/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        service.updateByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(RoomPage page){
        RoomPage roomPage = service.queryByRoom(page);
        return new Result().ok(roomPage);
    }

    @GetMapping("/queryById/{id}")
    public Result queryById(@PathVariable Integer id){
        Room room = service.selectById(id);
        return new Result().ok(room);
    }
}

