package com.project.promotion.model.reservation;

import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.search.HotelSearchDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    private String reservationId; //예약시퀀스
    private int roomId; //객실시퀀스
    private int businessId; //업체시퀀스
    private int userId; // 예약한 유저 시퀀스
    private String reservationStatus; //예약상태(enum ?)
    private String payment; //결제수단
    private int paymentPrice; //결제금액
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkIn; //체크인날짜
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkOut; //체크아웃날짜

    private int reservationPeopleCount; //인원수

    private Room room;
    private Member member;
    private Business business;

//    public Reservation(){}
//    public Reservation(HotelSearchDTO dateRange){
//        checkIn = dateRange.getDateFrom();
//        checkOut = dateRange.getDateTo();
//    }
    public void setDate(HotelSearchDTO dateRange){
        checkIn = dateRange.getDateFrom();
        checkOut = dateRange.getDateTo();
    }



}
