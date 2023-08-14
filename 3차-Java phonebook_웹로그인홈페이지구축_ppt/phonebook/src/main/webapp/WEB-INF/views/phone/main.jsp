<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

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

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<title>Main Page</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<style>
* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<link rel="stylesheet" type="text/css" href="/css/main_header.css">
<link rel="stylesheet" type="text/css" href="/css/main_main.css">
<!-- <script type="text/javascript" src="/js/main_main.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>


</head>
<body>
	<!-- 	<header>
	</header> -->
	<main>
		<div class="introduce1">환영합니다 ${userid}님!</div>
		<br>
		<div class="member">
			<div class="outoftable">
				<div class="btndiv">
					<a href="/user/logout.do"
						class="btn btn-primary logaddbutton logout-button">LOGOUT</a> <a
						href="/phone/add" class="btn btn-primary logaddbutton add-button">회원
						추가하기</a>
				</div>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Name</th>
							<th>Phone</th>
							<th>Address</th>
							<th>Group</th>
							<th>비고</th>
						</tr>
					</thead>
					<tbody class="listMember">
						<c:forEach var="mem" items="${memlist}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${mem.mbnm}</td>
								<td>${mem.mbph}</td>
								<td>${mem.mbad}</td>
								<td>${mem.groupName}</td>
								<td class="buttons"><a href="/phone/form/${mem.mbid}/${mem.mbnm}/${mem.mbph}/${mem.mbad}/${mem.groupName}"><button
											class="btn-outline-info updatebtn" type="button">>수정</button></a>
									<a href="/phone/delete/${mem.mbid}"><button type="button"
											class="btn-close" aria-label="Close"></button></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<br> <br>
				<nav>
					<form method="get" action="/phone/search">
						<fieldset>
							<legend class="visually-hidden">검색</legend>
							<div class="search_box">
								<input type="text" id="searchInput" name="searchInput"
									maxlength="225" placeholder="이름으로 검색하세요">
								<button type="submit">검색</button>
							</div>
						</fieldset>
					</form>
				</nav>
				<div id="searchedMemList"></div>
			</div>
		</div>
		<br>
		
		<div class="container">
				<div id="mycarousel" class="carousel slide" data-bs-ride="carousel">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#mycarousel"
							data-bs-slide-to="0" class="active" aria-current="true"
							aria-label="Slide 1"></button>
						<button type="button" data-bs-target="#mycarousel"
							data-bs-slide-to="1" aria-label="Slide 2"></button>
						<button type="button" data-bs-target="#mycarousel"
							data-bs-slide-to="2" aria-label="Slide 3"></button>
					</div>
					<div class="carousel-inner">
						<div class="carousel-item active">
							<img
								src="https://cdn.pixabay.com/photo/2017/10/12/22/17/business-2846221_1280.jpg"
								class="d-block w-100" alt="...">
							<div class="carousel-caption d-none d-md-block"></div>
						</div>
						<div class="carousel-item">
							<img
								src="https://cdn.pixabay.com/photo/2016/03/27/20/00/coffee-1284041_1280.jpg"
								class="d-block w-100" alt="...">
							<div class="carousel-caption d-none d-md-block"></div>
						</div>
						<div class="carousel-item">
							<img
								src="https://cdn.pixabay.com/photo/2015/05/31/15/18/technology-792180_1280.jpg"
								class="d-block w-100" alt="...">
							<div class="carousel-caption d-none d-md-block"></div>
						</div>
					</div>
					<button class="carousel-control-prev" type="button"
						data-bs-target="#mycarousel" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Previous</span>
					</button>
					<button class="carousel-control-next" type="button"
						data-bs-target="#mycarousel" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Next</span>
					</button>
				</div>
			</div>
		
	</main>
	${error}
</body>
</html>