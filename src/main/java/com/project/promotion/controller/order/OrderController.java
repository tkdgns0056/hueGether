package com.project.promotion.controller.order;

import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.business.Room;
import com.project.promotion.model.login.SocialInfo;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.reservation.Reservation;
import com.project.promotion.model.search.HotelSearchDTO;
import com.project.promotion.service.member.UserService;
import com.project.promotion.service.order.OrderService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /** IamPort 결제 검증 컨트롤러 **/
    private final IamportClient iamportClient;

    // 생성자를 통해 REST API와 REST API secret 입력
    public OrderController(){
        this.iamportClient = new IamportClient("5006242453312457",
                                         "4WblUDTzPUwHiodIoT68DGJANNRfUQmOkdJJp3pOPp0I7rY58Nm4isYApR3M7f8qSoFeDY5PpN5yjvNm");
    }

    /** 프론트에서 받은 PG사 결괏값을 통해 아임포트 토큰 발행
     *  paymentByIdUid : 검증에 필요한 자바 함수
     *  paymentByImpUid 를 사용하기 위해서는 토큰 발급이 필요하고,
     *  토큰 발급을 하기 위해서는 REST API 키와 REST API secret가 필요하다.
     *  생성자를 통해 REST API 와 REST API secret 를 주입하여 토큰발급을 용이하게 했다.
     *  paymentByImpUid 함수는 아임포트서버에서 imp_uid(거래 고유번호)를 검사하여, 데이터를 보내준다.
     * **/

    @ResponseBody
    @PostMapping("/order/verify-iamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }

    /** 결제 하기 위한 폼 **/
    @GetMapping("/order/new")
    public String orderNew( @AuthenticationPrincipal PrincipalDetails member,
                            @RequestParam("roomId") int roomId,
                            Reservation reservation,
                            Model model) {

        Room room = orderService.getOderRoomInfo(roomId);

        model.addAttribute("member", member);
        model.addAttribute("room", room);
        model.addAttribute("dateRange", reservation);

        System.out.println("reservation CheckIn = " + reservation.getCheckIn());
        System.out.println("reservation CheckOut = " + reservation.getCheckOut());

        return "order/orderForm";
    }

        /* 카카오페이 결제 및 검증 */
        @ResponseBody
        @PostMapping(value = "/order/proc", produces = "application/text; charset=UTF-8")
        public String orderSave(String reservationId,
                                String payment,
                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkIn,
                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOut,
                                int userId,
                                int roomId,
                                int businessId,
                                Model model) {

            System.out.println("예약 검증 접근 !");
            String msg = "예약에 실패했습니다.";

            Reservation reservation = new Reservation();

            reservation.setReservationId(reservationId);
            // --->  이런식으로 하셔도 됩니다.
            //reservation.setDate(dateRange);
            // <--- 이 부분들을   Reservation 객체 안에 setter 를 하나로 통일해서 쓰시면 편하겠죠.
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            reservation.setPayment(payment);
            reservation.setRoomId(roomId);
            reservation.setUserId(userId);
            reservation.setBusinessId(businessId);

            try {
                int result = orderService.insertReserved(reservation);
                System.out.println("result = " + result);
                if (result != 0) {
                    msg = "예약에 성공 하였습니다.";
                    System.out.println(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAttribute("msg", msg);
            return "?msg="+msg;
        }

        @GetMapping("/order/detail")
        public String orderDetail(){

            return "order/completed";
        }
}
