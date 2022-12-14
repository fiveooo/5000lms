package com.gd.lms.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gd.lms.commons.PageUtil;
import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.IBoardService;
import com.gd.lms.vo.Board;
import com.gd.lms.vo.BoardFile;
import com.gd.lms.vo.BoardPost;
import com.gd.lms.vo.Comment;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	//서비스 객체 생성	
	@Autowired IBoardService boardService;
	PageUtil PageUtil = new PageUtil();
	
	
	//게시판 리스트 출력 메서드
	// - 일반 게시판만 리스트의 출력이 필요하기 떄문에 필요한 파라미터는 lectureNo뿐.
	@GetMapping("/board/list")
	public String getboardList(int lectureNo, @RequestParam(required=false, value="currentPage", defaultValue="1") int currentPage, Model model) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		log.debug(TeamColor.KHJ + "파라미터 확인 / currentPage : " + currentPage);
		
		//강좌 이름 생성 쿼리
		String lectureName = boardService.getLectureName(lectureNo);
		
		
		//페이징 변수 설정
		Map<String, Object> pageVariable = PageUtil.pageVariable(currentPage, boardService.getRealEndPageForBoard(lectureNo));		
		
		//넘겨줄 리스트(게시판)
		List<Board> list = boardService.getBoardList((int)pageVariable.get("beginRow"), (int)pageVariable.get("rowPerPage"),lectureNo);
		

		//값 넘겨주기
		model.addAttribute("boardList",list);
		model.addAttribute("lectureNo",lectureNo);
		model.addAttribute("lectureName",lectureName);
		
		
		//페이징 넘겨주는 값
		model.addAttribute("pages",pageVariable.get("pages"));
		model.addAttribute("currentPage",pageVariable.get("currentPage"));

		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardList list : " + list);
		log.debug(TeamColor.KHJ + "게시판 리스트 폼으로 포워딩");

		
		//포워딩
		return "board/boardList";
		
	}
	

	//게시판 게시글 출력 메서드
	// - 필수 파라미터 : 게시판 번호 || 강좌번호, 게시판 타입
	@GetMapping("/board/post")
	public String getBoardPostList(@RequestParam(required=false, value="currentPage", defaultValue="1") int currentPage, Board board, Model model) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / board : " + board);
		log.debug(TeamColor.KHJ + "값 확인 / currentPage : " + currentPage);
		

		//게시판 번호가 없을 경우 타입과 강좌 번호로 번호 세팅
		if(board.getBoardNo() == 0) {
			board.setBoardNo(boardService.getBoardNoByLectureNonBoardType(board));
		}
		
		//returnBoard객체 생성 쿼리 실행
		Board reBoard = boardService.getBoardByBoardNo(board.getBoardNo());
		
		//강좌 이름 생성 쿼리
		String lectureName = boardService.getLectureName(board.getLectureNo());
		
		
		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / reBoard : " + reBoard);
		

		//페이징 변수 설정
		Map<String, Object> pageVariable = PageUtil.pageVariable(currentPage, boardService.getRealEndPageForBoardPost(reBoard.getLectureNo(), reBoard.getBoardNo()));
		
		//넘겨줄 리스트(게시판)
		List<BoardPost> list = boardService.getBoardPostList((int)pageVariable.get("beginRow"), (int)pageVariable.get("rowPerPage"), reBoard);
		
		
		//값 넘겨주기
		model.addAttribute("boardPostList",list);
		model.addAttribute("board",reBoard);	
		model.addAttribute("lectureName",lectureName);	
		
		
		//페이징 넘겨주는 값
		model.addAttribute("pages",pageVariable.get("pages"));
		model.addAttribute("currentPage",pageVariable.get("currentPage"));
		model.addAttribute("realLastPage",pageVariable.get("realLastPage"));
		
		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardPost list : " + list);
		log.debug(TeamColor.KHJ + "boardPost 리스트 생성 및 포워딩");
		
		
		//포워딩
		return "board/boardPost";
		
	}
	
	
	//게시글 상세 페이지 출력 메서드
	@GetMapping("/board/post/one")
	public String getBoardPostOne(int boardPostNo, int boardNo, Model model) {
		

		//파라미터 확인 디버깅
		System.out.println("[boardCtrl] boardPostNo : " + boardPostNo);	
		System.out.println("boardNo : " + boardNo);	
		

		//returnBoard객체 생성 쿼리 실행
		Board reBoard = boardService.getBoardByBoardNo(boardNo);
		
		//강좌 이름 생성 쿼리
		String lectureName = boardService.getLectureName(reBoard.getLectureNo());
		
		
		
		//넘겨줄 값(BoardPost)
		Map<String, Object> boardOne = boardService.getBoardPostOne(boardPostNo);		
		
		//댓글 리스트
		List<Comment> commentList = boardService.getComment(boardPostNo);
		
		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardOne : " + boardOne);
		
		
		//값 넘겨주기
		model.addAttribute("boardOne",boardOne);
		model.addAttribute("commentList",commentList);		
		model.addAttribute("board",reBoard);
		model.addAttribute("lectureName",lectureName);
		
		

		//결과 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardOne : " + boardOne);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 상세보기 폼으로 포워딩");
		
		//포워딩
		return "board/boardOne";
		
	}

	
	//게시판 추가 폼 전송 메서드
	@GetMapping("/board/add/form")
	public String directAddBoard(int lectureNo, Model model) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);

		//값 넘겨주기
		model.addAttribute("lectureNo", lectureNo);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 작성 폼으로 포워딩");
		
		//포워딩
		return "board/addBoard";
	}
	
	
	//게시판 추가 메서드
	@GetMapping("/board/add")
	public String addBoard(Board board) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / board : " + board);
		
		//추가할 게시판 내용
		int row = boardService.addBoard(board);
		
		//추가 여부 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시판 추가 행 수 : " + row);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시판 리스트로 포워딩");
		
		//리다이렉션
		return "redirect:/board/list?lectureNo="+board.getLectureNo();
	}
	
	
	//게시글 추가 폼 전송 메서드
	@GetMapping("/board/post/add/form")
	public String directAddBoardPost(int boardNo, Model model) {

		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardNo : " + boardNo);
		
		
		//returnBoard객체 생성 쿼리 실행
		Board reBoard = boardService.getBoardByBoardNo(boardNo);
		
		//강좌 이름 생성 쿼리
		String lectureName = boardService.getLectureName(reBoard.getLectureNo());
		
		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / reBoard : " + reBoard);
		
		
		
		//값 넘겨주기
		model.addAttribute("board",reBoard);
		model.addAttribute("lectureName",lectureName);
		

		
		
		//결과 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 추가 폼으로 포워딩");
		
		return "board/addBoardPost";
	}
	
	
	//게시글 추가 메서드
	@PostMapping("/board/post/add")
	public String addBoardPost(Board board, BoardPost boardPost, int boardType, int lectureNo, MultipartFile[] uploadFile, HttpServletRequest request) throws UnsupportedEncodingException {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost boardPost : " + boardPost);
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost uploadFile : " + uploadFile);
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost board : " + board);
		
		
		//게시글 추가하기
		//int row = boardService.addBoardPost(boardPost);
		int row = boardService.addBoardPostandFile(boardPost, uploadFile, request);
		
		
		//결과 확인 디버깅
		log.debug(TeamColor.KHJ + "완료 후 값 확인 / 게시글 + 파일 추가 행 수 : " + row);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 리스트로 리다이렉션");
		
		//리다이렉션
		return "redirect:/board/post?boardNo=" + board.getBoardNo();
		
	}
	
	
	//파일 다운로드 메서드
	@GetMapping("board/download/file")
	public ResponseEntity<Object> downloadFile(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
		
		//파일 경로 설정
		String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/boardFile") +"\\"+ fileName;

		//값 확인 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / realPath: "+realPath);
		
		//리턴값 세팅
		ResponseEntity<Object> returnVal = boardService.downloadFile(fileName, realPath);
		
		//값 확인 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / returnVal: "+returnVal);
		
		//리턴
		return returnVal;
				
	}
	
	
	//댓글 달기 기능
	@GetMapping("board/addComment")
	public String addComment(HttpSession session, Comment comment, int boardNo, Model model) throws UnsupportedEncodingException{
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / comment : " + comment);
		
		User login = (User)session.getAttribute("loginUser");
		
		
		comment.setCommentWriter(login.getUserName()+"(" + login.getUserId()+ ")");
		
		

		// 실행
		int row = boardService.addComment(comment);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 추가된 댓글 행수 : " + row);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 상세페이지로 포워딩");

		

		//포워딩
		return "redirect:/board/post/one?boardPostNo="+comment.getBoardPostNo()+"&boardNo="+boardNo;
				
		
	}
	
	
	//댓글 삭제 기능
	@GetMapping("board/removeComment")
	public String removeCommet(Comment comment, int boardNo, Model model) throws UnsupportedEncodingException {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / comment : " + comment);

		// 실행
		int row = boardService.removeComment(comment.getCommentNo());
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 삭제된 댓글 행수 : " + row);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 상세페이지로 포워딩");
		
		return "redirect:/board/post/one?boardPostNo="+comment.getBoardPostNo()+"&boardNo="+boardNo;
	}
	
	
	
	//게시글 삭제 기능
	@GetMapping("board/removePost")
	public String removePost(int boardNo, int boardPostNo,String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardPostNo : " + boardPostNo);
		log.debug(TeamColor.KHJ + "파라미터 확인 / fileName : " + fileName);
		

		// 실행
		int row = boardService.removeBoardPost(boardPostNo, fileName, request);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 삭제된 댓글 행수 : " + row);
		log.debug(TeamColor.KHJ + "결과 확인 / 해당 게시판으로 포워딩");
		
		return "redirect:/board/post?boardNo="+boardNo;

		
	}
	
	
	//게시글 수정 폼 전송 기능
	@GetMapping ("board/modifyPost/form")
	public String directModifyPost(int boardPostNo, int boardNo, Model model) {
		
		//파라미터 확인 디버깅
		System.out.println("[boardCtrl] boardPostNo : " + boardPostNo);	
		
		
		

		//returnBoard객체 생성 쿼리 실행
		Board reBoard = boardService.getBoardByBoardNo(boardNo);
		
		//넘겨줄 값(BoardPost)
		Map<String, Object> boardOne = boardService.getBoardPostOne(boardPostNo);
		
		
		//댓글 리스트
		List<Comment> commentList = boardService.getComment(boardPostNo);
		
		//값 넘겨주기
		model.addAttribute("boardOne",boardOne);
		model.addAttribute("commentList",commentList);		
		model.addAttribute("board",reBoard);

		model.addAttribute("boardPostNo",boardPostNo);

		
		//결과 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardOne : " + boardOne);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 수정 폼으로 포워딩");
		
		//포워딩
		return "board/modifyBoardPost";
	}
	
	//게시글 수정 기능
	@PostMapping("board/modifyPost")
	public String modifyPost(BoardPost boardPost, int boardNo) throws UnsupportedEncodingException {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardPost : " + boardPost);
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardNo : " + boardNo);


		//returnBoard객체 생성 쿼리 실행
		Board reBoard = boardService.getBoardByBoardNo(boardNo);
		
		
		//실행
		int row = boardService.modifyBoardPost(boardPost);

		
		//리턴
		return "redirect:/board/post/one?boardPostNo="+boardPost.getBoardPostNo()+"&boardNo="+boardNo;
	}
	
	
	//게시판 삭제 기능
	@GetMapping("board/removeBoard")
	public String removeBoard(int boardNo, int lectureNo) {
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardNo : " + boardNo);
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		
		// 실행
		int row = boardService.removeBoard(boardNo);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 삭제된 댓글 행수 : " + row);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시판 리스트로 포워딩");
				
		return "redirect:/board/list?lectureNo=" + lectureNo;
	}
	
	
	
	//댓글 수정 기능 - 만들기는 했으나, 비동기 기술을 좀 공부하고 나서 적용하는 걸로!
	@PostMapping("board/modifyComment")
	public String modifyComment(Comment comment, String boardName, int boardNo, Model model) throws UnsupportedEncodingException {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / comment : " + comment);

		// 실행
		int row = boardService.modifyComment(comment);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 수정된 댓글 행수 : " + row);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 상세페이지로 포워딩");
		
		//넘겨주는 값 인코딩
		String encodedboardName=URLEncoder.encode(boardName, "UTF-8");
		
		
		return "redirect:/board/post/one?boardPostNo="+comment.getBoardPostNo() + "&boardName=" + encodedboardName + "&boardNo=" + boardNo;
	}
	

}
