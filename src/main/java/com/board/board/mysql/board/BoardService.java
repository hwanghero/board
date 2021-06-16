package com.board.board.mysql.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class BoardService{
    @Autowired
    private BoardRepostiory BoardRepostiory;

    public Page<board> board_list(String title, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        Page<board> board = BoardRepostiory.findBoardByTitle(title, pageable);

        // 시간 계산
        for (board getBoard : board) {
            getBoard.setAftertime(BoardRepostiory.findBoardAfterTime(getBoard.getIdx()));
            int second = Integer.parseInt(getBoard.getAftertime());
            // 초 -> 분계산 (60)
            // 분 -> 시계산 (60)
            // 시 -> 날짜계산 (24)

            int min = 0;
            // 분 계산
            if(second >= 60){
                min = second/60;
                // 남는값을 빼야하네
                second = (second%60);
            }

            int hour = 0;
            // 시 계산
            if(min >= 60){
                hour = min/60;
                min = min%60;
            }

            int day = 0;
            if(hour >= 24){
                day = hour/24;
                hour = hour%24;
            }

            getBoard.setAftertime("수정 지난시간: "+day+"일 "+hour+"시 "+min+"분 "+second+"초");
        }
        return board;
    }

    public board board_one(int idx){
        board board_one = BoardRepostiory.findBoardByIdx(idx);
        return board_one;
    }

    public Collection<String> board_LastTime(){
        return BoardRepostiory.BoardLastTime();
    }

    public void board_write(board getBoard){
        // 현재 시간
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);

        // sysAdmin VO로 빌더 생성
        board newBoard = board.builder()
                .idx(getBoard.getIdx())
                .title(getBoard.getTitle())
                .color(getBoard.getColor())
                .description(getBoard.getDescription())
                .regtime(time1)
                .status('0')
                .aftertime("0")
                .build();
        BoardRepostiory.save(newBoard);
    }

    public void board_delete(int idx){
        board deleteBoard = board.builder()
                .idx(idx)
                .build();
        BoardRepostiory.delete(deleteBoard);
    }

    public void board_update(board getBoard){
        // 현재 시간
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);

        Optional<board> modifyBoard = Optional.ofNullable(BoardRepostiory.findBoardByIdx(getBoard.getIdx()));
        modifyBoard.ifPresent(selectBoard -> {
            selectBoard.setTitle(getBoard.getTitle());
            selectBoard.setColor(getBoard.getColor());
            selectBoard.setDescription(getBoard.getDescription());
            selectBoard.setRegtime(time1);
            selectBoard.setAftertime("0");
            BoardRepostiory.save(selectBoard);
        });
    }
}
