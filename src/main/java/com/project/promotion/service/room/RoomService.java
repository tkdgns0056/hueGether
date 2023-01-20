package com.project.promotion.service.room;

import com.project.promotion.mapper.RoomMapper;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;

    public List<Room> getRoomList(int businessId){

        List<Room> roomList = roomMapper.getBusinessRoomList(businessId);

        return roomList;
    }
}
