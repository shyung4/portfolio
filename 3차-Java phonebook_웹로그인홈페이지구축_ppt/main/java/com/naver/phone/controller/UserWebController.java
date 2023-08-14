package com.naver.phone.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.phone.dao.UserDao;
import com.naver.phone.dto.UserDto;

@Component
@RequestMapping("/user")
public class UserWebController {

	JSONObject jso = new JSONObject();

	UserDao userDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserWebController(UserDao userDao) {
		this.userDao = userDao;
	}

	
	@GetMapping("/signupPage")
	public String signupPage() {
		return "signupPage";
	}
	
	@ResponseBody
	@RequestMapping("/IdCheckService")
	public int IdCheckService(HttpServletRequest request, Model model) {
		ArrayList<UserDto> originUsers = null;
		
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		System.out.println(userid);
		System.out.println(userpw+"--------");
		
		int idCheck=0;
		try {
			originUsers = userDao.getUser();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "에러발생");
		}
		
		Iterator<UserDto> it = originUsers.iterator();

		while (it.hasNext()) {
			UserDto u = it.next();
			if (userid.equals(u.getUserid()) && userpw.equals(u.getUserpw())) { //아이디,비번 둘다 일치
				idCheck++;
			}else { // 어느것 하나도 일치 하지 않을때
			}
		}
		
		System.out.println("IdCheckService의 idCheck는"+idCheck);
		model.addAttribute("idCheck",idCheck);
		return idCheck;
		
	}

	
	
	@ResponseBody
	@RequestMapping("/SignupService")
	public int SignupService(HttpServletRequest request, Model model) {
		
		String userid = request.getParameter("userid");
		System.out.println("SignupService"+userid);
		
		int idCheck=0;
		try {
			idCheck = userDao.checkId(userid);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "에러발생");
		}
		
		System.out.println("idCheck는"+idCheck);
		model.addAttribute("i",idCheck);
		return idCheck;
		
	}
	
	
	
	
	@PostMapping("/signupSuccess")
	public String signupSuccess(UserDto userDto, Model model) {
		
		
		try {
			userDao.addMem(userDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("회원가입 실패");
			model.addAttribute("error", "회원가입이 실패하였습니다");
		}

		return "redirect:/user/signupPage";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";

	}
	
	
	@GetMapping("/loginfail")
	public String loginfail(Model model, RedirectAttributes red) {
		red.addFlashAttribute("error", "<script>alert('접근할수 없는 페이지 입니다 로그인 하십시오');</script>");
//		model.addAttribute("error","<script>alert('접근할수 없는 페이지 입니다');</script>");
//        red.addFlashAttribute("error", "<script>alert('회원만 가능한 페이지입니다 회원가입을 하십시오');</script>");
		return "redirect:/user/login";

	}
	

	@PostMapping("/loginSuccess")
	public String loginSuccess(UserDto userDto, Model model, ServletRequest request, RedirectAttributes red) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		session.setAttribute("userid", userDto.getUserid());
		session.setAttribute("userpw", userDto.getUserpw());
//		 HttpSession logSession = request.getSession();
		ArrayList<UserDto> originUsers;

		try {
			originUsers = userDao.getUser();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("회원불러오기 에러");
			model.addAttribute("회원 불러오기에서 에러가 발생했는데");
			return "login";
		}

		Iterator<UserDto> it = originUsers.iterator();

		int i = 0;
		while (it.hasNext()) {
			UserDto u = it.next();
			if (userDto.getUserid().equals(u.getUserid()) && userDto.getUserpw().equals(u.getUserpw())) {
				System.out.println(userDto.getUserid() + ", " + userDto.getUserpw());
				System.out.println(u.getUserid() + ", " + u.getUserpw());
				i++;
			}
		}

		System.out.println("여기는 loginSuccess"+i);
		if (i != 0) {
			return "redirect:/phone/main";
		} else {
	        session.invalidate();
	        red.addFlashAttribute("error", "<script>alert('회원만 가능한 페이지입니다 회원가입을 하십시오');</script>");
			return "redirect:/user/signupPage";
		}
	}

	
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
		logger.info("logoutMainGET메서드 진입");
        
        HttpSession session = request.getSession();
        
        session.invalidate();
        
        return "redirect:/user/login"; 
        
    }
	
//	public void clearSession(ServletRequest request) throws Exception{
//		logger.info("logoutMainGET메서드 진입");
//        
//        HttpSession session = request.getSession();
//        
//        session.invalidate();
//	}
	
	
	
//	@GetMapping("/loginError")
//	public String loginError(RedirectAttributes red) {
//		System.out.println("아아");
//		red.addFlashAttribute("msg", "<script>alert('회원이 아니거나 비밀번호가 일치하지 않습니다')</script>");
//		return "redirect:/user/signupPage";
//	}

	@GetMapping("/front")
	public String front() {
		return "front";

	}
	

}
