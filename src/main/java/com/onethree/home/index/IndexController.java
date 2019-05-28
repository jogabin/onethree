package com.onethree.home.index;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {
	
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class.getName());

	
	//메뉴 타이틀
	String contentTitle = "onethree";
	//메뉴번호
	String contentNumb = "";
	
	//메뉴타이틀, 메뉴번호 지정
	public void setMenuTitle(ModelMap model,String contentSubTitle){
		model.addAttribute("CONTENT_TITLE", contentTitle);
		model.addAttribute("CONTENTSUB_TITLE", contentSubTitle);
		model.addAttribute("CONTENT_NUMB", contentNumb);
	}
	
	@RequestMapping(value="/index")
	public String requestResult(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception {
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"Main");
		
		LOGGER.info("========================== MAIN INFO =======================================");
		
		return "main/main";
	}
	

}
