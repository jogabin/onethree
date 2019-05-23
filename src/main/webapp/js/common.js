
//아이디 검증
function  userIdValiCheck(loginId){
	var userIdStr = loginId;
	
	var blank_pattern = /[\s]/g;
	if(blank_pattern.test(userIdStr)){
		alert("아이디는  공백은 사용할 수 없습니다.");
		return false;
	}
	
	if (!userIdStr) {
		alert("아이디를 입력해 주세요.");
		return;
	} 
	
	var kor = userIdStr.search( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi);
	console.log(kor);
	if(kor>-1){
		alert("아이디에 한글은 들어갈 수 없습니다.");
		return false;
	}
	
	var spe = userIdStr.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	if(spe>-1){
		alert("아이디에 특수문자는 들어갈 수 없습니다.");
		return false;
	}
	if(userIdStr.length < 4 || userIdStr.length >= 18){
		alert("아이디는 4자리 ~ 18자리 이내로 입력해주세요.");
		return false;
	}
	
	return true;
}

//아이디 중복 확인
function userIdCheck(loginId) {
	if(!userIdValiCheck(loginId)){
		return;
	}
	var userIdStr = loginId;
	$.ajax({
		url: "/user/loginIdCheck",	
		data: {
			loginId:userIdStr
		},
		async: false,
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			if(data.cnt > 0){
				alert("중복된 아이디가 있습니다. 다시 입력해주세요.");
			}else{
				alert("사용가능한 아이디입니다.");
				$("#userIdConfirm").val("Y");
			}
		},
		error : function(data){
			console.log(data);
		}
	});	
}

//이메일 중복 확인
function userEmailCheck(userEmail) {
	
	if(userEmail===""){
		alert("이메일을 입력해주세요.");
		return;
	}
	
	if (!isEmail(userEmail)){
		alert("잘못된 이메일 정보입니다.");
		return;
	}
	
	var userEmailStr = userEmail;
	$.ajax({
		url: "/user/userEmailCheck",	
		data: {
			userEmail:userEmailStr
		},
		async: false,
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			console.log(data);
			if(data.cnt > 0){
				alert("중복된 이메일정보가 있습니다. 다시 입력해주세요.");
			}else{
				alert("사용가능한 이메일입니다.");
				$("#userEmailConfirm").val("Y");
			}
		},
		error : function(data){
			console.log(data);
		}
	});	
}

//아이디 중복 확인 초기화
function userIdConfirmInit() {
	$("#userIdConfirm").val("N");
}

//이메일 중복 확인 초기화
function userEmailConfirmInit() {
	$("#userEmailConfirm").val("N");
}

//비밀번호 체크
function isPassword(str){
	var pw = str;
	var num = pw.search(/[0-9]/g);
	var eng = pw.search(/[a-z]/ig);
	var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

	if(pw.length < 4 || pw.length > 12){
		alert("비밀번호는 4자리 ~ 12자리 이내로 입력해주세요.");
		return false;
	}
	
	var blank_pattern = /[\s]/g;
	if(blank_pattern.test(pw)){
		alert("비밀번호는  공백은 사용할 수 없습니다.");
		return false;
	}
	
	if(num < 0 || eng < 0 || spe < 0 ){
		alert("비밀번호는 영문,숫자, 특수문자를 혼합하여 입력해주세요.");
		return false;
	}
	return true;
}

//E-mail 유효성 체크
function isEmail(s){
	return s.search(/^\s*[\w\~\-\.]+\@[\w\~\-]+(\.[\w\~\-]+)+\s*$/g)>=0;
}