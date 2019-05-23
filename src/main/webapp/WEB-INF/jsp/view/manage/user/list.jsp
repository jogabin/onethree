<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>


<div class="table-wrapper">
<form action="./list" method="GET" >
	<ul class="actions" style="margin:0;">
		<li>
			<select class="form-control" name="searchType" style="width:100px;">
				<option value="userName">이름</option>
				<option value="userId">아이디</option>
			</select>
		</li>
		<li><input type="text" name="keyword" id="keyword" value="" placeholder="검색어" /></li>
		<li><input type="submit" value="검색" /></li>
	</ul>
</form>

<table class="defTable">
	<thead>
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>회원명</th>
			<th>승인여부</th>
			<th>등록일</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="rowNum" value="${pageVO.totalCount-(pageVO.page-1)*pageVO.rowCount }" />
		<c:forEach var="row" items="${userList}" varStatus="eachStatus">
			<tr>
				<td scope="row">${rowNum}</td>
				<td>
					<a href="./update?userUid=${row.userUid}&amp;${pageVO.paramStr}">
						${row.userId} 
					</a>
				</td>
				<td>${row.userName}</td>
				<td>
					<c:choose>
						<c:when test="${row.stateNum eq 0}">
							신청
						</c:when>
						<c:when test="${row.stateNum eq 1}">
							승인
						</c:when>
						<c:when test="${row.stateNum eq 2}">
							탈퇴
						</c:when>
						<c:otherwise>
							신청
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${row.registerDt }" type="date" pattern="yyyy-MM-dd" />
				</td>
				<c:set var="rowNum" value="${rowNum-1 }" />
			</tr>
		</c:forEach>
		<c:if test="${empty userList}">
			<tr>
				<td colspan="5">등록된 회원정보가 없습니다.</td>
			</tr>
		</c:if>
	</tbody>
</table>

<div style="position:relative;margin-bottom:100px !important;">
	<a href="./write" style="float:right;"><button type="button" class="btn btn-primary">등록</button></a>
</div>

<ul class="pagination">
	${paging}
</ul>

</div>

<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>