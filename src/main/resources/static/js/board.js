let index = {
    init: function () {
        $("#btn-board-save").on("click", () => {
            this.save();
        });
        $("#btn-board-delete").on("click", () => {
            this.deleteById();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        });
    },
    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),//http body 데이터
            contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
        }).done(function (resp) {
            alert("글쓰기 완료되었습니다.");
            //console.log(resp);
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteById: function () {
        var id = $("#id").text();
        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
        }).done(function (resp) {
            console.log(resp)
            if(resp.data == 1){
                alert("글삭제가 완료되었습니다.");
            }
            else{
                alert("접근제한으로 글삭제를 실패하였습니다.");
            }
            location.href = "/";
        }).fail(function (error) {
            alert("글삭제실패"+JSON.stringify(error));
        });
    },
    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),//http body 데이터
            contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
        }).done(function (resp) {
            alert("글수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replySave: function () {
        let data = {
            content: $("#reply-content").val(),
        };

        let boardId = $("#boardid").val();

        $.ajax({
            type: "POST",
            url: `/api/board/${boardId}/reply`,
            data: JSON.stringify(data),//http body 데이터
            contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javasript로변 환
        }).done(function (resp) {
            alert("댓글 작성 완료되었습니다.");
            //console.log(resp);
            location.href = `/board/${boardId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}
index.init();