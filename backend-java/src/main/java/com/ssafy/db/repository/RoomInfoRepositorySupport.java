package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoomInfo;
import com.ssafy.db.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomInfoRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QRoomInfo qRoomInfo = QRoomInfo.roomInfo;

    public List<RoomInfo> findAll(){
        List<RoomInfo> roomInfo= jpaQueryFactory.selectFrom(qRoomInfo).where(qRoomInfo.phase.in(0,1))
                .orderBy(qRoomInfo.cur_num.desc()).fetch();
       return roomInfo;
    }

    public List<RoomInfo> searchBy (String keyword){
        List<RoomInfo> searchBy = jpaQueryFactory.selectFrom(qRoomInfo)
                .where((qRoomInfo.subtitle.like("%"+keyword+"%").or(qRoomInfo.title.like("%"+keyword+"%")))
                        .and(qRoomInfo.phase.in(0,1)))
                .orderBy(qRoomInfo.cur_num.desc()).fetch();
        return searchBy;
    }

    public List<RoomInfo> findByCate(int cate){
        List<RoomInfo> findByCate=jpaQueryFactory.selectFrom(qRoomInfo)
                .where(qRoomInfo.cate.eq(cate).and(qRoomInfo.phase.in(0,1)))
                .orderBy(qRoomInfo.cur_num.desc()).fetch();
        return findByCate;
    }
}
