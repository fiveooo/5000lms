package com.gd.lms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gd.lms.commons.PageUtil;
import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.ILectureService;
import com.gd.lms.vo.Sign;
import com.gd.lms.vo.SignCancel;
import com.gd.lms.vo.Subject;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LectureController {
  @Autowired ILectureService lectureService;
  PageUtil PageUtil = new PageUtil();
	
  @GetMapping ("/sign/openLectureList")
   public String selectLectureListForSign(Model model, Sign sign, SignCancel signCancel, HttpSession session, @RequestParam(required=false, value="currentPage", defaultValue="1")int currentPage) {
//		  // 로그인 상태가 아니면 로그인페이지
//		  if(session.getAttribute("user") == null) { 
//			  return "redirect:/lms/user/login";
//		  }
//		  // 권한이 학생 아니면 인덱스 페이지
//		  else if (Integer.parseInt((String) session.getAttribute("level")) != 3 || Integer.parseInt((String) session.getAttribute("level")) != 1) {
//			  return "redirect:/lms/index";
//		  }
	 
	  // 학기 확인하기
	  boolean test = lectureService.getSemesterCheck();
	  
	  //수강신청 기간이 아닐 시
	  if(!test) {
		  log.debug(TeamColor.KHJ + "수강신청 일자가 아닙니다 -- "+ test );
		  
		  //수강신청 오류 페이지로 보내기
// 		  return "/sign/outOfSignDate";
	  }
	  log.debug(TeamColor.KHJ + "test -- "+ test );
 	  
	  
	  
	  //페이징 용 파라미터값 디버깅
	  log.debug(TeamColor.YHW + "-- currentPage - controller -- "+ currentPage ); 
	  
	  // 페이징 변수 설정
	  Map<String, Object> pageVariable = PageUtil.pageVariable(currentPage, lectureService.getTotal());
	  
	  // 개설강좌 목록 불러오기
	  List<Map<String,Object>> lectureList = lectureService.selectLectureListForSign((int)pageVariable.get("beginRow"),(int)pageVariable.get("rowPerPage"));
	 
	  // 개설강좌 목록 확인
 	  log.debug(TeamColor.YHW + "-- lectureList - controller -- "+ lectureList );
 	  
 	  //페이징 넘겨주는 값
 	  model.addAttribute("pages",pageVariable.get("pages"));
 	  model.addAttribute("currentPage",pageVariable.get("currentPage"));
 	  model.addAttribute("realLastPage",pageVariable.get("realLastPage"));
 	  
 	
 	  // 강좌 목록
 	  model.addAttribute("lectureList",lectureList);
	  
 	  
 	  
 	  
	  // 수강신청 리스트
 	  String userId = ((User)session.getAttribute("loginUser")).getUserId();
 	  sign.setUserId(userId);
 	  List<Map<String,Object>> singList = lectureService.signList(sign);
 	  // 수강신청 목록 확인
 	  log.debug(TeamColor.YHW + singList + "-- addSign-controller");
 	  // model에 담아 전달
 	  model.addAttribute("singList",singList);
 	  
 	  
 	  // 수강 취소 리스트 
 	  signCancel.setUserId(userId);
 	  List<Map<String,Object>> cancelSignList = lectureService.selectCancelSignList(signCancel);
	   //디버깅
	  log.debug(TeamColor.YHW + "-- cancelSignList-controller -- "+ cancelSignList );
	   
	  // view에 전달
	  model.addAttribute("cancelSignList",cancelSignList);
	  
	  //신청 학점 조회
	  int signTime = lectureService.getSignTime(sign);
	  //신청 학점 model에 담기
 	  model.addAttribute("signTime",signTime);
	  
 	  // 포워딩
      return "/sign/openLectureList";
	  }
  
  
  
  
  
	  // 수강신청 추가
  	   @GetMapping("/sign/addSign")
  	   public String insertLecture(Sign sign, HttpSession session) {
		   // 수강신청 추가 내용
		   log.debug(TeamColor.YHW + "-- sign-controller -- "+ sign );
		   int addSign = lectureService.addSign(sign);
		   // 수강신청 등록 확인
		   log.debug(TeamColor.YHW + addSign + "-- addSign-controller");
		   // 포워딩
		   return "redirect:/sign/openLectureList";
  	   } 
  	   
  	   
	  // 수강신청 취소 이력 추가
	   @GetMapping("/sign/cancelSign") 
	   public String cancelSign(SignCancel signCancel, HttpSession session, Model model) {
		   String userId = ((User)session.getAttribute("loginUser")).getUserId();
		   signCancel.setUserId(userId);
		   int addSignCancel = lectureService.addCancleSign(signCancel);
		   //디버깅
		   log.debug(TeamColor.YHW + "-- addSignCancel-controller -- "+ addSignCancel );
		   //디버깅
		   int lectureNo = signCancel.getLectureNo();
		   int signNo = signCancel.getSignNo();
		   // 포워딩
		   return "redirect:/sign/removeSign?lectureNo="+lectureNo+"&signNo="+signNo+"&userId="+userId+"&cancelId"+userId;
	   }

	   
	   // 수강신청 취소(수강 신청 list에서 제거)
	   @GetMapping("/sign/removeSign")
	   public String removeSign(Sign sign, SignCancel signCancel, HttpSession session) {
		   String userId = ((User)session.getAttribute("loginUser")).getUserId();
		   sign.setUserId(userId);
		   int removeSign = lectureService.removeSign(sign); 
		   // 수강신청 목록
		   log.debug(TeamColor.YHW + "-- removeSign-controller -- "+ removeSign );
		   
		   int lectureNo = signCancel.getLectureNo();
		   int signNo = signCancel.getSignNo();
		   return "redirect:/sign/openLectureList?lectureNo="+lectureNo+"&signNo="+signNo+"&userId="+userId;
	   }
}