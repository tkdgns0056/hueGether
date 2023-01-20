package com.project.promotion.model.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private int roomId; //객실시퀀스
    private int businessId;
    private String roomName; //객실이름
    private int roomPrice; //객실가격
    private int roomCount; //객실개수
    private String roomInfo; //객실정보
    private int peopleCount; //최대수용인원

    private Business business;
}
