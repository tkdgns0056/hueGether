package com.project.promotion.service.order;

import com.project.promotion.mapper.OrderMapper;
import com.project.promotion.mapper.RoomMapper;
import com.project.promotion.model.business.Room;
import com.project.promotion.model.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    /* 예약할 객실 정보 가져오기 */
    private final OrderMapper orderMapper;


    public Room getOderRoomInfo(int roomId){

        Room room = orderMapper.getOrderRoom(roomId);
        return room;
    }

    /** 예약 및 결제 */
    @Transactional(rollbackFor = Exception.class)
    public int insertReserved(Reservation reservation){

        int result = orderMapper.insertReserved(reservation);

        if(result == 0){
            return 0;
        } else {
            return 1;
        }
    }
}

