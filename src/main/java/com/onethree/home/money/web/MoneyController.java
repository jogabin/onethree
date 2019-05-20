package com.onethree.home.money.web;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onethree.home.common.util.CommUtil;
import com.onethree.home.money.service.MoneyService;
import com.onethree.home.money.service.MoneyUserService;
import com.onethree.home.money.vo.MoneyVO;
import com.onethree.home.money.vo.MoneyUserVO;
import com.onethree.home.user.service.UserService;
import com.onethree.home.user.vo.UserVO;

@Controller
public class MoneyController {
	
	
	//메뉴 타이틀
	String contentTitle = "회비관리";
	//메뉴번호
	String contentNumb = "2";
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private MoneyService moneyService;
	

	@Autowired
	private MoneyUserService moneyUserService;
	
	//회비시작일 년
	@Value( "${planMoneyStartYear}" )
	private int planMoneyStartYear;
	
	//회비시작일 달
	@Value( "${planMoneyStartMonth}" )
	private int planMoneyStartMonth;
	
	//메뉴타이틀, 메뉴번호 지정
	public void setMenuTitle(ModelMap model,String contentSubTitle){
		model.addAttribute("CONTENT_TITLE", contentTitle);
		model.addAttribute("CONTENTSUB_TITLE", contentSubTitle);
		model.addAttribute("CONTENT_NUMB", contentNumb);
	}
	
	
	@RequestMapping(value="/money/yearlist")
	public String yearlist(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("MoneyVO") MoneyVO paramMoneyVO) throws Exception 
	{
		//연도설정
		if(paramMoneyVO==null || paramMoneyVO.getYearDate() == 0){
			String yearDateStr = CommUtil.getCurrentDate("yyyy");
			try{
				paramMoneyVO.setYearDate(Integer.parseInt(yearDateStr));
			}catch(Exception e){}
		}
		
		paramMoneyVO.setInoutFlag("A");//입금용만
		paramMoneyVO.setPlanFlag("Y");//회비전용만
		
		//회비전용 회원리스트 설정
		MoneyUserVO moneyUserVO = new MoneyUserVO();
		moneyUserVO.setDeleteFlag("N");//회비전용플래그
		List<MoneyUserVO> moneyUserList = moneyUserService.getMoneyUserList(moneyUserVO);
		
		/*if(userList!=null && userList.size()>0){
			for(UserVO dataVO:userList){
				DecimalFormat df = new DecimalFormat("00");
				//for(int j=2014;j<=2017;j++){
				for(int i=1; i<=9;i++){
					MoneyVO moneyVO = new MoneyVO();
					moneyVO.setUserUid(dataVO.getUserUid());
					
					moneyVO.setYearDate(2018);
					moneyVO.setMonthDate(i);
					moneyVO.setMoney(10000);
					moneyVO.setInoutFlag("A");
					moneyVO.setPlanFlag("Y");
					String inoutDt = String.valueOf(moneyVO.getYearDate())+"-"+df.format(moneyVO.getMonthDate())+"-01";
					moneyVO.setInoutDt(inoutDt);
					moneyVO.setRegisterDt(new Date());
					moneyVO.setRegisterId("admin");
					
					String moneyUid = moneyService.moneyWrite(moneyVO);
				} 
				//}
			}
		}*/
		
		//회비리스트 설정
		List<MoneyVO> moneyList = moneyService.getYearMoneyList(paramMoneyVO);
		
		//회원별로 12달치의 회비내역으로 맵으로 설정
		Map<String,Map<Integer,MoneyVO>> userMoneyMap = new HashMap<String,Map<Integer,MoneyVO>>(); 
		if(moneyList!=null && moneyList.size()>0){
			Map<Integer,MoneyVO> monthMoneyMap = new HashMap<Integer,MoneyVO>();
			String beforeUserUid = "";
			for(MoneyVO moneyVO:moneyList){
				//각회원별로 달의 입금내역 설정
				if("".equals(beforeUserUid)){
					beforeUserUid = moneyVO.getUserUid();
					monthMoneyMap.put(moneyVO.getMonthDate(), moneyVO);
					userMoneyMap.put(moneyVO.getUserUid(), monthMoneyMap);
				}else if(beforeUserUid.equals(moneyVO.getUserUid())){
					beforeUserUid = moneyVO.getUserUid();
					monthMoneyMap.put(moneyVO.getMonthDate(), moneyVO);
					userMoneyMap.put(moneyVO.getUserUid(), monthMoneyMap);
				}else{
					beforeUserUid = moneyVO.getUserUid();
					monthMoneyMap = null;
					monthMoneyMap = new HashMap<Integer,MoneyVO>();
					monthMoneyMap.put(moneyVO.getMonthDate(), moneyVO);
					userMoneyMap.put(moneyVO.getUserUid(), monthMoneyMap);
				}
			}
		}
		
		/*Iterator<String> keys = userMoneyMap.keySet().iterator();
        while( keys.hasNext() ){
            String key = keys.next();
            System.out.println("======================");
            System.out.println(key);
            System.out.println("----------------------");
            Map<Integer,MoneyVO> monthMoneyMap = userMoneyMap.get(key);
            Iterator<Integer> keysNum = monthMoneyMap.keySet().iterator();
            while( keysNum.hasNext() ){
            	 int keyNum = keysNum.next();
            	 MoneyVO moneyVO = (MoneyVO)monthMoneyMap.get(keyNum);
            	 System.out.println(keyNum+" :::");
            	 System.out.println(moneyVO.getMoney());
            }
        }*/
		
		//잔액설정
		paramMoneyVO.setInoutFlag("A");
		int totalInMoney = moneyService.getMaxMoney(paramMoneyVO);
		paramMoneyVO.setInoutFlag("B");
		int totalOutMoney = moneyService.getMaxMoney(paramMoneyVO);
		totalInMoney -= totalOutMoney; 
		
		//해당날짜까지의 회원별 회비입금내역 리스트
		String inoutDt = CommUtil.getCurrentDate("yyyy-MM-01");//오늘날짜로 설정
		paramMoneyVO.setInoutDt(inoutDt);
		paramMoneyVO.setInoutFlag("A");
		paramMoneyVO.setPlanFlag("Y");
		List<Object[]> totalObjList = moneyService.getUserYearTotalMoneyList(paramMoneyVO);
		Map<String,Long> userTotalMoneyMap = new HashMap<String,Long>();
		if(totalObjList!=null && totalObjList.size()>0){
			for(Object[] objData:totalObjList){
				userTotalMoneyMap.put(objData[0].toString(), Long.parseLong(objData[1].toString()));
			}
		}
		DecimalFormat df = new DecimalFormat("00");
		//최종입금 예정금액 설정
		long expectMoney = 0;
		int sYear= planMoneyStartYear; 
		int sMonth = planMoneyStartMonth; 
		int eYear = Integer.parseInt(CommUtil.getCurrentDate("yyyy")); 
		int eMonth = Integer.parseInt(CommUtil.getCurrentDate("MM")); 
		int month_diff = (eYear - sYear)* 12 + (eMonth - sMonth); //날짜사이의 달수 계산
		expectMoney = month_diff*10000;
		
		int startDateNum = Integer.parseInt(String.valueOf(planMoneyStartYear)+df.format(planMoneyStartMonth));
		
		model.addAttribute("moneyUserList",moneyUserList);
		model.addAttribute("userMoneyMap",userMoneyMap);
		model.addAttribute("userTotalMoneyMap",userTotalMoneyMap);
		model.addAttribute("expectMoney",expectMoney);
		model.addAttribute("totalInMoney",totalInMoney);
		model.addAttribute("startDateNum",startDateNum);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"연간회비내역");
		
		return "/money/yearlist";
	}
	
	/**
	 * 정기회비 action 처리
	 * */
	@RequestMapping(value="/money/planaction")
	public String planAction(HttpServletRequest request,HttpServletResponse response
			,@ModelAttribute("MoneyVO") MoneyVO paramMoneyVO,ModelMap model) throws Exception {
		
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
			if(paramMoneyVO==null || (
					"".equals(paramMoneyVO.getUserUid())
					||"".equals(paramMoneyVO.getYearDate())
					|| "".equals(paramMoneyVO.getMonthDate())
				)){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return viewPage;
			}
			
			DecimalFormat df = new DecimalFormat("00");
			
			paramMoneyVO.setMoney(10000);
			paramMoneyVO.setInoutFlag("A");
			paramMoneyVO.setPlanFlag("Y");
			String inoutDt = String.valueOf(paramMoneyVO.getYearDate())+"-"+df.format(paramMoneyVO.getMonthDate())+"-01";
			paramMoneyVO.setInoutDt(inoutDt);
			paramMoneyVO.setRegisterDt(new Date());
			paramMoneyVO.setRegisterId("admin");
			
			String moneyUid = moneyService.moneyWrite(paramMoneyVO); 
			
			if(moneyUid!=null && !"".equals(moneyUid)){
				model.addAttribute("RESULT_CODE", "006");//이전페이지로
				model.addAttribute("RESULT_URL", "/money/yearlist.do?yearDate="+paramMoneyVO.getYearDate());//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
			
		}else if("update".equals(mode)){//=========================================================수정
			
		}else if("delete".equals(mode)){//=========================================================삭제
			if(paramMoneyVO==null || "".equals(paramMoneyVO.getMoneyUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return viewPage;
			}
			
			int result = moneyService.deleteMoneyInfo(paramMoneyVO);
			if(result>0){
				model.addAttribute("RESULT_CODE", "006");//이전페이지로
				model.addAttribute("RESULT_URL", "/money/yearlist.do?yearDate="+paramMoneyVO.getYearDate());//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		//액션결과페이지로
		return viewPage;
	}
	
	/**
	 * 기타입출금 내역
	 * */
	@RequestMapping(value="/money/moneyout")
	public String moneyout(HttpServletRequest request,HttpServletResponse response,ModelMap model
			,@ModelAttribute("MoneyVO") MoneyVO paramMoneyVO) throws Exception 
	{
				
		paramMoneyVO.setPlanFlag("N");//회비전용만
		
		//입출금 리스트
		List<MoneyVO> moneyList = moneyService.getInOutList(paramMoneyVO);
		
		//잔액설정
		MoneyVO moneyVO = new MoneyVO();
		moneyVO.setInoutFlag("A");
		int totalInMoney = moneyService.getMaxMoney(moneyVO);
		moneyVO.setInoutFlag("B");
		int totalOutMoney = moneyService.getMaxMoney(moneyVO);
		totalInMoney -= totalOutMoney; 
		
		model.addAttribute("moneyList", moneyList);
		model.addAttribute("totalInMoney", totalInMoney);
		model.addAttribute("pageVO", paramMoneyVO);
		
		//2번째 파라미터 서브타이틀
		setMenuTitle(model,"입출금 내역");
		
		return "/money/moneyout";
	}
	
	/**
	 * 입출금 action 처리
	 * */
	@RequestMapping(value="/money/inoutaction")
	public String inoutaction(HttpServletRequest request,HttpServletResponse response
			,@ModelAttribute("MoneyVO") MoneyVO paramMoneyVO,ModelMap model) throws Exception {
		
		String viewPage = "common/actionResult";
		String mode = "";
		String inoutFlagStr = "";
		String inoutCateStr1 = "";
		
		if(request.getParameter("mode")!=null && !"".equals(request.getParameter("mode"))){
			mode = request.getParameter("mode");
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
			return viewPage;
		}
		
		if(request.getParameter("inoutFlagStr")!=null && !"".equals(request.getParameter("inoutFlagStr"))){
			inoutFlagStr = request.getParameter("inoutFlagStr");
		}
		if(request.getParameter("inoutCateStr1")!=null && !"".equals(request.getParameter("inoutCateStr1"))){
			inoutCateStr1 = request.getParameter("inoutCateStr1");
		}
		
		if("write".equals(mode)){//=========================================================등록
			if(paramMoneyVO==null ||"".equals(paramMoneyVO.getInoutDt())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return viewPage;
			}
			
			paramMoneyVO.setPlanFlag("N");
			paramMoneyVO.setRegisterDt(new Date());
			paramMoneyVO.setRegisterId("admin");
			
			String moneyUid = moneyService.moneyWrite(paramMoneyVO); 
			
			if(moneyUid!=null && !"".equals(moneyUid)){
				model.addAttribute("RESULT_CODE", "001");
				model.addAttribute("RESULT_URL", "/money/moneyout.do?inoutFlag="+inoutFlagStr+"&inoutCate1="+inoutCateStr1);//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
			
		}else if("update".equals(mode)){//=========================================================수정
			if(paramMoneyVO==null ||"".equals(paramMoneyVO.getInoutDt()) || "".equals(paramMoneyVO.getMoneyUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return viewPage;
			}
			
			paramMoneyVO.setPlanFlag("N");
			paramMoneyVO.setUpdateDt(new Date());
			paramMoneyVO.setUpdateId("admin");
			
			moneyService.moneyUpdate(paramMoneyVO); 
			
			model.addAttribute("RESULT_CODE", "001");
			model.addAttribute("RESULT_URL", "/money/moneyout.do?inoutFlag="+inoutFlagStr+"&inoutCate1="+inoutCateStr1);//성공후 이동페이지
			
		}else if("delete".equals(mode)){//=========================================================삭제
			if(paramMoneyVO==null || "".equals(paramMoneyVO.getMoneyUid())){
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
				return viewPage;
			}
			
			int result = moneyService.deleteMoneyInfo(paramMoneyVO);
			if(result>0){
				model.addAttribute("RESULT_CODE", "003");//이전페이지로
				model.addAttribute("RESULT_MSG", "삭제처리 완료");//이전페이지로
				model.addAttribute("RESULT_URL", "/money/moneyout.do?inoutFlag="+inoutFlagStr+"&inoutCate1="+inoutCateStr1);//성공후 이동페이지
			}else{
				model.addAttribute("RESULT_CODE", "901");//이전페이지로
				model.addAttribute("RESULT_MSG", "등록에 실패하였습니다.");//이전페이지로
			}
		}else{
			model.addAttribute("RESULT_CODE", "901");//이전페이지로
			model.addAttribute("RESULT_MSG", "잘못된 접근입니다.");//이전페이지로
		}
		
		//액션결과페이지로
		return viewPage;
	}
}
