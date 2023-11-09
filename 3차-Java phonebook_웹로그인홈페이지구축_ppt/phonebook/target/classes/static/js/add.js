/**
 * 
 */


function checkform() { // submit를 눌렀을때 서버로 정보를 보내기 전임
    // let member_id = $('#userid_input').val();
    if ( document.addform.mbnm.value == "") {
        alert("이름은 공백란이면 안됩니다");
        addform.mbnm.focus();
        return false;
    } else {
        if(document.addform.mbnm.value.length<2){
        alert("이름은 두 자 이상이여야 합니다")
        return false;
        }

    }
    
    if ( document.addform.mbph.value == "") {
        alert("전화번호를 입력하세요");
        addform.mbph.focus();
        return false;
    }
    else {

        var num_check1 = /^[0-9]{11,11}$/;//숫자와 문자 포함 형태의 6~12자리 이내의 암호 정규식
 
        if(!num_check1.test($("#validationCustom02").val())) {
        //경고
        alert("전화번호가 올바르지 않습니다 숫자 11자리 입력하세요");
        addform.mbph.focus();
        return false;
        }

        // var num_check=/^[0-9]$/;
        // var length_check=/^[]{11,11}$/;
        // if(!num_check.test($("#validationCustom02").val())) {
        //     alert("전화번호가 올바르지 않습니다 숫자만 입력하세요ㅇㅇ");
        //     addform.mbph.focus();
        //     return false;
        // }else 
        // if(!length_check.test($("#validationCustom02").val())) {
        //     alert("전화번호가 올바르지 않습니다 11자리 입력하세요ㅁㅁㅁ");
        //     addform.mbph.focus();
        //     return false;
        // }

    }
    
    if( document.addform.mbad.value == ""){
        alert("주소는 공백란이면 안됩니다");
        addform.mbad.focus();
        return false;
    }



    document.addform.submit();
}






    