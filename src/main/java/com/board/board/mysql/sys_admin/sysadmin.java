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
public class sysadmin {
    @Id
    private String id;
    private String pw;
    private char level;
}
