package com.project.promotion.model.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageCreate {

    private HotelSearchDTO hotelSearchDTO;
    private int articleTotalCnt;
    private int endPage;
    private int beginPage;
    private boolean prev;
    private boolean next;

    private final int buttonNum = 5;

    private void calcDataOfPage(){
        endPage = (int)(Math.ceil(hotelSearchDTO.getPageNum()/(double) buttonNum) * buttonNum);
        beginPage = (endPage - buttonNum) + 1;
        prev = (beginPage == 1) ? false : true;
        next = articleTotalCnt <= (endPage * hotelSearchDTO.getCountPerPage()) ? false : true;

        if(next){
            endPage = (int) Math.ceil(articleTotalCnt / (double)hotelSearchDTO.getCountPerPage());
        }
    }
}
