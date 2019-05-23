<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.onethree.home.user.vo.UserVO" %>
<%@ page import="java.net.URLEncoder" %>

<%
	UserVO loginUserVOBot = null;
	loginUserVOBot = (UserVO)session.getAttribute("loginUserVO");
	
%>
				</div>		
			</div>
		</div>

		<!-- Sidebar -->
		<div id="sidebar">
			<div class="inner">
				<!-- Search -->
				<section id="search" class="alt"><header class="major">
						<h2><a href="/">HOME</a></h2>
					</header>
				</section>
				<!-- Menu -->
				<nav id="menu">
					<%
					if(loginUserVOBot!=null){
						%>
						<%=loginUserVOBot.getUserName() %>님 환영합니다. <a href="/user/update" class="button primary small" style="margin-left:5px;">정보수정</a>
						<%
					}
					%>
					<ul>
						<li>
							<%
								String loginReturnUrl = request.getAttribute("javax.servlet.forward.request_uri").toString();
								String loginQueryString = request.getQueryString();
								if(loginQueryString!=null && !"".equals(loginQueryString)){
									loginReturnUrl += "?"+loginQueryString; 
								}
								if(loginUserVOBot!=null){
									%>
									<form name="logoutForm" action="/login/action" method="post">
										<input type="hidden" name="mode" value="logout"/>
										<input type="hidden" name="returnUrl" value="<%=loginReturnUrl%>"/>
									</form>
									<a href="#logout" onclick="document.logoutForm.submit();">로그아웃</a>
									<%
								}else{
									%>
									<a href="/login?returnUrl=<%=URLEncoder.encode(loginReturnUrl,"UTF-8")%>">로그인</a>
									<%	
								}
							%>
						</li>
						<li><a href="/">게시판</a></li>
						<%
						//정회원이면서 하나둘셋연동인것만
						if(loginUserVOBot!=null && (
								(loginUserVOBot.getUserAuthor()>1 && "Y".equals(loginUserVOBot.getMoneyFlag())) ||
								loginUserVOBot.getUserAuthor()>8
							)){
							%>
							<li>
								<span class="opener">하나둘셋</span>
								<ul>
									<li><a href="/money/yearlist">연간회비내역</a></li>
									<li><a href="/money/moneyout">기타입출금내역</a></li>
									<li><a href="/money/money_user/list">회비회원관리</a></li>
								</ul>
							</li>
							<%
							}
						if(loginUserVOBot!=null && loginUserVOBot.getUserAuthor()>8){
							%>
							<li>
								<span class="opener">관리자</span>
								<ul>
									<li><a href="/manage/user/list">회원관리</a></li>
								</ul>
							</li>
							<%
						}
						%>
					</ul>
				</nav>

				<!-- Footer -->
				<footer id="footer">
					<p class="copyright">&copy; Untitled. All rights reserved. Demo Images: <a href="https://unsplash.com">Unsplash</a>. Design: <a href="https://html5up.net">HTML5 UP</a>.</p>
				</footer>

				</div>
			</div>

		</div>

		<!-- Scripts -->
		<script type="text/javascript" src="/assets/js/jquery.min.js"></script>
		<script type="text/javascript" src="/assets/js/browser.min.js"></script>
		<script type="text/javascript" src="/assets/js/breakpoints.min.js"></script>
		<script type="text/javascript" src="/assets/js/util.js"></script>
		<script type="text/javascript" src="/assets/js/main.js"></script>
	</body>
</html>