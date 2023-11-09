package com.naver.phone.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.naver.phone.dao.PhoneDao;
import com.naver.phone.dto.PhoneDto;

@Controller
@RequestMapping("/phone")
public class PhoneWebController {

	JSONObject jsoob = new JSONObject();
	JSONArray jsoarr = new JSONArray();

	PhoneDao phoneDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public PhoneWebController(PhoneDao phoneDao) {
		this.phoneDao = phoneDao;
	}

	@GetMapping("/main")
	public String listMem(Model model, ServletRequest request) {
		ArrayList<PhoneDto> list = null;
//		JSONObject jsodata = new JSONObject();

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String userid = (String) session.getAttribute("userid");

		try {
			list = phoneDao.getAll(userid);
			model.addAttribute("memlist", list);
			model.addAttribute(userid);
		} catch (Exception e) {
			logger.warn("전체조회 실패");
			model.addAttribute("error", "전체 조회하기를 실패하였습니다");
		}

		createJson(list, userid);

		return "main";
	}

	@PostMapping("/addSuccess")
	public String add(PhoneDto phoneDto, Model model, ServletRequest request) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String userid = (String) session.getAttribute("userid");

		try {
			phoneDao.addMem(phoneDto, userid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("추가과정 실패");
			model.addAttribute("error", "회원 추가를 실패하였습니다");
		}

		return "redirect:/phone/main";
	}

	@GetMapping("/delete/{mbid}")
	public String delete(@PathVariable int mbid, Model model) {

		try {
			phoneDao.delMem(mbid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("삭제과정 실패");
			model.addAttribute("error", "회원 삭제를 실패하였습니다");
		}

		return "redirect:/phone/main";

	}

	@PostMapping("/updateSuccess")
	public String updateSuccess(PhoneDto phoneDto, Model model,
			ServletRequest request) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String userid = (String) session.getAttribute("userid");
		System.out.println("dddddd");
			
		try {
			phoneDao.updateMem(phoneDto.getMbid(), phoneDto, userid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("수정과정 실패");
			model.addAttribute("error", "<script>alert('회원 수정을 실패하였습니다');</script>");
		}
		
		return "redirect:/phone/main";

	}
	

	@GetMapping("/search")
	public String search(@RequestParam(value = "searchInput") String searchInput, Model model, ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String userid = (String) session.getAttribute("userid");

		System.out.println(searchInput);
		
		ArrayList<PhoneDto> list;
		try {
			list = phoneDao.searchMem(searchInput, userid);
			model.addAttribute("memlist", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("검색과정 실패");
			model.addAttribute("error", "회원 검색을 실패하였습니다");
		}

		return "main2";
	}

	@GetMapping("/form/{mbid}/{mbnm}/{mbph}/{mbad}/{groupName}")
	public String form(@PathVariable int mbid, @PathVariable String mbnm, @PathVariable String mbph,
			@PathVariable String mbad, @PathVariable String groupName, Model model) {

		System.out.println(mbnm);
		System.out.println(mbph);

//		model.addAttribute("memlist", phoneDto);
		model.addAttribute("mbid", mbid);

		return "update";
	}

	@GetMapping("/add")
	public String add() {

		return "add";
	}

	public void createJson(ArrayList<PhoneDto> list, String userid) {
		// Creating a JSONObject object
		JSONObject jsonOob = new JSONObject();
		JSONArray jsoarr = new JSONArray();
		// Inserting key-value pairs into the json object
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("mbid", list.get(i).getMbid());
			jsonObject.put("mbnm", list.get(i).getMbnm());
			jsonObject.put("mbph", list.get(i).getMbph());
			jsonObject.put("mbad", list.get(i).getMbad());
			jsonObject.put("groupName", list.get(i).getGroupName());

			jsoarr.add(i, jsonObject);
		}
		jsonOob.put(userid, jsoarr);

		try {
			FileWriter file = new FileWriter(
					"C:\\Users\\User\\Desktop\\big16\\spring_dev\\workspace\\phonebook\\src\\main\\resources\\static\\json\\output.json");
			file.write(jsonOob.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("JSON file created: " + jsonOob);
	}

}
