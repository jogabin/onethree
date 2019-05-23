<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<script type="text/javascript">
	function formCheck(){
		var form = document.writeForm;
		<c:if test="${mode eq 'join' }">
		if(form.userId.value===""){
			alert("아이디를 입력해주세요.");
			form.userId.focus();
			return false;
		}
		if (form.userIdConfirm.value==="N") { 
			alert("아이디 중복 확인을 해 주세요."); 
			form.userIdConfirm.focus(); 
			return false; 
		}
		</c:if>
		
		if(form.userName.value===""){
			alert("이름을 입력해주세요.");
			form.userName.focus();
			return false;
		}
		
		<c:if test="${mode eq 'join' }">
		//비밀번호 설정
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
		</c:if>
		
		<c:if test="${mode eq 'update' }">
		//비밀번호 설정
		if (form.userPass.value!="") { 
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
		}		
		</c:if>
		
		
		if(form.userEmail.value===""){
			alert("이메일을 입력해주세요.");
			form.userEmail.focus();
			return false;
		}
		if (form.userEmailConfirm.value==="N") { 
			alert("이메일 중복 확인을 해 주세요."); 
			form.userEmailConfirm.focus(); 
			return false; 
		}
		
		if (form.userEmail.value!="" && !isEmail(form.userEmail.value)){
			alert("이메일 주소가 잘못되었습니다.");
			form.userEmail.focus();
			return false;
		}
		
		<c:if test="${mode eq 'join' }">
		if(confirm("회원가입 하시겠습니까?")){
		</c:if>
		<c:if test="${mode eq 'update' }">
		if(confirm("회원정보를 수정하시겠습니까?")){
		</c:if>		
			form.submit();
		}
	}
	
	function userIdCheckForm(){
		var loginId = $("#userId").val();
		userIdCheck(loginId);
	}
	
	function userEmailCheckForm(){
		var userEmail = $("#userEmail").val();
		userEmailCheck(userEmail);
	}
</script>

<form:form modelAttribute="userVO" name="writeForm" action="./action" method="post">
	<input type="hidden" name="mode" id="mode"  value="${mode }">
	
	<c:if test="${mode eq 'write' }">
		<input type="hidden" name="userAuthor" value="0">
		<input type="hidden" name="stateNum"  value="0">
	</c:if>
	<c:if test="${mode eq 'update' }">
		<form:hidden path="userUid" id="userUid"/>
		<form:hidden path="userAuthor" id="userAuthor"/>
		<form:hidden path="stateNum" id="stateNum"/>
	</c:if>
	
	<table class="bbsView" summary="보기">
		<tbody>
			<tr>
				<th scope="row"><label for="userId">아이디</label> (<span class="required">*</span>)</th>
				<td>
					<c:if test="${mode eq 'join' }">
						<form:input path="userId" id="userId" class="inw150" onkeyup="userIdConfirmInit();" style="float:left"/>
						<a class="button primary" href="#" onclick="userIdCheckForm();" style="float:left;margin-left:5px;">아이디 중복확인</a>
						<input type="hidden" id="userIdConfirm" name="userIdConfirm" value="" />
					</c:if>
					<c:if test="${mode eq 'update' }">
						${userVO.userId }
					</c:if>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="userEmail">이메일</label> (<span class="required">*</span>)</th>
				<td>
					<form:input path="userEmail" id="userEmail" onkeyup="userEmailConfirmInit();"/>
					<a class="button primary" href="#" onclick="userEmailCheckForm();" style="float:left;margin-top:5px;">이메일 중복확인</a>
					<input type="hidden" id="userEmailConfirm" name="userEmailConfirm" value="" />
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="userName">이름</label> (<span class="required">*</span>)</th>
				<td>
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
		<c:if test="${mode eq 'join' }">
			<button type="button" class="btn primary" onclick="formCheck();">등록</button>
		</c:if>
		<c:if test="${mode eq 'update' }">
			<button type="button" class="btn primary" onclick="formCheck();">수정</button>
			<button type="button" class="btn" onclick="goDelete();">탈퇴</button>
		</c:if>
			<button type="button" class="btn" onclick="location.href='/'" >메인으로</button>
	</div>
</form:form>


<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>