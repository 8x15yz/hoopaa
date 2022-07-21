package com.ssafy.api.service;

import com.ssafy.db.entity.RoomDescription;
import com.ssafy.db.entity.RoomInfo;

import java.util.List;

public interface RoomInfoService {

    List<RoomInfo> findAll();
    List<RoomDescription> searchBy(String keyword);
    List<RoomDescription> findByCate(int cate);
}
