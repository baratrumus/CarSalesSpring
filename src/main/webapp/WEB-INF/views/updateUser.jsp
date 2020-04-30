<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update</title>
    <meta charset='UTF-8'>

    <title>Create advertisment</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="js/script.js"></script>
</head>

<body>
<form id="editForm"   class="form_sign_up"  method='post' action="${pageContext.servletContext.contextPath}/editUser">
    <h2>User update</h2>
        <c:if test="${error != ''}">
            <div style = "background-color: darksalmon">
                <c:out value="${error}" />
            </div>
        </c:if>

        <table><tr><td>
            <b>Login:</b></td>
            <td><input type="text" name='login' id="login" value="<c:out value="${user.getLogin()}"/>"><br>
            </td></tr>

            <tr><td>
                <b>Password:</b></td>
                <td><input type="password" name='password' id="password"  value="<c:out value="${user.getPassword()}"/>"><br>
                </td></tr>

            <tr><td>
                <b>Email:</b></td>
                <td><input type="email" name='email' id="email" value="<c:out value="${user.getEmail()}"/>"><br>
                </td></tr>

            <tr><td>
                <b>Phone:</b><br></td>
                <td><input type="tel" id="phone" name="phone" pattern="[+]{1}[0-9]{7-14}"  value="<c:out value="${user.getPhone()}"/>"><br>
                </td></tr>

            <tr><td><div class="spaceFill">

            </div></td></tr>
        </table>

        <c:if test="${userIdfromAdm != null}">
            <input type="hidden" name='userIdfromAdm' value="<c:out value="${user.getId()}" />">
        </c:if>



        <input class="button1"  type='submit' value='Update'/>
        <input class="button1"  type='button' value='Main page' onclick="toMain()"/>

    </form>

</body>
</html>

