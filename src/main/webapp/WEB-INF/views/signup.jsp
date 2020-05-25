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
<form:form id="userForm"  class="form_sign_up" onsubmit="return validate()" modelAttribute="userForm" method="post" action="${pageContext.servletContext.contextPath}/signup">
    <c:if test="${error != ''}">
        <div style = "background-color: darksalmon">
            <c:out value="${error}" />
        </div>
    </c:if>

    <h2><span class="centred">Sign up</span></h2>

    <table class="table1"><tbody>
    <tr><td></td>
        <td><div id="loginError"></div></td></tr>
    <tr><td><b>Login:</b></td>
        <td><input type="text"  class="form-control" name='login' id="login" placeholder="Login"></td>
    </tr>

    <tr><td></td>
        <td><div id="passError"></div></td></tr>
    <tr><td><b>Password:</b></td>
        <td><input type="password"  class="form-control" name='password' id="password" placeholder="Password">
        </td></tr>

    <tr><td></td>
        <td><div id="emailError"></div></td></tr>
    <tr>
        <td><b>Email:</b></td>
        <td><input type="email"  class="form-control" name='email' id="email" placeholder="Email">
        </td></tr>

    <tr><td></td>
        <td><div id="phoneError"></div></td></tr>
    <tr>
        <td><b>Phone:</b><br></td>
        <td><input type="text" class="form-control" name='phone' id="phone"> </td></tr>
    <tr><td><div class="spaceFill">

    </div></td></tr>

    <input type='hidden' name='created' value=''/><br>
    </tbody></table>

    <input class="button1"  type='submit' value='Create'/>
    <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

</form:form>
</body>
</html>
