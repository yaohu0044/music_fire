package com.musicfire.modular.room.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.room.dto.RoomDto;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result save(RoomDto dto){
        Room room = new Room();
        BeanUtils.copyProperties(room,dto);
        service.insert(room);
       return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@Validated(value = Update.class) RoomDto dto){
//        if(ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getId())){
//            return new Result().fail(RoomEnum.ROOM_ID_IS_NULL.getMsg());
//        }
        Room room = new Room();
        BeanUtils.copyProperties(room,dto);
        service.updateById(room);
        return new Result().ok();
    }

    @PostMapping("/delete")
    public Result delete(List<Room> rooms){
        rooms.forEach(room->room.setFlag(true));
        service.updateBatchById(rooms);
        return new Result().ok();
    }

    @PostMapping("/list")
    public Result list(RoomPage page){
        RoomPage roomPage = service.queryByRoom(page);
        return new Result().ok(roomPage);
    }


}

