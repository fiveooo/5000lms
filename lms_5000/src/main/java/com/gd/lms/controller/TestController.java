package com.gd.lms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.IBoardService;
import com.gd.lms.service.ITestService;
import com.gd.lms.vo.Answer;
import com.gd.lms.vo.MultiChoice;
import com.gd.lms.vo.Question;
import com.gd.lms.vo.Test;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {
	@Autowired ITestService testService;
	@Autowired IBoardService boardService;
	

	
	

	
	//시험 게시판 폼 전송 메서드
	@GetMapping ("/test/board")
	public String testBoard(HttpSession session, int lectureNo, Model model) {

		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
				
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / lectureNo : "+ lectureNo );
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / user : "+ (User)session.getAttribute("loginUser") );
		

		
		//세션 권한 받아오기
		int userLv = ((User)session.getAttribute("loginUser")).getUserLevel();
		
		//넘겨줄 값 세팅
		List<Map<String, Object>>  list = testService.getTestList(userLv, userId, lectureNo);
		
		//넘겨줄 값 세팅
		String lectureName  = boardService.getLectureName(lectureNo);
				
		
		//값 넘겨주기
		model.addAttribute("testList",list);
		model.addAttribute("lectureNo", lectureNo);
		model.addAttribute("lectureName", lectureName);
		
		
		
		//리턴
		return "test/testBoard";
	}
	
	
	@GetMapping ("/test/page")
	public String testPage(HttpSession session, int testNo, int lectureNo, Model model) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / testNo : "+ testNo );
		
		
		//넘겨줄 값 세팅
		List<Question>  list = testService.getTestQuestionList(testNo);
		List<MultiChoice>  list2 = testService.getTestChoiceList(testNo);
		
		//값 넘겨주기
		model.addAttribute("questionList",list);
		model.addAttribute("choiceList",list2);
		model.addAttribute("testNo",testNo);
		model.addAttribute("lectureNo",lectureNo);
		
		
		
		
		return"test/testPage";
	}
	
	//시험 생성 폼으로 보내기
	@GetMapping("test/addTest")
	public String directAddTestForm(HttpSession session, int lectureNo, Model model) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//값 넘겨주기
		model.addAttribute("lectureNo", lectureNo);
		
		//바로 포워딩
		return "test/addTest";
	}
	
	
	//시험 추가 메서드
	@PostMapping("test/addTest")
	public String addTest(Test test, Question question, MultiChoice multichoice) {
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / test : "+ test );
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / question : "+ question );
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / multichoice : "+ multichoice );
		
		int row = testService.addTest(test, question, multichoice);

		//결과 값 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 값 확인 / row : "+ row );
				
		//리턴
		return "redirect:/test/board?lectureNo=" + test.getLectureNo();
	}
	
	//시험 장 입장
	@GetMapping ("test/enter")
	public String testEnter(HttpSession session, int testNo, int lectureNo) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / testNo : "+ testNo );
		
		
		//응시여부 확인
		boolean check = testService.testCheck(userId, testNo);
		
		//결과 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / check : "+ check );
				
		
		if(check) { //true(응시한 적이 있을 경우) - 시험 거부
			//리턴
			return "redirect:/test/board?lectureNo=" + lectureNo;
		} else { //false(응시한 적이 없을 경우) - 시험장 입장
			//리턴
			return "redirect:/test/page?testNo=" + testNo + "&lectureNo=" + lectureNo;
		}
		
	}
	
	//시험 응시
	@PostMapping("test/submit")
	public String testSubmit(HttpSession session, int testNo, Answer answer, int lectureNo) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//결과 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / testNo : "+ testNo );
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / answer : "+ answer );
		
		
		//배열 갑 받아오기
		int [] answers = answer.getAnswerSelects();
		int [] questions = answer.getQuestionNos();
		
		//실행
		int row = testService.testSubmit(userId, testNo, answers, questions);
				

		//결과 값 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 값 확인 / row : "+ row );
		
		
		//리턴
		return "redirect:/test/board?lectureNo="+lectureNo;
	}
	
	
	//응시 학생 리스트 폼으로 가기
	@GetMapping("test/student")
	public String testSutdentForm(HttpSession session, int lectureNo, int testNo, Model model) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / lectureNo : " + lectureNo);
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / TestNo : " + testNo);
		
		
		
		//값 확인
		List<Map<String, Object>> list = testService.getTestStudnet(lectureNo, testNo);
		
		
		//값 넘겨주기
		model.addAttribute("testStudentList", list);
		model.addAttribute("testNo", testNo);
		model.addAttribute("lectureNo", lectureNo);
		
		
		//리턴
		return "test/testStudent";
	}
	
	
	//시험 채점
	@GetMapping("test/score")
	public String testScore(HttpSession session, int testNo, int lectureNo) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / lectureNo : " + lectureNo);
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / TestNo : " + testNo);
		
		//서비스 실행
		int row= testService.updateScore(testNo);
		
		//값 
		log.debug(TeamColor.KHJ + "실행 값 확인 / row : " + row);
		
		//리턴값
		return "redirect:/test/student?testNo="+testNo+"&lectureNo="+lectureNo;
	}

	//시험 수정
	@GetMapping("test/modify/form")
	public String directTestModifyForm(HttpSession session, int testNo, int lectureNo, Model model){
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / lectureNo : " + lectureNo);
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / TestNo : " + testNo);
		
		
		//넘겨줄 값 세팅
		List<Question>  list = testService.getTestQuestionList(testNo);
		List<MultiChoice>  list2 = testService.getTestChoiceList(testNo);
		
		//값 넘겨주기
		model.addAttribute("questionList",list);
		model.addAttribute("choiceList",list2);
		model.addAttribute("testNo",testNo);
		model.addAttribute("lectureNo",lectureNo);

		
		return "test/testModifyForm";
	}
	
	@GetMapping("test/modify/form2")
	public String directTestModifyForm2(HttpSession session, int testNo, int lectureNo, int questionNo, Model model) {
		//로그인이 안되어있을 경우 로그인 창으로 보내기
		if(session.getAttribute("loginUser") == null) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 / 로그인 창을 포워딩");			
			
			//리턴
			return "redirect:/index/login";			
		}
		
		//세션 아이디 받아오기
		String userId = ((User)session.getAttribute("loginUser")).getUserId();
		
		//수강생이 아닐 경우 / 해당 강좌의 교사가 아닐 경우 인덱스로 보내기
		if(testService.getSignNo(userId, lectureNo) == 0&&!(testService.getProId(lectureNo).equals(userId))) {
		
			//파라미터 값 확인 디버깅
			log.debug(TeamColor.KHJ + "로그인 세션 확인 불가 userId / " +userId);			
		
			//리턴
			return "redirect:/index";	
		}
		
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / questionNo : " + questionNo);
		
		
		//쿼리 실행
		List<Map<String, Object>> list = testService.getTestModifyForm(questionNo);
				
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 값 확인 / list : " + list);
		
		//값 넘기기
		model.addAttribute("questionList", list);
		model.addAttribute("testNo", testNo);
		model.addAttribute("lectureNo", lectureNo);
		
		
		return "test/testModifyFormDetail";
	}
	
	@PostMapping("test/modifyTest")
	public String modifyTest(int testNo, int lectureNo, MultiChoice multiChoice, Question question) {
		//파라미터 값 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / question : " + question);
		log.debug(TeamColor.KHJ + "파라미터 값 확인 / multiChoice : " + multiChoice);
		
		//배열 추출
		String []  choiceContents = multiChoice.getChoiceContents();
		
		//추출 배열 값 확인 디버깅
		log.debug(TeamColor.KHJ + "배열 값 확인 / choiceContents : " + Arrays.toString(choiceContents));
				
		
		//서비스 실행
		int row = testService.modifyQuestion(choiceContents, question);
		
		//리턴
		return "redirect:/test/modify/form?testNo="+testNo+"&lectureNo="+lectureNo;
	}
	
	
}
