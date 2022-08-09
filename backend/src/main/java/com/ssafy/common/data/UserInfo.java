package com.ssafy.common.data;

import com.ssafy.db.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 토론방의 패널 유저 정보와 최종 토론왕 정보를 알기 위한 클래스
 */
@Data
@ToString
public class UserInfo {

    String id;
    String em;
    String nnm;
    int kingCnt;
    boolean hasAgree;
    boolean hasDisagree;
    boolean hasFinalVote;

    @Builder
    public UserInfo(String id, String em, String nnm, int kingCnt, boolean hasAgree, boolean hasDisagree, boolean hasFinalVote) {
        this.id = id;
        this.em = em;
        this.nnm = nnm;
        this.kingCnt = kingCnt;
        this.hasAgree = hasAgree;
        this.hasDisagree = hasDisagree;
        this.hasFinalVote = hasFinalVote;
    }
}
