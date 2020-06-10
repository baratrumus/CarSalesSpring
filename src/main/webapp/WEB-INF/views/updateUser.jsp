<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset='UTF-8'>
    <title>User update</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="${baseUrl}/js/signup.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/script.js"></script>
</head>

<body>
<form id="userForm"  class="form_sign_up" onsubmit="return validate()" method='post' action="/users/update">

    <h2>User update</h2><br/>

    <span class="errClass">  ${bizyNameError} </span>

    <div>
        <b>Login:</b>
        <input type="text" class="form-control" id="username" name="username"  value="${user.getUsername()}"/>
        <div id="loginErrorJS"></div>
    </div>

    <div>
        <b>Password:</b>
        <input type="password"  class="form-control" id='password' name="password"  value="${user.getPassword()}"/>
        <div id="passErrorJS"></div>
    </div>

    <div>
        <b>Confirm password:</b>
        <input type="password" class="form-control" name="passwordConfirm" value="${user.getPassword()}"/>
        <span class="errClass"> ${passError} </span>
    </div>

    <div>
        <b>Email:</b>
        <input type="email" class="form-control" id='email' name='email' value="${user.getEmail()}"/>
        <div id="emailErrorJS"></div>
    </div>

    <div>
        <b>Phone:</b><br>
        <input type="text" class="form-control" id='phone' name='phone' value="${user.getPhone()}" />
        <div id="phoneErrorJS"></div>
    </div>

    <div class="spaceFill"></div>

    <input type="hidden" name='cameFromAdm' value="${cameFromAdm}" />
    <input type="hidden" name='editedUserId' value="${user.getId()}" />

    <input class="button1"  type='submit' value='Update'/>
    <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

</form>
</body>
</html>

