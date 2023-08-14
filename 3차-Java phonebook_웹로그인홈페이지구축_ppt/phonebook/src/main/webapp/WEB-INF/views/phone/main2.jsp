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

<title>Main Page</title>

<link rel="stylesheet" type="text/css" href="/css/main_header.css">
<link rel="stylesheet" type="text/css" href="/css/main_main.css">
<!-- <script type="text/javascript" src="/js/main_main.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
	<main>

		<c:if test="${error != null}">
			<script>
				alert("${error}")
			</script>
		</c:if>
		<br>
		<div class="member">
		<div class="outoftable">
		<div class="btndiv">
<!-- 		<a href="javascript:history.back()" class="btn btn-primary back-button"><< Back</a> -->
		<a href="/phone/main" class="btn btn-primary back-button"><< Main Page</a>
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
										class="btn-outline-info updatebtn" type="button">>수정</button></a> <a
								href="/phone/delete/${mem.mbid}"><button type="button"
										class="btn-close" aria-label="Close"></button></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				</div>
			<br> <br>
		</div>
		<br>
	</main>
</body>
</html>