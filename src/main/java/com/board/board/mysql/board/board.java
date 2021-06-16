package com.board.board.mysql.board;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class board {
    @Id
    private int idx;
    private String title;
    private String color;
    private String description;
    private String regtime;
    // 노출=0, 삭제=1
    private char status;
    private String aftertime;
}
