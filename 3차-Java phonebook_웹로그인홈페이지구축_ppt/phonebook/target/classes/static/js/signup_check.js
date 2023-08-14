/**
 * 
 */


let nickCheck = false;
let idCheck = false;


// 아이디 중복 체크
$('#userid').keyup(function () {


    let userid = $('#userid').val();

    $.ajax({
        url: '/memberIdCheck',
        type: 'post',
        data: {userid: userid},
        success: function (cnt) {
            if (cnt != 1) {
                $('.idCheck').html('사용 가능한 아이디입니다.');
                idCheck = true;
            } else {
                $('.idCheck').html('이미 사용하고 있는 아이디입니다.');
                idCheck = false;
            }
        }
    });
});

// 닉네임 중복 체크
$('#member_nick').keyup(function () {

    let member_nick = $('#member_nick').val();


    $.ajax({
        url: '/memberNickCheck',
        type: 'post',
        data: {member_nick: member_nick},
        success: function (nickCnt) {
            if (nickCnt != 1) {
                $('.nickCheck').html('사용 가능한 닉네임입니다.');
                nickCheck = true;
            } else {
                $('.nickCheck').html('이미 사용하고 있는 닉네임입니다.');
                return false;
            }
        }
    });

})



$('#userid').focusout(function() {
	let userId = $('#userid').val();
	
	$.ajax({
		url : "IdCheckService",
		type : "post",
		data : {userId: userId},
		dataType : 'json',
		success : function(result) {
			if(result == 0) {
				$("#checkId").html('사용할수 없는 아이디입니다');
				$("#checkId").attr('color','red');
			}else{
				$("#checkId").html('사용할수 있는 아이디입니다');
				$("#checkId").attr('color','green');
			}
		},
		
		error : function() {
			alert("서버요청 실패");
		}
	})
})



// 회원가입 유효성 검사

function mySubmit() {


    let userid = $('#userid').val();
    let userph = $('#userph').val();
    let userpw = $('#userpw').val();

    let check_id = RegExp(/^[a-zA-Z0-9]{4,10}$/); // 아이디 유효성 검사 (영문 및 숫자 4-10글자)
    let check_nick = RegExp(/^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{2,10}$/); // 닉네임 유효성 검사 (영문/한글/숫자 2-10글)
    let check_pw = RegExp(/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{4,20}$/); // 비밀번호 유효성 검사 (영문 및 숫자 4-20글자)
    //let memberInsertForm = $('#memberInsertForm');



    // 닉네임 중복 체크 여부
    if (nickCheck == false) {
        return false;
    }

    // 아이디 중복 체크 여부
    if (idCheck == false) {
        return false;
    }

    // 아이디 공백 확인
    if (userid == "" || usesrid == null) {
        $('.idCheck').html('아이디를 입력해주세요.');
        $('#userid').focus();
        return false;
    }

    // 아이디 유효성 체크
    if (!check_id.test(member_id)) {
        $('.idCheck').html('영문 및 숫자만 4-10자리까지 입력해주세요.');
        $('#member_id').val("");
        $('#member_id').focus();
        return false;
    }

    // 비밀번호 공백 확인
    if (userpw == "" || userpw == null) {
        $('.pwCheck').html('비밀번호를 입력해주세요.');
        $('#userpw').focus();
        return false;
    }

    // 비밀번호 유효성 체크
    if (!check_pw.test(member_pw)) {
        $('.pwCheck').html('영문 및 숫자, 특수문자를 포함한 비밀번호를 입력해주세요.');
        $('#member_pw').val("");
        $('#member_pw').focus();
        return false;
    }

    // 닉네임 공백 체크
    if (member_nick == "" || member_nick == null) {
        $('.nickCheck').html('닉네임을 입력해주세요.');
        $('#member_nick').focus();
        return false;
    }

    // 닉네임 유효성 체크
    if (!check_nick.test(member_nick)) {
        $('.nickCheck').html('닉네임은 영문과 한글, 숫자로 생성 가능합니다.');
        $('#member_nick').val("");
        $('#member_nick').focus();
        return false;
    }

    //memberInsertForm.submit();


}