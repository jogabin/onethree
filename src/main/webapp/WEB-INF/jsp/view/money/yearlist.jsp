<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.onethree.home.common.util.CommUtil" %>
<%@ page import="com.onethree.home.user.vo.UserVO" %>
<%@ page import="com.onethree.home.money.vo.MoneyVO" %>
<%@ page import="com.onethree.home.money.vo.MoneyUserVO" %>

<%
	String yearDate = CommUtil.getCurrentDate("yyyy");
	if(request.getParameter("yearDate") != null && !request.getParameter("yearDate").equals("")){
		yearDate = request.getParameter("yearDate");
	}	
%>

<script type="text/javascript">
	//입금상태 변경
	function moneyPlanWrite(userUid,monthDate){
		var moneyForm = document.moneyForm;
		
		moneyForm.mode.value = "write";
		moneyForm.userUid.value = userUid;
		moneyForm.monthDate.value = monthDate;
		moneyForm.yearDate.value = <%=yearDate %>;
		moneyForm.submit();
	}
	
	//입금정보 삭제
	function moneyPlanDel(moneyUid){
		var delForm = document.delForm;
		
		delForm.mode.value = "delete";
		delForm.moneyUid.value = moneyUid;
		delForm.yearDate.value = <%=yearDate %>;
		delForm.submit();
	}
</script>


<form action="./planaction.do" method="post" name="moneyForm" id="moneyForm">
	<input type="hidden" name="userUid" value="" />
	<input type="hidden" name="yearDate" value="" />
	<input type="hidden" name="monthDate" value="" />
	<input type="hidden" name="moneyUid" value="" />
	<input type="hidden" name="mode" value="" />
</form>	
<form action="./planaction.do" method="post" name="delForm" id="delForm">
	<input type="hidden" name="moneyUid" value="" />
	<input type="hidden" name="yearDate" value="" />
	<input type="hidden" name="mode" value="" />
</form>		

<div class="col-6" style="padding-bottom:15px;">
	<a href="./moneyout.do" class="abtn abtn-blue">기타 입출금 내역</a>
</div>

<div class="calendar_tab" >
	<p class="year">
		<a href="./yearlist.do?yearDate=<%=Integer.parseInt(yearDate)-1 %>" onfocus="this.blur()" title="<%=Integer.parseInt(yearDate)-1 %>년 이동">
			<img alt="이전년" src="/images/moneylist/mon_arr01.gif">
		</a>
		<font><%=yearDate %></font>
		<a href="./yearlist.do?yearDate=<%=Integer.parseInt(yearDate)+1 %>" onfocus="this.blur()" title="<%=Integer.parseInt(yearDate)+1 %>년 이동">
			<img alt="다음년" src="<%=request.getContextPath() %>/images/moneylist/mon_arr02.gif">
		</a>
		<font class="right_font">총 회비 잔액: <fmt:formatNumber value="${totalInMoney }" pattern="#,###" />원</font>
	</p>
</div>

<table class="defTable calTable">
	<thead>
		<tr>
			<th>이름</th>
			<th>1월</th>
			<th>2월</th>
			<th>3월</th>
			<th>4월</th>
			<th>5월</th>
			<th>6월</th>
			<th>7월</th>
			<th>8월</th>
			<th>9월</th>
			<th>10월</th>
			<th>11월</th>
			<th>12월</th>
			<th>미납여부</th>
			<th>총입금액</th>
		</tr>
	</thead>
	<tbody>
<%   
	DecimalFormat df = new DecimalFormat("00");
	int startDateNum = (int)request.getAttribute("startDateNum");
	int currDate = Integer.parseInt(CommUtil.getCurrentDate("yyyyMM"));
	long expectMoney = (long)request.getAttribute("expectMoney");
	List<MoneyUserVO> moneyUserList = (List<MoneyUserVO>)request.getAttribute("moneyUserList");
	Map<String,Map<Integer,MoneyVO>> userMoneyMap = (Map<String,Map<Integer,MoneyVO>>)request.getAttribute("userMoneyMap");
	Map<String,Long> userTotalMoneyMap = (Map<String,Long>)request.getAttribute("userTotalMoneyMap");
	
	int totalYearMoney = 0;
	
	//회비내역 리스트 출력
	if(moneyUserList!=null && moneyUserList.size()>0){
		for(MoneyUserVO moneyUserVO:moneyUserList){
			int userMoney = 0;
		%><%--입금내역s --%>
		<tr>
			<td><%=moneyUserVO.getMoneyUserName() %></td>
			<%
				//맵에 설정된 계정의 회비내역맵 설정
				Map<Integer,MoneyVO> moneyMap = null;
				if(userMoneyMap!=null && userMoneyMap.get(moneyUserVO.getMoneyUserUid())!=null){
					moneyMap = (Map<Integer,MoneyVO>)userMoneyMap.get(moneyUserVO.getMoneyUserUid());
				}
				
				long userTotalMoney = 0;
				
				if(userTotalMoneyMap!=null && userTotalMoneyMap.get(moneyUserVO.getMoneyUserUid())!=null){
					userTotalMoney = userTotalMoneyMap.get(moneyUserVO.getMoneyUserUid());
				}
				
				//미납금 설정
				boolean lastMoneyFlag = false;//미납여부
				if(expectMoney > userTotalMoney){
					lastMoneyFlag = true;
				}
				
				//월정보 설정
				for(int i=1;i<=12;i++){
					//월에 설정된 내역정보 설정
					MoneyVO moneyVO = null;
					if(moneyMap!=null && moneyMap.get(i)!=null){
						moneyVO = (MoneyVO)moneyMap.get(i);
					}
					
			%>
			<td>
				<%
					int inDate = Integer.parseInt(yearDate+df.format(i));
					if(startDateNum <= inDate){
						if(moneyVO!=null && moneyVO.getMoney()>0){//입금
							userMoney += moneyVO.getMoney();
						%>
						<a href="#dropMenu" title="입금완료" onclick="moneyPlanDel('<%=moneyVO.getMoneyUid() %>');return false;" class="abtn abtn-bluegreen" style="padding:2px 8px;margin:5px 0;">
							<i class="fa"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>
						</a>	
						<%
						}else{//미입금
							
							
							if(lastMoneyFlag && (inDate < currDate)){//미납설정
								%>
								<a href="#dropMenu" title="미납" class="abtn abtn-yellow" onclick="moneyPlanWrite('<%=moneyUserVO.getMoneyUserUid() %>',<%=i %>);return false;" onfocus="this.blur()" style="padding:2px 8px;margin:5px 0;">
									미납
								</a>
								<%
							}else{//보류설정
								%>
								<a href="#dropMenu" title="보류" class="abtn" onclick="moneyPlanWrite('<%=moneyUserVO.getMoneyUserUid() %>',<%=i %>);return false;" onfocus="this.blur()" style="padding:2px 8px;margin:5px 0;">
									<i class="fa"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>
								</a>
								<%
							}
						}
					}
				%>										
		    </td>		
				<%
				}
			%>
			
			<td>
				<%
					//미납금 설정
					if(lastMoneyFlag){
					%>
						<font style="color:red"><%=CommUtil.getMoneyComma(expectMoney-userTotalMoney) %></font>원
					<%
					}
				%>
			</td>
			<td><%=CommUtil.getMoneyComma(userMoney) %>원</td>
		</tr>					
		<%
		totalYearMoney += userMoney;
		}
	}
%><%--입금내역e --%>

	</tbody>
</table>
<font class="right_font"><%=yearDate %>년 총 회비 입금액: <%=CommUtil.getMoneyComma(totalYearMoney) %>원</font>



<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>