<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="">
<head>
    <title>User login</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <style>
        <%@include file="/css/style.css"%>
    </style>

    <script type="text/javascript" src="js/script.js"></script>


</head>

<body  class="text-center">

<form  class="form_sign_up" method='post' action="${pageContext.servletContext.contextPath}/signin">
    <c:if test="${error != ''}">
        <div style = "background-color: darksalmon">
            <c:out value="${error}" />
        </div>
    </c:if>

    <h2><span class="centred">Please sign in</span></h2>

    <table class="table1"><tr><td>
        <b>Login:</b></td>
        <td><input type="text" name='login' id="login" placeholder="Login" required>
        </td></tr>

        <tr><td>
        <b>Password:</b></td>
        <td><input type="password" name='password' id="password" placeholder="Password" required>
        </td></tr>

        <tr><td><div class="spaceFill">
        </div></td></tr>

    </table>

    <input class="button1"  type='submit' value='Sign in'/>
    <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

</form>
</body>
</html>
