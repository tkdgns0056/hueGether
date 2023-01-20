package com.project.promotion.model.business;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business {

    private int businessId; //사업자시퀀스
    private String businessEmail; //사업자이메일
    private String businessPassword; //사업자비밀번호
    private int businessPhoneNumber; //사업자전화번호
    private String businessName; //사업자이름
    private String companyName; //숙박업체이름
    private String companyAddress; //숙박업체주소
    private int companyRegNumber; //사업자등록번호

    private Room room;

}
