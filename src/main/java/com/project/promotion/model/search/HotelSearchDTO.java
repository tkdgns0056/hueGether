package com.project.promotion.model.search;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class HotelSearchDTO {

    private int pageNum; //사용자가 선택한 페이지 정보를 담을 변수.
    private int countPerPage;
    private String condition; //타입
    private String keyword; //검색 키워드


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateFrom;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateTo;

    public HotelSearchDTO(){
        this.pageNum = 1;
        this.countPerPage = 10;
    }
}
