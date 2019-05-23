package com.onethree.home.user.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onethree.home.common.util.CommUtil;
import com.onethree.home.common.util.PagingUtil;
import com.onethree.home.common.vo.PageVO;
import com.onethree.home.user.service.UserService;
import com.onethree.home.user.vo.UserVO;

import net.sf.json.JSONObject;

@Controller
public class UserController {
	//메뉴 타이틀
	String contentTitle = "회원관리";
	//메뉴번호
	String contentNumb = "3";
	
	@Autowired
	private UserService userService; 
	
	//메뉴타이틀, 메뉴번호 지정
	public void setMenuTitle(ModelMap model,String contentSubTitle){
		model.addAttribute("CONTENT_TITLE", contentTitle);
		model.addAttribute("CONTENTSUB_TITLE", contentSubTitle);
		model.addAttribute("CONTENT_NUMB", contentNumb);
	}
	
	@RequestMapping(value="/manage/user/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("UserVO") UserVO paramUserVO,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		long totalCount = userService.count(paramUserVO);
		
		List<UserVO> userList = userService.getUserList(paramUserVO);
		
		PagingUtil paging = new PagingUtil(totalCount,pageVO.getRowCount(),pageVO.getPage(),pageVO.getPageBlockCount(),"/manage/user/list",pageVO.getSubParamStr());
		pageVO.setTotalCount(totalCount);
		
		model.addAttribute("userList", userList);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("paging", paging.getPaging());
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원목록");
		
		return "/manage/user/list";
	}
	
	@RequestMapping(value="/manage/user/write")
	public String write(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		UserVO userVO = new UserVO();
		
		model.addAttribute("mode", "write");
		model.addAttribute("userVO", userVO);
		model.addAttribute("pageVO", pageVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원등록");
		
		return "/manage/user/write";
	}
	
	/**
	 * 사용자 회원가입
	 * */
	@RequestMapping(value="/user/join")
	public String userJoin(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception 
	{
		
		UserVO userVO = new UserVO();
		
		model.addAttribute("mode", "join");
		model.addAttribute("userVO", userVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원가입");
		
		return "/user/join";
	}
	
	@RequestMapping(value="/manage/user/update")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("UserVO") UserVO paramUserVO,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		if(paramUserVO==null || "".equals(paramUserVO.getUserUid())){
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return "common/actionResult";
		}
		
		UserVO userVO = userService.getUserVO(paramUserVO);
		
		model.addAttribute("mode", "update");
		model.addAttribute("userVO", userVO);
		model.addAttribute("pageVO", pageVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원수정");
		
		return "/manage/user/write";
	}
	
	/**
	 * 계정관련 action
	 * */
	@RequestMapping(value="/manage/user/action")
	public String action(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("UserVO") UserVO paramUserVO,ModelMap model) throws Exception {
		
		String viewPage = "common/actionResult";
		String mode = "";
		
		if(request.getParameter("mode")!=null && !"".equals(request.getParameter("mode"))){
			mode = request.getParameter("mode");
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return viewPage;
		}
		
		HttpSession session  = request.getSession(true);
		UserVO loginUserVO = (UserVO)session.getAttribute("loginUserVO");
		if(loginUserVO==null || "".equals(loginUserVO.getUserId())){
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return viewPage;
		}else {
			if(loginUserVO.getUserAuthor()<9) {//관리자만 가능
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "권한이 없습니다.");//이전페이지로
				return viewPage;
			}
		}
		
		if("write".equals(mode)){//=========================================================등록

			paramUserVO.setRegisterDt(new Date());
			paramUserVO.setRegisterId(loginUserVO.getUserId());
			
			//비밀번호설정
			if(paramUserVO!=null && !"".equals(paramUserVO.getUserPass())){
				String userPass = paramUserVO.getUserPass();
				userPass = CommUtil.getEncSHA256(userPass); 
				paramUserVO.setUserPass(userPass);
			}
			
			String userUid = userService.writeUser(paramUserVO); 
			
			if(userUid!=null && !"".equals(userUid)){
				model.addAttribute("RESULT_CODE", "001");//이전페이지로
				model.addAttribute("RESULT_URL", "/manage/user/list");//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
			
		}else if("update".equals(mode)){//=========================================================수정
			if(paramUserVO==null || "".equals(paramUserVO.getUserUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return "common/actionResult";
			}
			
			UserVO oriUserVO = userService.getUserVO(paramUserVO);
			
			oriUserVO.setStateNum(paramUserVO.getStateNum());
			oriUserVO.setUserName(paramUserVO.getUserName());
			oriUserVO.setUserAuthor(paramUserVO.getUserAuthor());
			oriUserVO.setUserId(paramUserVO.getUserId());
			oriUserVO.setMoneyFlag(paramUserVO.getMoneyFlag());
			oriUserVO.setUpdateDt(new Date());
			oriUserVO.setUpdateId(loginUserVO.getUserId());
			
			//비밀번호설정
			if(paramUserVO!=null && !"".equals(paramUserVO.getUserPass())){
				String userPass = paramUserVO.getUserPass();
				userPass = CommUtil.getEncSHA256(userPass); 
				oriUserVO.setUserPass(userPass);
			}
			
			userService.updateUser(oriUserVO);
			
			model.addAttribute("RESULT_CODE", "001");//이전페이지로
			model.addAttribute("RESULT_URL", "/manage/user/update?userUid="+oriUserVO.getUserUid()+"&"+paramUserVO.getParamStr());//성공후 이동페이지
			
		}else if("delete".equals(mode)){
			if(paramUserVO==null || "".equals(paramUserVO.getUserUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return "common/actionResult";
			}
			
			UserVO oriUserVO = userService.getUserVO(paramUserVO);
			if(oriUserVO==null || "".equals(oriUserVO.getUserUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "회원정보가 없습니다.");//이전페이지로
				return "common/actionResult";
			}
			
			userService.deleteUser(oriUserVO);
			
			model.addAttribute("RESULT_CODE", "003");//성공메시지 출력후 이동
			model.addAttribute("RESULT_MSG", "삭제처리되었습니다.");
			model.addAttribute("RESULT_URL", "/manage/user/list");//성공후 이동페이지
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		//액션결과페이지로
		return viewPage;
	}
	
	/**
	 * 사용자 계정관련 action
	 * */
	@RequestMapping(value="/user/action")
	public String userAction(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("UserVO") UserVO paramUserVO,ModelMap model) throws Exception {
		
		String viewPage = "common/actionResult";
		String mode = "";
		
		if(request.getParameter("mode")!=null && !"".equals(request.getParameter("mode"))){
			mode = request.getParameter("mode");
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return viewPage;
		}
		
		
		
		if("join".equals(mode)){//=========================================================회원가입
			
			if(paramUserVO==null || "".equals(paramUserVO.getUserId()) 
					|| "".equals(paramUserVO.getUserName()) || "".equals(paramUserVO.getUserPass())) {
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "필수값이 없습니다.");//이전페이지로
				return viewPage;
			}
			
			paramUserVO.setUserAuthor(1);//준회원
			paramUserVO.setStateNum(0);//신청
			paramUserVO.setRegisterDt(new Date());
			paramUserVO.setRegisterId(paramUserVO.getUserId());
			
			//비밀번호설정
			if(paramUserVO!=null && !"".equals(paramUserVO.getUserPass())){
				String userPass = paramUserVO.getUserPass();
				userPass = CommUtil.getEncSHA256(userPass); 
				paramUserVO.setUserPass(userPass);
			}
			
			String userUid = userService.writeUser(paramUserVO); 
			
			if(userUid!=null && !"".equals(userUid)){
				model.addAttribute("RESULT_CODE", "003");//성공후 메시지 출력
				model.addAttribute("RESULT_MSG", "가입신청되었습니다. \\n가입승인후 로그인 가능합니다.");//성공후 메시지 출력
				model.addAttribute("RESULT_URL", "/");//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "가입신청에 실패하였습니다.");//이전페이지로
			}
			
		}else if("update".equals(mode)){//=========================================================수정
			
			
		}else if("delete".equals(mode)){
			
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		//액션결과페이지로
		return viewPage;
	}
	
	/**
	 * 계정 아이디 카운트 조회 json반환
	 * */
	@RequestMapping(value="/user/loginIdCheck")
	public String loginIdCheck(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
		
		String loginId = "";
		long cnt = 0;
		if(request.getParameter("loginId")!=null && !"".equals(request.getParameter("loginId"))){
			loginId = request.getParameter("loginId");
		}
				
		UserVO userVO = new UserVO();
		userVO.setUserId(loginId);
		cnt = userService.getLoginUserIdCount(userVO);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cnt", cnt);
		
		model.addAttribute("jsonObj", jsonObj);
		
		return "common/json";
	}
}
