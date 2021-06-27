package com.board.board.controller;

import com.board.board.mysql.board.BoardService;
import com.board.board.mysql.board.board;
import com.board.board.mysql.sys_admin.sysAdminService;
import com.board.board.mysql.sys_admin.sysadmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private sysAdminService sysAdminService;

    @RequestMapping("/")
    public String main(Model model, @PageableDefault(size=10, sort="regtime",direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request) {
        Page<board> boardList = boardService.board_list("", pageable);
        model.addAttribute("boardList", boardList);
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return "redirect:/login";
    }

    public boolean AdminCheck(HttpServletRequest request){
        HttpSession session = request.getSession();
        String getAdmin = (String) session.getAttribute("admin");
        if(getAdmin != null && getAdmin.equals("true")){
            return true;
        }
        return false;
    }

    @RequestMapping(value="/login_submit", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public String login_submit(@RequestBody Map<String, Object> json, HttpServletRequest request){
        // 여기서 로그인 체크
        HttpSession session = request.getSession();

        sysadmin SysAdmin = sysAdminService.sysAdminLogin(json.get("id").toString(), json.get("pw").toString());
        System.out.println(SysAdmin);

        if(SysAdmin != null){
            session.setAttribute("admin", "true");
            return "success";
        }else{
            return "failed";
        }
    }

    @RequestMapping("/list")
    public String list(Model model, @PageableDefault(size=10, sort="regtime",direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request) {
        System.out.println(AdminCheck(request));
        if(!AdminCheck(request))  return "redirect:/login";
        String searchTitle = request.getParameter("searchTitle")==null ? "" : request.getParameter("searchTitle");

        Page<board> boardList = boardService.board_list(searchTitle, pageable);
        model.addAttribute("boardList", boardList);

        Collection<board> boardContent = boardList.getContent();
        model.addAttribute("boardCount", boardList.getTotalElements());
        model.addAttribute("boardContent", boardContent);

        Collection<String> Time = boardService.board_LastTime();
        Object[] getTime = Time.toArray();
        model.addAttribute("boardLastTime", getTime[0].toString());
        return "list";
    }

    @RequestMapping("/write")
    public String write(HttpServletRequest request) {
        if(!AdminCheck(request))  return "redirect:/login";
        return "write";
    }

    @RequestMapping(value="/write_submit", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public String write_submit(@RequestBody Map<String, Object> json){
        board newBoard = new board();
        newBoard.setTitle(json.get("title").toString());
        newBoard.setColor(json.get("color").toString());
        newBoard.setDescription(json.get("desc").toString());
        boardService.board_write(newBoard);
        return "success";
    }

    @RequestMapping("/modify/{Idx}")
    public String modify(@PathVariable int Idx, Model model, HttpServletRequest request) {
        if(!AdminCheck(request))  return "redirect:/login";
        board getBoard = boardService.board_one(Idx);
        if(getBoard == null) return "redirect:/list";

        model.addAttribute("getBoard", getBoard);
        return "modify";
    }

    @RequestMapping(value="/modify_submit", method=RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public String modify_submit(@RequestBody Map<String, Object> json){
        board newBoard = new board();
        newBoard.setIdx(Integer.valueOf((String)json.get("idx")));
        newBoard.setTitle(json.get("title").toString());
        newBoard.setColor(json.get("color").toString());
        newBoard.setDescription(json.get("desc").toString());
        boardService.board_update(newBoard);
        return "success";
    }
}