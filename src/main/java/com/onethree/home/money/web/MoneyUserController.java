package com.onethree.home.money.web;

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

import com.onethree.home.common.util.PagingUtil;
import com.onethree.home.common.vo.PageVO;
import com.onethree.home.money.service.MoneyUserService;
import com.onethree.home.money.vo.MoneyUserVO;
import com.onethree.home.user.vo.UserVO;

@Controller
public class MoneyUserController {
	
	//메뉴 타이틀
	String contentTitle = "회비관리";
	//메뉴번호
	String contentNumb = "2";
	
	@Autowired
	private MoneyUserService moneyUserService;
	
	//메뉴타이틀, 메뉴번호 지정
	public void setMenuTitle(ModelMap model,String contentSubTitle){
		model.addAttribute("CONTENT_TITLE", contentTitle);
		model.addAttribute("CONTENTSUB_TITLE", contentSubTitle);
		model.addAttribute("CONTENT_NUMB", contentNumb);
	}
	
	@RequestMapping(value="/money/money_user/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("MoneyUserVO") MoneyUserVO paramMoneyUserVO,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		long totalCount = moneyUserService.count(paramMoneyUserVO);
		
		List<MoneyUserVO> moneyUserList = moneyUserService.getMoneyUserListPaging(paramMoneyUserVO);
		
		PagingUtil paging = new PagingUtil(totalCount,pageVO.getRowCount(),pageVO.getPage(),pageVO.getPageBlockCount(),"/money/money_user/list",pageVO.getSubParamStr());
		pageVO.setTotalCount(totalCount);
		
		model.addAttribute("list", moneyUserList);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("paging", paging.getPaging());
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회비회원 목록");
		
		return "/money/money_user/list";
	}
	
	@RequestMapping(value="/money/money_user/write")
	public String write(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		MoneyUserVO moneyUserVO = new MoneyUserVO();
		
		model.addAttribute("mode", "write");
		model.addAttribute("moneyUserVO", moneyUserVO);
		model.addAttribute("pageVO", pageVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회비회원 등록");
		
		return "/money/money_user/write";
	}
	
	@RequestMapping(value="/money/money_user/update")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("MoneyUserVO") MoneyUserVO paramMoneyUserVO,@ModelAttribute("PageVO") PageVO pageVO) throws Exception 
	{
		
		if(paramMoneyUserVO==null || "".equals(paramMoneyUserVO.getMoneyUserUid())){
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return "common/actionResult";
		}
		
		MoneyUserVO moneyUserVO = moneyUserService.getMoneyUserVO(paramMoneyUserVO);
		
		model.addAttribute("mode", "update");
		model.addAttribute("moneyUserVO", moneyUserVO);
		model.addAttribute("pageVO", pageVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"회비회원 수정");
		
		return "/money/money_user/write";
	}
	
	/**
	 * 계정관련 action
	 * */
	@RequestMapping(value="/money/money_user/action")
	public String action(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("MoneyUserVO") MoneyUserVO paramMoneyUserVO,ModelMap model) throws Exception {
		
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
		}
		
		if("write".equals(mode)){//=========================================================등록

			paramMoneyUserVO.setRegisterDt(new Date());
			paramMoneyUserVO.setRegisterId(loginUserVO.getUserId());
			paramMoneyUserVO.setDeleteFlag("N");
			
			String userUid = moneyUserService.writeMoneyUser(paramMoneyUserVO); 
			
			if(userUid!=null && !"".equals(userUid)){
				model.addAttribute("RESULT_CODE", "001");//이전페이지로
				model.addAttribute("RESULT_URL", "/money/money_user/list");//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
			
		}else if("update".equals(mode)){//=========================================================수정
			if(paramMoneyUserVO==null || "".equals(paramMoneyUserVO.getMoneyUserUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return "common/actionResult";
			}
			
			MoneyUserVO oriMoneyUserVO = moneyUserService.getMoneyUserVO(paramMoneyUserVO);
			
			oriMoneyUserVO.setMoneyUserName(paramMoneyUserVO.getMoneyUserName());
			oriMoneyUserVO.setUpdateDt(new Date());
			oriMoneyUserVO.setUpdateId(loginUserVO.getUserId());
			
			moneyUserService.updateMoneyUser(paramMoneyUserVO);
			
			model.addAttribute("RESULT_CODE", "001");//이전페이지로
			model.addAttribute("RESULT_URL", "/manage/money_user/update?moneyUserUid="+oriMoneyUserVO.getMoneyUserUid()+"&"+paramMoneyUserVO.getParamStr());//성공후 이동페이지
			
		}else if("delete".equals(mode)){
			
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		//액션결과페이지로
		return viewPage;
	}
}
