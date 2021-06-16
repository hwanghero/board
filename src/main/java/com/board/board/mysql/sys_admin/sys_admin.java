package com.board.board.mysql.sys_admin;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class sys_admin {
    @Id
    private String id;
    private String pw;
    private char level;
}
