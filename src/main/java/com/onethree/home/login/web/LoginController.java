package com.onethree.home.login.web;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onethree.home.common.util.CommUtil;
import com.onethree.home.money.service.MoneyService;
import com.onethree.home.money.vo.MoneyVO;
import com.onethree.home.user.service.UserService;
import com.onethree.home.user.vo.UserVO;

@Controller
public class LoginController {

	protected Log log = LogFactory.getLog(this.getClass());
	
	//메뉴 타이틀
	String contentTitle = "로그인";
	//메뉴번호
	String contentNumb = "1";
	
	@Autowired
	private UserService userService; 
	
	//메뉴타이틀, 메뉴번호 지정
	public void setMenuTitle(ModelMap model,String contentSubTitle){
		model.addAttribute("CONTENT_TITLE", contentTitle);
		model.addAttribute("CONTENTSUB_TITLE", contentSubTitle);
		model.addAttribute("CONTENT_NUMB", contentNumb);
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception 
	{
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"로그인");
		
		return "/login/login";
	}
	
	
	/**
	 * 로그인 action 처리
	 * */
	@RequestMapping(value="/login/action")
	public String action(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
		
		String viewPage = "common/actionResult";
		String mode = "";
		String returnUrl = "/";
		
		if(request.getParameter("returnUrl")!=null && !"".equals(request.getParameter("returnUrl"))){
			returnUrl = request.getParameter("returnUrl");
		}
		
		if(request.getParameter("mode")!=null && !"".equals(request.getParameter("mode"))){
			mode = request.getParameter("mode");
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");
			return viewPage;
		}
		
		if("login".equals(mode)){//=========================================================등록
			String loginId = "";
			String loginPassword = "";
			boolean paramFlag = true;
			if(request.getParameter("loginId")!=null && !"".equals(request.getParameter("loginId"))) {
				loginId = request.getParameter("loginId");
			}else {
				paramFlag = false;
			}
			if(request.getParameter("loginPassword")!=null && !"".equals(request.getParameter("loginPassword"))) {
				loginPassword = request.getParameter("loginPassword");
			}else {
				paramFlag = false;
			}
			
			if(!paramFlag){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "아이디와 비밀번호 정보를 정확하게 입력해주세요.");
				return viewPage;
			}
			 
			UserVO loginUserVOTemp = new UserVO();
			loginUserVOTemp.setUserId(loginId);
			
			String userPass = loginPassword;
			userPass = CommUtil.getEncSHA256(userPass); 
			loginUserVOTemp.setUserPass(userPass);
			
			UserVO loginUserVO = userService.getLoginUser(loginUserVOTemp);
			if(loginUserVO!=null && loginUserVO.getUserId()!=null && !"".equals(loginUserVO.getUserId())) {
				//로그인정보 세션설정 처리
				HttpSession session  = request.getSession(false);
				if(session != null)
				{
					session.invalidate();
				}
				loginUserVO.setUserPass("");//비밀번호는 초기화
				
				session  = request.getSession(true);
				session.setAttribute("loginUserVO", loginUserVO);
				
				log.info(loginUserVO.getUserId()+" ========================== USER LOGIN SUCCESS!!!!!!!!!!!!!!!!!!!!");
				
				model.addAttribute("RESULT_CODE", "010");//로그인성공
				model.addAttribute("RESULT_URL", returnUrl);//성공후 이동페이지
			}else {
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "아이디와 비밀번호 정보를 정확히 입력해주세요.");
			}
		}else if("logout".equals(mode)){
			//로그인 세션 삭제
			HttpSession session  = request.getSession(true);
			session.removeAttribute("loginUserVO");
			
			model.addAttribute("RESULT_CODE", "010");
			model.addAttribute("RESULT_URL", returnUrl);//성공후 이동페이지
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");
		}
		
		//액션결과페이지로
		return viewPage;
	}
}
