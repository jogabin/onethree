<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>원탑 홀덤 게임관리자</title>
</head>
<body>

<script type="text/javascript">
	var  resultCode = "${RESULT_CODE}";	
		
	if(resultCode == '001'){//================================001 기본등록
		alert("정상적으로 등록되었습니다.");
		location.href="${RESULT_URL}";
	}else if(resultCode == '002'){//==========================002 팝업기본등록 팝업창닫고 부모창 새로고침
		alert("정상적으로 등록되었습니다.");
		opener.location.reload();
		self.close();
	}else if(resultCode == '003'){//==========================003 액션성공 메시지 출력 페이지이동
		alert("${RESULT_MSG}");
		location.href="${RESULT_URL}";
	}else if(resultCode == '004'){//==========================004 팝업등록액션후 부모창 새로고침
		alert("${RESULT_MSG}");
		location.href="${RESULT_URL}";
		opener.location.reload();
	}else if(resultCode == '005'){//==========================005 팝업등록액션후 안읽은메시지 ajax호출
		alert("${RESULT_MSG}");
		location.href="${RESULT_URL}";
		opener.unreadNote();
	}else if(resultCode == '006'){//==========================006 노메세지
		location.href="${RESULT_URL}";
		opener.unreadNote();
	}else if(resultCode == '010'){//==========================010 메세지 없이 이동
		location.href="${RESULT_URL}";
	}else if(resultCode == '801'){//==========================801 팝업에러 창닫기
		alert("${RESULT_MSG}");
		window.close();
	}else if(resultCode == '901'){//==========================901 에러 이전페이지로
		alert("${RESULT_MSG}");
		history.back();
	}else if(resultCode == '902'){//==========================902 에러 리다이렉트페이지로
		alert("${RESULT_MSG}");
		location.href="${RESULT_URL}";
	}else{//==================================================예외 오류 고정
		alert("잘못된 접근입니다.");
		history.back();
	}
</script>

</body>
</html>