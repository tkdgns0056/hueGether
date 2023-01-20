package com.project.promotion.mapper;

import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SearchMapper {

    /* 메인 페이지에서 숙소명 및 지역으로 검샋 시 낮은 가격순으로 리스트 보여줌 */
    public List<Business> searchHotelList(String keyword);

}

