<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>


<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/signup.css">
<script type="text/javascript" src="/js/login.js"></script>


</head>
<body>
	${error}
	<div class="login-wrap">
		<div class="login-html">
			<form method="post" action="/user/loginSuccess"
				enctype="multipart/form-data" name="loginform" id="loginform"
				onsubmit="return mySubmit()">
				<div class="h2">LOGIN</div>
				<div class="login-form">
					<div class="sign-up-htm">
						<div class="group">
							<label for="user" class="label">Username</label> <input
								id="userid_input" type="text" class="input" name="userid">
						</div>
						<div class="group">
							<label for="pass" class="label">PASSWORD</label> <input
								id="userpw_input" type="password" class="input"
								data-type="password" name="userpw">
						</div>
						<div class="group buttondiv">
							<!-- <input type="submit" class="button press" value="Login"> -->
							<input type="button" id="loginFinal" class="button press"
								value="Login" onclick="mySubmit()">
						</div>
					</div>
				</div>
			</form>

			<a class="btn btn-primary btn2" href="/user/signupPage" role="button">회원가입
				하러가기</a>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script>
		/* var existId = false;
		$("#loginFinal").click(function() {
			var params = {
				userid : $("#userid_input").val(),
				userpw : $("#userpw_input").val()
			}
			$.ajax({
				type : "POST", 
				url : "IdCheckService", // 컨트롤러에서 대기중인 URL 주소이다.
				data : params, 
				success : function(idCheck) {
					console.log(idCheck)
					if (idCheck == 1) { // 아이디비번 둘다 맞음
						existId = true; // 로그인 성공
						console.log(existId);
					} else { 
					}
				}

			});
		}); */
	</script>




</body>
</html>