<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

	<link rel="stylesheet" type="text/css" href="/css/add.css">
	
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script type="text/javascript" src="/js/add.js"></script>

</head>
<body>
<main>
<form method="post" action="/phone/addSuccess" enctype="multipart/form-data" id="addform" name="addform" onsubmit="return checkform()">
<div class="update-wrap">
    <div class="update-html">
    <div class="update-form">
    <label for="validationCustom01" class="form-title">ADD MEMBER</label> 
    <div>${mbid }</div>
  	<div class="col-md-4">
    <label for="validationCustom01" class="form-label">NAME</label>
    <input type="text" class="form-control" name="mbnm" id="validationCustom01" placeholder="홍길동" >
    <div id="loadtext1" class="fadeout"></div>
  </div>
  <div class="col-md-4">
    <label for="validationCustom02" class="form-label">PHONE NUMBER</label>
    <input type="text" class="form-control" name="mbph" id="validationCustom02" placeholder="01012341234" >
    <div id="loadtext2" class="fadeout"></div>
  </div>

  <div class="col-md-4">
    <label for="validationCustom02" class="form-label">ADDRESS</label>
    <input type="text" class="form-control" name="mbad" id="validationCustom03" placeholder="서울시" >
    <div id="loadtext3" class="fadeout"></div>
  </div>

  <div class="col-md-3">
    <label for="validationCustom04" class="form-label">GROUP</label>
    <select class="form-select" name="groupName" id="validationCustom05" required>
      <option selected disabled value="">Choose...</option>
      <option>가족</option>
      <option>친구</option>
      <option>기타</option>
    </select>
  </div>

  
  </div>
  <div class="col-12">
    <a href="#"><button class="btn btn-primary" type="submit" >ADD</button></a>
  </div>
</div>
</div>

</form>
</main>

<script>


 $("#validationCustom01").keyup(function(){ // 
var mbnm = $('#validationCustom01').val();
	if(mbnm=="") {
		$("#loadtext1").html("이름은 공백란이면 안됩니다");
	}else if(mbnm.length<2){
		$("#loadtext1").html("이름은 두자 이상으로 적으세요");
	}else{
		$("#loadtext1").html("");
	}
	}

) 


var num_check=/^[0-9]*$/;
var length_check=/^[0-9]{11,11}$/;
$("#validationCustom02").keyup(function(){ // 
var userph = $('#validationCustom02').val();
	if(!num_check.test(userph)) {
		$("#loadtext2").html("숫자로 된 전화번호를 넣으세요");
	}else if(userph==""){
		$("#loadtext2").html("전화번호는 공백란이면 안됩니다");
	
	}else if(!length_check.test(userph)){
		$("#loadtext2").html("11자리 숫자가 아닙니다");
	}else{
		$("#loadtext2").html("");
	}

})


 $("#validationCustom03").keyup(function(){ // 
var mbad = $('#validationCustom03').val();
	if(mbad=="") {
		$("#loadtext3").html("주소는 공백란이면 안됩니다");
	}else{
		$("#loadtext3").html("");
	}

}) 


</script>


</body>
</html>