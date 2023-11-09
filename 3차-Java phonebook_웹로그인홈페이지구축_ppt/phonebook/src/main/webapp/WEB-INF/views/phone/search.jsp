<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>


<title>Main Page</title>

<link rel="stylesheet" type="text/css" href="/css/main_header.css">
<link rel="stylesheet" type="text/css" href="/css/main_main.css">

</head>
<body>
		<header>
		<div class="introduce">
      <a href="#" class="link_text">환영합니다 <%-- ${memlist.userid} --%>님!</a>
    	</div>
    	
<!--     	<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link active" aria-current="page" href="#">Active</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">Link</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">Link</a>
  </li>
	</ul> -->
    	
    	
		
	<nav> <!--링크들의 집합이라 정의  -->
      <div class="nav_items">
        <ul>
      	  <li><a href="#">Home</a></li>
          <li><a href="#">Search </a></li>
          <li><a href="#">Add </a></li>
        </ul>
      </div>
    </nav>
		</header>		
		
		<main>
		<div class="member">
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
			<td class="buttons">
                <a href="#"><button class="button">Update</button></a>
                <%-- <a href="/phone/delete/${mem.mbid}"><button class="button button2">Delete</button></a> --%>
                <a href="/phone/delete/${mem.mbid}"><button type="button" class="btn-close" aria-label="Close"></button></a>
                
            </td>
		</tr>
			</c:forEach>
			
		</tbody>
		
			<c:if test="${error != null}">
		<div class="alert alert-danger alert-dismissible fade show mt-3">
				에러 발생: ${error}
			<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
		</div>
	</c:if>
		
				
		</table>
   		<nav>
			<form method="get" action="/phone/search" enctype="multipart/form-data">
      		<fieldset> <!--테두리 잡아줄때  -->
       		<legend class="visually-hidden">검색</legend>  <!--legend 테두리 이름 잡아줄때 -->
        	<div class="search_box">
            <input type="text" name="searchInput" maxlength="225" tabindex="1" /> <!--tab은 커서의 위치  -->
            <button type="submit" tabindex="2">검색</button>
        	</div>
      		</fieldset>
    		</form>
    	</nav>
   		</div>
   		
   		<br>
   		<div class="pages">
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
  </ul>
</nav>
	</div>
   </main>

</body>
</html>