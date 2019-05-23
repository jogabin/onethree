<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<%@ page import="com.onethree.home.common.util.CommUtil" %>
<%@ page import="com.onethree.home.money.vo.MoneyVO" %>

<link href="/js/jqueryUi/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jqueryUi/jquery-ui.js"></script>

<style>
#dialog-background {
    display: none;
    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    background: rgba(0,0,0,.3);
    z-index: 10;
}
.modalDialog {
    display: none;
    position: fixed;
    left: calc( 50% - 160px ); top: calc( 50% - 70px );
    width: 660px; height: 220px; 
    background: #fff;
    z-index: 11;
    padding: 10px;
}
</style>

<script type="text/javascript">
	jQuery(function($){
		$.datepicker.regional['ko'] = { // Default regional settings
		        closeText: '닫기',
		        prevText: '이전달',
		        nextText: '다음달',
		        currentText: '오늘',
		        monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
		        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		        dayNames: ['일','월','화','수','목','금','토'],
		        dayNamesShort: ['일','월','화','수','목','금','토'],
		        dayNamesMin: ['일','월','화','수','목','금','토'],
		        showMonthAfterYear: true,
		        yearSuffix: '년'
		 };
	
		 $.datepicker.setDefaults($.datepicker.regional['ko']);
	
	     $( "#datepicker" ).datepicker({
	          dateFormat: 'yy-mm-dd',  //데이터 포멧형식	        
	          changeMonth: true,    //달별로 선택 할 수 있다.
	          changeYear: true,     //년별로 선택 할 수 있다.
	          showOtherMonths: false,  //이번달 달력안에 상/하 빈칸이 있을경우 전달/다음달 일로 채워준다.
	          selectOtherMonths: true, 
	          showButtonPanel: true,  //오늘 날짜로 돌아가는 버튼 및 닫기 버튼을 생성한다.
	     });
	});
	
	function inputComma(num){
		var money1 = document.getElementById("outMoney"+num).value;
		var money2 = money1.replace(/\D/g,"");
		var formatMoney = setComma(money2);
		document.getElementById("outMoney"+num).value = formatMoney;
	}
	 
    function setComma(n) {
         var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
         n += '';                          // 숫자를 문자열로 변환         
         while (reg.test(n)) {
            n = n.replace(reg, '$1' + ',' + '$2');
         }         
         return n;
    }
	
	
	//출금정보 삭제
	function goOutDelete(num){
		var outUptForm = document.getElementById("OutDelForm-"+num);
		
		if(confirm('출금 기록을 삭제하시겠습니까?')){
			outUptForm.submit();
		}
	}
	
	$(document).ready(function(){
		//모달 오픈
		$(".modalOpenA").click(function (){
			var moneyUid = $(this).attr("id").replace("a_","");
			$("#modalDiv_"+moneyUid+",#dialog-background").css("display","block");
		});
		
		//모달 닫기
		$("#dialog-background").click(function () {
			$(".modalDialog,#dialog-background").css("display","none");
		});
	});
</script>

<div id="dialog-background"></div>


<div class="col-6" style="padding-bottom:15px;">
	<a href="./yearlist" class="abtn abtn-blue">연간회비내역</a>
</div>

<h4>입출금입력</h4>
<form name="outForm" id="outForm" method="post" action="./inoutaction">
	<input type="hidden" name="mode" value="write" />
	<input type="hidden" name="inoutFlagStr" value="${pageVO.inoutFlag }" />
	<input type="hidden" name="inoutCateStr1" value="${pageVO.inoutCate1 }" />
	<ul class="actions" style="margin:0;">
		<li>
			<input type="text" name="inoutDt" id="datepicker" placeholder="날짜 입력" required style="width:120px;">
		</li>
		<li>
			<select name="inoutFlag">
                 <option value="B">출금</option>
                 <option value="A">입금</option>
             </select>
		</li>
		<li>
			<select name="inoutCate1" >
                 <option value="1">기타</option>
                 <option value="2">회식</option>
                 <option value="3">축하</option>
                 <option value="4">경조사</option>
                 <option value="5">애경사</option>
             </select>
		</li>
	</ul>
	<ul class="actions" style="margin:0;">
		<li>
			<input type="text" name="money" id="money" class="inw150" placeholder="금액 입력" required>	
		</li>
		<li>
			<input type="text" name="memo" placeholder="내용을 입력해주세요." required>	
		</li>
		<li>
			<button type="submit" class="btn btn-success">입출금입력</button>	
		</li>
	</ul>
</form>

<form action="./moneyout" method="get">
	<ul class="actions" style="margin:0;">
		<li>
			<select  name="inoutFlag" style="color:#555;">
				<option value="">입출금선택</option>
                <option value="A" <c:if test="${pageVO.inoutFlag eq 'A' }">selected="selected"</c:if>>입금</option>
                <option value="B" <c:if test="${pageVO.inoutFlag eq 'B' }">selected="selected"</c:if>>출금</option>
            </select>
		</li>
		<li>
			 <select name="inoutCate1" style="color:#555;">
            	<option value="">구분선택</option>
                <option value="1" <c:if test="${pageVO.inoutCate1 eq '1' }">selected="selected"</c:if>>기타</option>
                <option value="2" <c:if test="${pageVO.inoutCate1 eq '2' }">selected="selected"</c:if>>회식</option>
                <option value="3" <c:if test="${pageVO.inoutCate1 eq '3' }">selected="selected"</c:if>>축하</option>
                <option value="4" <c:if test="${pageVO.inoutCate1 eq '4' }">selected="selected"</c:if>>경조사</option>
                <option value="5" <c:if test="${pageVO.inoutCate1 eq '5' }">selected="selected"</c:if>>애경사</option>
            </select>
		</li>
		<li>
			<button type="submit" class="btn btn-warning">검색</button>
		</li>
	</ul>
</form>

<font class="right_font">회비 잔액:  <fmt:formatNumber value="${totalInMoney }" pattern="#,###" />원</font>
<table class="defTable">
	<thead>
		<tr>
			<th>번호</th>
			<th>입.출금</th>
			<th>입출금일</th>
			<th>구분</th>
			<th>입출금액</th>
			<th>내용</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="inMoney" value="0" />
		<c:set var="outMoney" value="0" />
		<c:set var="rowNum" value="${fn:length(moneyList)}" />
		<c:forEach var="row" items="${moneyList}" varStatus="eachStatus">
			<tr id="tableCol_${row.moneyUid }">
				<td>${rowNum }</td>
				<td>
					<c:choose>
						<c:when test="${row.inoutFlag eq 'A' }">
							입금
							<c:set var="inMoney" value="${inMoney+row.money }" />
						</c:when>
						<c:when test="${row.inoutFlag eq 'B' }">
							출금
							<c:set var="outMoney" value="${outMoney+row.money }" />
						</c:when>
					</c:choose>
				</td>
				<td>
					${row.inoutDt }
				</td>	
				<td>
					<c:choose>
						<c:when test="${row.inoutCate1 eq '1' }">
							기타
						</c:when>
						<c:when test="${row.inoutCate1 eq '2' }">
							회식
						</c:when>
						<c:when test="${row.inoutCate1 eq '3' }">
							축하
						</c:when>
						<c:when test="${row.inoutCate1 eq '4' }">
							경조사
						</c:when>
						<c:when test="${row.inoutCate1 eq '5' }">
							애경사
						</c:when>
					</c:choose>
				</td>
				<td>
					<fmt:formatNumber value="${row.money }" pattern="#,###" />원
				</td>						
				<td>
					<a href="#modalOpen" id="a_${row.moneyUid }" class="modalOpenA">
						${row.memo }
					</a>
				</td>		
			</tr>	
			<div class="modalDialog" id="modalDiv_${row.moneyUid }">
				<form action="./inoutaction" method="post" id="OutUptForm-${row.moneyUid }">
					<input type="hidden" name="mode" value="update">		
					<input type="hidden" name="moneyUid" value="${row.moneyUid }">
					<input type="hidden" name="inoutFlagStr" value="${pageVO.inoutFlag }" />
					<input type="hidden" name="inoutCateStr1" value="${pageVO.inoutCate1 }" />
					<input type="text" name="inoutDt" id="datepicker_${row.moneyUid }" value="${row.inoutDt }" style="display:inline-block;width:120px;" placeholder="입금일"/>	
					<select name="inoutFlag" id="inoutFlag${row.moneyUid }" style="display:inline-block;">
	                    <option value="B" <c:if test="${row.inoutFlag eq 'B' }">selected="selected"</c:if> >출금</option>
	                    <option value="A" <c:if test="${row.inoutFlag eq 'A' }">selected="selected"</c:if>>입금</option>
	                </select>				
		            <select name="inoutCate1" id="inoutCate1${row.moneyUid }" style="display:inline-block;">
	                    <option value="1" <c:if test="${row.inoutCate1 eq '1' }">selected="selected"</c:if>>기타</option>
	                    <option value="2" <c:if test="${row.inoutCate1 eq '2' }">selected="selected"</c:if>>회식</option>
	                    <option value="3" <c:if test="${row.inoutCate1 eq '3' }">selected="selected"</c:if>>축하</option>
	                    <option value="4" <c:if test="${row.inoutCate1 eq '4' }">selected="selected"</c:if>>경조사</option>
	                    <option value="5" <c:if test="${row.inoutCate1 eq '5' }">selected="selected"</c:if>>애경사</option>
	                </select>
	                
		            <input type="text" name="money" id="outMoney${row.moneyUid }"value="${row.money }"  style="display:inline-block;width:120px;" placeholder="금액"/>
		            <input type="text" name="memo" id="memo${row.moneyUid }" value="${row.memo }"  style="display:inline-block;width:100%;margin:10px 0;" placeholder="내용"/>
		            <input type="submit" value="수정" />	
		            <button class="btn btn-success" type="button" onclick="goOutDelete('${row.moneyUid }');return false;">삭제</button>	
		         </form>
		         <form action="./inoutaction" method="post" id="OutDelForm-${row.moneyUid }" >
					<input type="hidden" name="mode" value="delete"  />
					<input type="hidden" name="moneyUid" value="${row.moneyUid }"/>
					<input type="hidden" name="inoutFlagStr" value="${pageVO.inoutFlag }" />
					<input type="hidden" name="inoutCateStr1" value="${pageVO.inoutCate1 }" />
				</form>
			</div>
			<c:set var="rowNum" value="${rowNum-1}" />
		</c:forEach>
	</tbody>
</table>
<div style="float:right">
	<c:choose>
		<c:when test="${pageVO.inoutFlag eq 'A' }">
			<font class="right_font">총 입금액:  <fmt:formatNumber value="${inMoney }" pattern="#,###" />원</font>
		</c:when>
		<c:when test="${pageVO.inoutFlag eq 'B' }">
			<font class="right_font">총 출금액:  <fmt:formatNumber value="${outMoney }" pattern="#,###" />원</font>
		</c:when>
		<c:otherwise>
			<p><font class="right_font">총 입금액:  <fmt:formatNumber value="${inMoney }" pattern="#,###" />원</font></p>
			<p><font class="right_font">총 출금액:  <fmt:formatNumber value="${outMoney }" pattern="#,###" />원</font></p>
		</c:otherwise>
	</c:choose>	
</div>


<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>
