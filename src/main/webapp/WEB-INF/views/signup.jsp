<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset='UTF-8'>
    <title>User create</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="js/signup.js"></script>

</head>

<body>
<form:form id="form1"  class="form_sign_up" modelAttribute="userForm" method="post" action="${pageContext.servletContext.contextPath}/signup">
    <c:if test="${error != ''}">
        <div style = "background-color: darksalmon">
            <c:out value="${error}" />
        </div>
    </c:if>

    <h2>Sign up</h2>

    <table><tr><td>
    <b>Login:</b></td>
    <td><input type="text" name='login' id="login" placeholder="Login" required>
    </td></tr>

    <tr><td>
     <b>Password:</b></td>
     <td><input type="password" name='password' id="password" placeholder="Password" required>
     </td></tr>

    <tr><td>
    <b>Email:</b></td>
    <td><input type="email" name='email' id="email" placeholder="Email" required>
    </td></tr>


    <tr><td>
    <b>Phone:</b><br></td>
    <td>

    <input type="text" class="form-control" id="phone">
    </td></tr>

    <tr><td><div class="spaceFill">

    </div></td></tr>

        <input type='hidden' name='created' value=''/><br>
    </table>

    <input class="button1"  type='submit' value='Create'/>
    <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

</form:form>
</body>
</html>
