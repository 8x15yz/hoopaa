package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "notId")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cate;
    private Integer hash_1;
    private Integer hash_2;
    private Integer hash_3;
    private String title;
    private String subtitle;

    @OneToOne(mappedBy = "roomDescription")
    @JoinColumn(name = "id")
//    @JsonManagedReference
//    @JsonBackReference
    private RoomInfo roomInfo;

    @Builder
    public RoomDescription(Long id, int cate, Integer hash_1, Integer hash_2, Integer hash_3, String title, String subtitle, RoomInfo roomInfo) {
        this.id = id;
        this.cate = cate;
        this.hash_1 = hash_1;
        this.hash_2 = hash_2;
        this.hash_3 = hash_3;
        this.title = title;
        this.subtitle = subtitle;
        this.roomInfo = roomInfo;
    }
}
