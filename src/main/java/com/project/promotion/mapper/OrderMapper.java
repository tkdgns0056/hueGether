package com.project.promotion.mapper;

import com.project.promotion.model.business.Room;
import com.project.promotion.model.reservation.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    public Room getOrderRoom(@Param("roomId") int roomId);

    public int insertReserved(Reservation reservation);
}
