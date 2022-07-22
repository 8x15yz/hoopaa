package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface RoomInfoRepository extends JpaRepository<RoomInfo,Long> {

    List<RoomInfo> findAll();
    List<RoomInfo> searchBy();
    List<RoomInfo> findByCate(int cate);
}
