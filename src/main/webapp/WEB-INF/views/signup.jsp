<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;image/*;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset='UTF-8'>
    <title>Sign up</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="js/signup.js"></script>

</head>

<body>
<form:form id="userForm"  class="form_sign_up" onsubmit="return validate()" modelAttribute="userForm" method="post" action="/signup">
    <c:if test="${error != ''}">
        <div class="errClass">
            <c:out value="${error}" />
        </div>
    </c:if>

    <h2><span class="centred">Sign up</span></h2>

    <div class="errClass"> ${DBerror} </div>

    <div>
        <b>Login:</b>
        <form:input type="text" class="form-control" id="username" path="username" placeholder="Login"/>
        <div id="loginErrorJS"></div>
        <span class="errClass">  ${bizyNameError} </span>
    </div>


    <div>
        <b>Password:</b>
        <form:input type="password"  class="form-control" id='password' path='password' placeholder="Password"/>
        <div id="passErrorJS"></div>
    </div>

    <div>
        <b>Confirm password:</b>
        <form:input type="password" class="form-control" path="passwordConfirm"
                    placeholder="Confirm your password"></form:input>
        <span class="errClass"> ${passError} </span>
    </div>

    <div>
        <b>Email:</b>
        <form:input type="email" class="form-control" id='email' path='email' placeholder="Email"/>
        <div id="emailErrorJS"></div>
    </div>

    <div>
        <b>Phone:</b><br>
        <form:input type="text" class="form-control" id='phone' path='phone'/>
        <div id="phoneErrorJS"></div>
    </div>

    <div class="spaceFill"></div>

    </tbody></table>

    <input class="button1"  type='submit' value='Create'/>
    <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

</form:form>
</body>
</html>
