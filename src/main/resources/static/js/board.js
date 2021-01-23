let index = {
    init: function (){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },
    save:function (){
        //alert('user의 save 호출됨');
        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val()
        };
        //console.log(data)
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        //회원가입 수행요청이 길어도 비동기로 실행함
        //ajax가 통신을 성공하고나서 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),//http body 데이터
            contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지
            dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            //console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });





    },

}
index.init();

// login:function (){
//     //alert('user의 save 호출됨');
//     let data = {
//         username : $("#username").val(),
//         password : $("#password").val(),
//     };
//     $.ajax({
//         type: "POST",
//         url: "/api/user/login",
//         data: JSON.stringify(data),//http body 데이터
//         contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지
//         dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
//     }).done(function(resp){
//         alert("로그인이 완료되었습니다.");
//         //console.log(resp);
//         location.href = "/";
//     }).fail(function(error){
//         alert(JSON.stringify(error));
//     });
// }