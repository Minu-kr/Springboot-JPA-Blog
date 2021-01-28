<%@ page language="java" contentType="text/html; charset = UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <div class="form-check">
            <label class="form-check-label">
                <input type="checkbox" name="remember-me" class="form-check-input" value="">자동 로그인
            </label>
        </div>
        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=78341655b0e6f515a025aacc898c2108&redirect_uri=http://localhost:8088/auth/kakao/callback&response_type=code
"><img height="38px" src="/image/kakao_login_button.png"/></a>
    </form>
</div>
<%@ include file="../layout/footer.jsp"%>
