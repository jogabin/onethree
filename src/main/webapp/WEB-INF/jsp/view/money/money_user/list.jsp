<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/view/include/top_inc.jsp" %>

<div class="row gtr-150">
	<div class="col-6 col-12-medium">
		<div class="table-wrapper">
			<form action="./list" method="GET">
				<ul class="actions" style="margin:0;">
					<li>
						<select class="form-control" name="searchType" style="width:100px;">
							<option value="moneyUserName">이름</option>
						</select>
					</li>
					<li><input type="text" name="keyword" id="keyword" value="" placeholder="검색어" /></li>
					<li><input type="reset" value="검색" /></li>
				</ul>
			</form>
			
			<table class="defTable">
				<thead>
					<tr>
						<th>번호</th>
						<th>회원명</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="rowNum" value="${pageVO.totalCount-(pageVO.page-1)*pageVO.rowCount }" />
					<c:forEach var="row" items="${list}" varStatus="eachStatus">
						<tr>
							<td scope="row">${rowNum}</td>
							<td>
								<a href="./update.do?moneyUserUid=${row.moneyUserUid}&amp;${pageVO.paramStr}">
									${row.moneyUserName}
								</a>
							</td>
							<c:set var="rowNum" value="${rowNum-1 }" />
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr>
							<td colspan="2">등록된 회원정보가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			
			<div style="position:relative;margin-bottom:100px !important;">
				<a href="./write.do" style="float:right;"><button type="button" class="btn btn-primary">등록</button></a>
			</div>
			
			<ul class="pagination">
				${paging}
			</ul>
		
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jsp/view/include/bottom_inc.jsp" %>