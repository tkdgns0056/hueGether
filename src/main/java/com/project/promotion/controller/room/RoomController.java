package com.project.promotion.controller.room;

import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import com.project.promotion.model.reservation.Reservation;
import com.project.promotion.model.search.HotelSearchDTO;
import com.project.promotion.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/accommodation/detail")
    public String accommodationDetail( @AuthenticationPrincipal PrincipalDetails member,
                                       Business business,
                                       Room room,
                                       Model model){

        Reservation dateRange = new Reservation();
        dateRange.setCheckIn(new Date());
        dateRange.setCheckOut(new Date());

        List<Room> roomList = roomService.getRoomList(business.getBusinessId());

        model.addAttribute("roomList", roomList);
        model.addAttribute("room", room);
        model.addAttribute("business", business);
        model.addAttribute("dateRange", dateRange);
        model.addAttribute("member", member);

        return "accommodation/detail";
    }
}
