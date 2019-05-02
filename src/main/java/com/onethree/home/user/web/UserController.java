package com.onethree.home.user.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value="/user/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("UserVO") UserVO paramUserVO,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		long totalCount = userService.count(paramUserVO);
		
		List<UserVO> userList = userService.getUserList(paramUserVO);
		
		PagingUtil paging = new PagingUtil(totalCount,pageVO.getRowCount(),pageVO.getPage(),pageVO.getPageBlockCount(),"/user/list",pageVO.getSubParamStr());
		pageVO.setTotalCount(totalCount);
		
		model.addAttribute("userList", userList);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("paging", paging.getPaging());
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원목록");
		
		return "/user/list";
	}
	
	@RequestMapping(value="/user/write")
	public String write(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		UserVO userVO = new UserVO();
		
		model.addAttribute("mode", "write");
		model.addAttribute("userVO", userVO);
		model.addAttribute("pageVO", pageVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회원등록");
		
		return "/user/write";
	}
	
	@RequestMapping(value="/user/update")
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
		
		return "/user/write";
	}
	
	/**
	 * 계정관련 action
	 * */
	@RequestMapping(value="/user/action")
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
		
		if("write".equals(mode)){//=========================================================등록

			paramUserVO.setRegisterDt(new Date());
			paramUserVO.setRegisterId("admin");
			
			//비밀번호설정
			if(paramUserVO!=null && !"".equals(paramUserVO.getUserPass())){
				String userPass = paramUserVO.getUserPass();
				userPass = CommUtil.getEncSHA256(userPass); 
				paramUserVO.setUserPass(userPass);
			}
			
			String userUid = userService.writeUser(paramUserVO); 
			
			if(userUid!=null && !"".equals(userUid)){
				model.addAttribute("RESULT_CODE", "001");//이전페이지로
				model.addAttribute("RESULT_URL", "/user/list");//성공후 이동페이지
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
			oriUserVO.setUpdateId("admin");
			
			//비밀번호설정
			if(paramUserVO!=null && !"".equals(paramUserVO.getUserPass())){
				String userPass = paramUserVO.getUserPass();
				userPass = CommUtil.getEncSHA256(userPass); 
				oriUserVO.setUserPass(userPass);
			}
			
			userService.updateUser(oriUserVO);
			
			model.addAttribute("RESULT_CODE", "001");//이전페이지로
			model.addAttribute("RESULT_URL", "/user/update?userUid="+oriUserVO.getUserUid()+"&"+paramUserVO.getParamStr());//성공후 이동페이지
			
		}else if("delete".equals(mode)){
			
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		
		
		//액션결과페이지로
		return viewPage;
	}
	
}
