


function checkform() { // submit를 눌렀을때 서버로 정보를 보내기전임
    if ( document.signupform.userid.value == "") {
        alert("아이디를 입력하세요");
        signupform.userid.focus();
        return false;
    }

    var idAnnotation_check = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if ( !idAnnotation_check.test($("#userid_input").val())) {
        alert("올바르지 않는 양식의 아이디입니다 xxx@xxxx.xx");
        signupform.userid.focus();
        return false;
    } 



    
    if ( document.signupform.userph.value == "") {
        alert("전화번호를 입력하세요");
        signupform.userph.focus();
        return false;
    }else {
        var num_check3= /^[0-9]{11,11}$/;
        if(!num_check3.test($("#userph_input").val())) {
            alert("전화번호가 올바르지 않습니다 11자리 숫자를 입력하세요");
            signupform.userph.focus();
            return false;
        }

    }

    
    if( document.signupform.userpw.value == ""){
        alert("비밀번호를 입력하세요");
        signupform.userpw.focus();
        return false;
    }
    
    if( document.signupform.userpw.value !=document.signupform.userpw2.value) {
        alert("비밀번호가 같지 않습니다");
        signupform.userpw.focus();
        return false;
    }

    //아이디 중복이면 경고창 뜨게
    if (dupId==true){
        alert("아이디가 중복입니다 새로운 아이디를 입력하세요");
        return false;
    }else{

    }

    document.signupform.submit();
}






    