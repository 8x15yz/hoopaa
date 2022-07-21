package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomDescription;
import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDescriptionRepository extends JpaRepository<RoomDescription,Long> {

    List<RoomDescription> searchBy();
    List<RoomDescription> findByCate(int cate);
}
