package com.board.board.mysql.sys_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sysAdminService {
    @Autowired
    private sysAdminRepository sysAdminRepository;

    public sysadmin sysAdminLogin(String id, String pw){
        sysadmin getAdmin = sysAdminRepository.findsysadminById(id, pw);
        return getAdmin;
    }
}
