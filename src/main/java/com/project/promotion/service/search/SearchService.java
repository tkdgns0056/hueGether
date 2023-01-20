package com.project.promotion.service.search;

import com.project.promotion.mapper.SearchMapper;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchMapper searchMapper;

    /*
      메인 페이지에서 호텔 및 지역 검색 후 보여주는 리스트
     */
    @Transactional
    public List<Business> searchList(String keyword) {

        List<Business> businesses = searchMapper.searchHotelList(keyword);

        return businesses;
    }
}
