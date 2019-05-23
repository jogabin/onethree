<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<script type="text/javascript">
	function writeCheckForm(form){
		if(form.loginId.value === ""){
			alert("아이디를 입력해주세요.");
			form.loginId.focus();
			return false;
		}
		if(form.loginPassword.value === ""){
			alert("비밀번호를 입력해주세요.");
			form.loginPassword.focus();
			return false;
		}
		return true;
	}
</script>
<%
	String returnUrl = "";
	if(request.getParameter("returnUrl")!=null && !"".equals(request.getParameter("returnUrl"))){
		returnUrl = request.getParameter("returnUrl");
	}
%>
<div class="row gtr-150">
	<div class="col-4 col-12-medium">
		<form name="writeForm" method="post" action="/login/action" onsubmit="return writeCheckForm(this);">
			<input type="hidden" name="mode" value="login" />
			<input type="hidden" name="returnUrl" value="<%=returnUrl%>" />
			
			<div class="row gtr-uniform">
				<div class="col-12">
					<input type="text" name="loginId" id="loginId" value="" placeholder="아이디" />
				</div>
				<div class="col-12">
					<input type="password" name="loginPassword" id="loginPassword" value="" placeholder="비밀번호" />
				</div>
				<!-- Break -->
				<div class="col-12">
					<ul class="actions">
						<li><input type="submit" value="로그인" class="primary" /></li>
						<li><input type="reset" value="회원가입" onclick="location.href='/user/join';"/></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>