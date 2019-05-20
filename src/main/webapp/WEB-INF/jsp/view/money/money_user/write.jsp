<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<script type="text/javascript">
	function formCheck(){
		var form = document.writeForm;
		if(form.moneyUserName.value===""){
			alert("이름을 입력해주세요.");
			form.moneyUserName.focus();
			return false;
		}
		
		if(confirm("회원정보를 등록하시겠습니까?")){
			form.submit();
		}
	}
</script>

<div class="row gtr-150">
	<div class="col-6 col-12-medium">
	<form:form modelAttribute="moneyUserVO" name="writeForm" action="./action.do" method="post">
		<input type="hidden" name="mode" id="mode"  value="${mode }">
		<input type="hidden" name="page" id="page"  value="${pageVO.page }">
		<input type="hidden" name="searchType" id="searchType"  value="${pageVO.searchType }">
		<input type="hidden" name="keyword" id="keyword"  value="${pageVO.keyword }">
		
		<c:if test="${mode eq 'update' }">
			<form:hidden path="moneyUserUid" id="moneyUserUid"/>
		</c:if>
		
		<table class="bbsView" summary="보기">
			<tbody>
				<tr>
					<th scope="row"><label for="moneyUserName">이름</label> (<span class="required">*</span>)</th>
					<td colspan="3">
						<form:input path="moneyUserName" id="moneyUserName" class="inw150" />
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btn_area bbs_btn">
			<button type="button" class="btn primary" onclick="formCheck();">등록</button>
			<button type="button" class="btn" onclick="location.href='./list.do?${pageVO.paramStr}'" >취소</button>
		</div>
	</form:form>
	</div>
</div>


<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>