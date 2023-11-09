/**
 * 
 */


function mySubmit() { 


    if ( document.loginform.userid.value == "") {
        alert("없는 정보이거나 비밀번호가 틑렸습니다");
        loginform.userid.focus();
        return false;
    }


    if( document.loginform.userpw.value == ""){
        alert("비밀번호를 입력하세요");
        loginform.userpw.focus();
        return false;
    }



    //  //아이디는 맞는데 비번이 틀렸을때
    //  if (existId==false){
    //     alert("없는 정보이거나 비밀번호가 틑렸습니다");
    //     return false;
    // }

    // console.log(existId);
    document.loginform.submit();

}



