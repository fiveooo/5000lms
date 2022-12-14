package com.gd.lms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gd.lms.commons.PageUtil;
import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.IMypageService;
import com.gd.lms.service.IUserListService;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserListController {
	@Autowired IUserListService userService;
	
	@Autowired IMypageService mypageService;
	//유저 리스트
	@GetMapping("/user/userList")
	public String userList(Model model,
			@RequestParam(value="currentPage", required=false) Integer paramcurrentPage,
			@RequestParam(value="rowPerPage", required=false) Integer ParamRowPerPage) {
		
//		  // 로그인 상태가 아니면 로그인페이지
//		  if(session.getAttribute("user") == null) { 
//			  return "redirect:/lms/user/login";
//		  }
//		  // 권한이 운영자가 아니면 인덱스 페이지
//		  else if (Integer.parseInt((String) session.getAttribute("level")) == 1 || Integer.parseInt((String) session.getAttribute("level")) == 2) {
//			  return "redirect:/lms/index";
//		  }
		
	
		//페이징
		int currentPage = 1;
			
			if(paramcurrentPage != null) {
				currentPage = paramcurrentPage;
				log.debug(TeamColor.JCH + " 현재페이지 currentPage" + currentPage);
			}
			
			model.addAttribute("currentPage",currentPage);
			
		int rowPerPage = 10;
			if(ParamRowPerPage !=null) {
				rowPerPage = ParamRowPerPage;
				log.debug(TeamColor.JCH + "보여지는 페이지 rowPerPage " + rowPerPage);
			}
			
		//마지막 페이지
		int total = userService.lastPage();
		int lastPage =0;
		
		lastPage = total / rowPerPage;
		if( total % rowPerPage != 0) {
			lastPage+=1;
			System.out.println(total);
		}
		model.addAttribute("lastPage" , lastPage);
		
		//리스트 불러오기
		List<User> list = userService.selectUserList(currentPage , rowPerPage);
		model.addAttribute("list", list);
		
		log.debug(TeamColor.JCH + this.getClass()  + " 유저 리스트 출력 ");

		//페이징
		
		return "user/userList";
	}
	
	//유저 활성화값 변경 - 전체보기 리스트에서
	@PostMapping("/user/userList")
	public String updateUserActive(User user) {
		//유저 계정승인여부를 Y , N로 
		
		int row= userService.updateUserActive(user);
		
		return "redirect:/user/userList";
	}
	
	//승인대기 유저 리스트 페이지
	@GetMapping("/user/waitUser")
	
	public String waitUserList(Model model,
			@RequestParam(value="currentPage", required=false) Integer paramcurrentPage,
			@RequestParam(value="rowPerPage", required=false) Integer ParamRowPerPage){
		
//		  // 로그인 상태가 아니면 로그인페이지
//		  if(session.getAttribute("user") == null) { 
//			  return "redirect:/lms/user/login";
//		  }
//		  // 권한이 운영자가 아니면 인덱스 페이지
//		  else if (Integer.parseInt((String) session.getAttribute("level")) == 1 || Integer.parseInt((String) session.getAttribute("level")) == 2) {
//			  return "redirect:/lms/index";
//		  }
		
		//페이징
		int currentPage = 1;
			
			if(paramcurrentPage != null) {
				currentPage = paramcurrentPage;
				log.debug(TeamColor.JCH + " 현재페이지 currentPage" + currentPage);
			}
			
			model.addAttribute("currentPage",currentPage);
			
		int rowPerPage = 10;
			if(ParamRowPerPage !=null) {
				rowPerPage = ParamRowPerPage;
				log.debug(TeamColor.JCH + "보여지는 페이지 rowPerPage " + rowPerPage);
			}
			
		//마지막 페이지
		int total = userService.lastPageWaitUser();
		int lastPage =0;
		
		lastPage = total / rowPerPage;
		if( total % rowPerPage != 0) {
			lastPage+=1;
			System.out.println(total);
		}
		model.addAttribute("lastPage" , lastPage);
		
		//리스트 불러오기
		List<User> list = userService.selectWaitUserList(currentPage , rowPerPage);
		model.addAttribute("list", list);
		
		log.debug(TeamColor.JCH + this.getClass()  + " 승인 대기 유저 리스트 출력 ");

		//페이징
		
		return "user/waitUser";
	}
	
	//유저 활성화값 변경 - 대기유저 리스트에서
	@PostMapping("/user/waitUser")
	public String updateWaitUser(User user) {
		//유저 계정승인여부를 Y , N로 

		int row= userService.updateUserActive(user);

		
		return "redirect:/user/waitUser";
	}
	
	//승인완료 유저 리스트 페이지
	@GetMapping("/user/yesUser")
	
	public String yesUserList(Model model,
			@RequestParam(value="currentPage", required=false) Integer paramcurrentPage,
			@RequestParam(value="rowPerPage", required=false) Integer ParamRowPerPage){
//		  // 로그인 상태가 아니면 로그인페이지
//		  if(session.getAttribute("user") == null) { 
//			  return "redirect:/lms/user/login";
//		  }
//		  // 권한이 운영자가 아니면 인덱스 페이지
//		  else if (Integer.parseInt((String) session.getAttribute("level")) == 1 || Integer.parseInt((String) session.getAttribute("level")) == 2) {
//			  return "redirect:/lms/index";
//		  }
		
		//페이징
		int currentPage = 1;
			
			if(paramcurrentPage != null) {
				currentPage = paramcurrentPage;
				log.debug(TeamColor.JCH + " 현재페이지 currentPage" + currentPage);
			}
			
			model.addAttribute("currentPage",currentPage);
			
		int rowPerPage = 10;
			if(ParamRowPerPage !=null) {
				rowPerPage = ParamRowPerPage;
				log.debug(TeamColor.JCH + "보여지는 페이지 rowPerPage " + rowPerPage);
			}
			
		//마지막 페이지
		int total = userService.lastPageYesUser();
		int lastPage =0;
		
		lastPage = total / rowPerPage;
		if( total % rowPerPage != 0) {
			lastPage+=1;
			System.out.println(total);
		}
		model.addAttribute("lastPage" , lastPage);
		
		//리스트 불러오기
		List<User> list = userService.selectYesUserList(currentPage , rowPerPage);
		model.addAttribute("list", list);
		
		log.debug(TeamColor.JCH + this.getClass()  + " 승인 대기 유저 리스트 출력 ");

		//페이징
		
		return "user/yesUser";
	}
	
	//유저 활성화값 변경 - 완료유저 리스트에서
		@PostMapping("/user/yesUser")
		public String updateYesUser(User user) {
			//유저 계정승인여부를 Y , N로 

			int row= userService.updateUserActive(user);

			
			return "redirect:/user/yesUser";
		}
		
		//승인완료 유저 리스트 페이지
		@GetMapping("/user/hUser")
		
		public String hUserList(Model model,
				@RequestParam(value="currentPage", required=false) Integer paramcurrentPage,
				@RequestParam(value="rowPerPage", required=false) Integer ParamRowPerPage){
//			  // 로그인 상태가 아니면 로그인페이지
//			  if(session.getAttribute("user") == null) { 
//				  return "redirect:/lms/user/login";
//			  }
//			  // 권한이 운영자가 아니면 인덱스 페이지
//			  else if (Integer.parseInt((String) session.getAttribute("level")) == 1 || Integer.parseInt((String) session.getAttribute("level")) == 2) {
//				  return "redirect:/lms/index";
//			  }
			
			//페이징
			int currentPage = 1;
				
				if(paramcurrentPage != null) {
					currentPage = paramcurrentPage;
					log.debug(TeamColor.JCH + " 현재페이지 currentPage" + currentPage);
				}
				
				model.addAttribute("currentPage",currentPage);
				
			int rowPerPage = 10;
				if(ParamRowPerPage !=null) {
					rowPerPage = ParamRowPerPage;
					log.debug(TeamColor.JCH + "보여지는 페이지 rowPerPage " + rowPerPage);
				}
				
			//마지막 페이지
			int total = userService.lastPageHUser();
			int lastPage =0;
			
			lastPage = total / rowPerPage;
			if( total % rowPerPage != 0) {
				lastPage+=1;
				System.out.println(total);
			}
			model.addAttribute("lastPage" , lastPage);
			
			//리스트 불러오기
			List<User> list = userService.selectHUserList(currentPage , rowPerPage);
			model.addAttribute("list", list);
			
			log.debug(TeamColor.JCH + this.getClass()  + " 승인 대기 유저 리스트 출력 ");

			//페이징
			
			return "user/hUser";
		}
		
		//유저 활성화값 변경 - 완료유저 리스트에서
			@PostMapping("/user/hUser")
			public String hYesUser(User user) {
				//유저 계정승인여부를 Y , N로 

				int row= userService.updateUserActive(user);

				
				return "redirect:/user/yesUser";
			}
	
	
	//비밀번호 변경 페이지. userController로 이동시켜도 무방.
	@GetMapping("/user/updatePw")
	public String updatePw(HttpSession session, Model model) {
		//사용자 정보가 없는 사람이 마이페이지 갈경우
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("errMsg","로그인 후 이용 가능합니다");
			return "index/login";
		}
		
		return "/user/updatePw";
	}
	
}
