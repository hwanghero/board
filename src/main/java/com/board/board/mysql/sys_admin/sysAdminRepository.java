package com.board.board.mysql.sys_admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface sysAdminRepository extends JpaRepository<sysadmin, String> {
    @Query(value = "select * from sysadmin where id = ?1 AND pw =?2", nativeQuery = true)
    sysadmin findsysadminById(String id, String pw);
}
