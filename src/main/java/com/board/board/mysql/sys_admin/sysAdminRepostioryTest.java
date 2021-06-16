package com.board.board.mysql.sys_admin;

import com.board.board.BoardApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class sysAdminRepostioryTest extends BoardApplication {
    @Autowired
    private sysAdminRepository sysAdminRepository;

    @Test
    public void create(){
        // sysAdmin VO로 빌더 생성
        sys_admin SysAdmin = sys_admin.builder()
                .id("test")
                .pw("testPW")
                .level('1')
                .build();
        sys_admin NewSysAdmin = sysAdminRepository.save(SysAdmin);
    }
}
