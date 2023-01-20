package com.project.promotion.mapper;

import com.project.promotion.model.business.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {

    public List<Room> getBusinessRoomList(int businessId);

    /* 객실 한개 정보 가져오기 */
    public Room getOneRoom(int roomId);
}
