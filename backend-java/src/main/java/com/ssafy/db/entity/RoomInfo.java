package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "notId")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pwd;
    private int host_id;
    private int is_sys;
    private String thumb_url;
    private int phase;
    private int max_num;
    private int cur_num;
    private Date start_time;

    @OneToOne
    @JoinColumn(name = "id")
//    @JsonManagedReference
//    @JsonBackReference
    private RoomDescription roomDescription;

    @Builder
    public RoomInfo(Long id, String pwd, int host_id, int is_sys, String thumb_url, int phase, int max_num, int cur_num, Date start_time, RoomDescription roomDescription) {
        this.id = id;
        this.pwd = pwd;
        this.host_id = host_id;
        this.is_sys = is_sys;
        this.thumb_url = thumb_url;
        this.phase = phase;
        this.max_num = max_num;
        this.cur_num = cur_num;
        this.start_time = start_time;
        this.roomDescription = roomDescription;
    }
}
