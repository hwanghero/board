package com.board.board.mysql.sys_admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sysAdminRepository extends JpaRepository<sys_admin, String> { }
