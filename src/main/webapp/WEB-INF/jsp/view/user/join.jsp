<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<script type="text/javascript">
	function formCheck(){
		var form = document.writeForm;
		if(form.userId.value===""){
			alert("아이디를 입력해주세요.");
			form.userId.focus();
			return false;
		}
		if (form.userIdConfirm.value==="N") { 
			alert("아이디 중복 확인을 해 주세요."); 
			form.userId.focus(); 
			return false; 
		}
		
		if(form.userName.value===""){
			alert("이름을 입력해주세요.");
			form.userName.focus();
			return false;
		}
		
		//비밀번호 설정
		if (form.userPass.value==="") { 
			alert("비밀번호를 입력해 주세요."); 
			form.userPass.focus(); 
			return false; 
		}
		if (form.userPassChk.value==="") { 
			alert("비밀번호확인을 입력해 주세요."); 
			form.userPassChk.focus(); 
			return false; 
		}
		var userPass = form.userPass.value;
		var userPassChk = form.userPassChk.value;
		if(!isPassword(userPassChk))
		{
			return false;
		}

		if(userPass!=userPassChk){
			alert("비밀번호와 비밀번호확인이 동일하지 않습니다."); 
			form.userPassChk.focus(); 
			return false; 
		}
		
		
		if(confirm("회원정보를 등록하시겠습니까?")){
			form.submit();
		}
	}
	
	
	
	function goDelete(){
		var form = document.writeForm;
		if(confirm("삭제하시겠습니까?")){
			form.mode.value = "delete";
			form.submit();
		}
	}
	
	function userIdCheckForm(){
		var loginId = $("#userId").val();
		userIdCheck(loginId);
	}
</script>

<form:form modelAttribute="userVO" name="writeForm" action="./action.do" method="post">
	<input type="hidden" name="mode" id="mode"  value="join">
	<input type="hidden" name="userAuthor" value="0">
	<input type="hidden" name="stateNum"  value="0">
	
	<c:if test="${mode eq 'update' }">
		<form:hidden path="userUid" id="userUid"/>
	</c:if>
	
	<table class="bbsView" summary="보기">
		<tbody>
			<tr>
				<th scope="row"><label for="userId">아이디</label> (<span class="required">*</span>)</th>
				<td colspan="3">
					<form:input path="userId" id="userId" class="inw150" onkeyup="userIdConfirmInit();" style="float:left"/>
					<a class="button primary" href="#" onclick="userIdCheckForm();" style="float:left;margin-left:5px;">중복확인</a>
					<input type="hidden" id="userIdConfirm" name="userIdConfirm" value="" />
				</td>
			</tr>
			<tr ">
				<th scope="row"><label for="userName">이름</label> (<span class="required">*</span>)</th>
				<td colspan="3">
					<form:input path="userName" id="userName" class="inw150" />
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="userPass">비밀번호</label> (<span class="required">*</span>)</th>
				<td>
					<input type="password" name="userPass" id="userPass" class="inw200" placeholder="비밀번호를 입력해주세요." value="" >
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="userPassChk">비밀번호 확인</label> (<span class="required">*</span>)</th>
				<td>
					<input type="password" name="userPassChk" id="userPassChk" class="inw200" placeholder="비밀번호를 입력해주세요." value="">
				</td>
			</tr>
		</tbody>
	</table>
	<div class="btn_area bbs_btn">
		<button type="button" class="btn primary" onclick="formCheck();">등록</button>
		<button type="button" class="btn" onclick="location.href='./list.do?${pageVO.paramStr}'" >취소</button>
		<c:if test="${mode eq 'update' }">
			<button type="button" class="btn" onclick="goDelete();">삭제</button>
		</c:if>
	</div>
</form:form>


<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>