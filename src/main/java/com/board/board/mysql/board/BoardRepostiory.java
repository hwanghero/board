package com.board.board.mysql.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoardRepostiory extends JpaRepository<board, Integer> {
    @Query(value = "select * from board where title like %?1%", nativeQuery = true)
    Page<board> findBoardByTitle(String title, Pageable pageable);
    // 게시판 Idx 조회
    board findBoardByIdx(int Idx);
    // 모든 게시판 마지막 수정시간
    @Query(value = "select regtime from board order by regtime desc limit 1", nativeQuery = true)
    Collection<String> BoardLastTime();
    // 게시판 마다 마지막 수정 시간
    @Query(value = "select TIMESTAMPDIFF(second, regtime, now()) from board where idx=?1", nativeQuery = true)
    String findBoardAfterTime(int Idx);
}
