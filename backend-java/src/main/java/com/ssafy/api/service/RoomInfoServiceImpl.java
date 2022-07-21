package com.ssafy.api.service;

import com.ssafy.db.entity.RoomDescription;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.RoomDescriptionRepository;
import com.ssafy.db.repository.RoomDescriptionRepositorySupport;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoomInfoService")
public class RoomInfoServiceImpl implements RoomInfoService{

    @Autowired
    RoomInfoRepository roomInfoRepository;
    @Autowired
    RoomDescriptionRepository roomDescriptionRepository;

    @Autowired
    RoomInfoRepositorySupport roomInfoRepositorySupport;
    @Autowired
    RoomDescriptionRepositorySupport roomDescriptionRepositorySupport;

    @Override
    public List<RoomInfo> findAll() {
        List<RoomInfo> roomInfos=roomInfoRepositorySupport.findAll();
        return roomInfos;
    }

    @Override
    public List<RoomDescription> searchBy(String keyword) {
        List<RoomDescription> searchBy=roomDescriptionRepositorySupport.searchBy(keyword);
        return searchBy;
    }

    @Override
    public List<RoomDescription> findByCate(int cate) {
        List<RoomDescription> findByCate=roomDescriptionRepositorySupport.findByCate(cate);
        return findByCate;
    }
}
