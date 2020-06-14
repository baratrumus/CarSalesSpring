<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;image/*;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Users</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <style>
        body {
            background-image: url("${baseUrl}/img/car_back.jpg");
            background-size: contain, cover;
            background-repeat: no-repeat;
        }
    </style>

    <script type="text/javascript" src="${baseUrl}/js/script.js"></script>


</head>
<body>

<div class="container_header" id="topBar">
    <div class="statusArea" id="topBarIn">
        <c:if test="${not empty uEdited}">
            <p><b>User successfully updated.</b></p>
        </c:if>
        <c:if test="${userDeleted == 'yes'}">
            <p><b>User removed. </b></p>
        </c:if>
    </div>
</div>

<c:set var = "userDeleted" scope = "session" value = ""/>
<c:set var = "uEdited" scope = "session" value = ""/>


<div class="container-my">
    <div class="statusArea">
        <ul>
            <li>
                <input class="button1"  type='button' value='Main page' onclick="toMain()"/>
            </li>
        </ul>
    </div>
</div>


<div class="spaceFill">
</div>


<div class="container-my">
    <table class="table table-striped" border='1' cellpadding='3'>
        <thead>
        <tr>
            <th scope="col" width="15%">Actions</th>
            <th scope="col" width="5%">Id</th>
            <th scope="col" width="12%">Username</th>
            <th scope="col" width="10%">Email</th>
            <th scope="col" width="10%">Phone</th>
        </tr>
        </thead>

        <c:forEach var="user" items="${listOfUsers}">
            <tr><td>
                <form:form method='get'  action="/users/update/${user.getId()}">
                    <input type="hidden" name='cameFromAdm' value="yes" />
                    <input type='submit' value='Edit'/>
                </form:form>
                <br>
                <form:form method='post' action="/users/delete/${user.getId()}">
                    <input type="hidden" name='id' value="${user.getId()}" />
                    <input type='submit' value='Delete'/>
                </form:form>
                </td>

                <td><c:out value="${user.getId()}" /></td>

                <td><c:out value="${user.getUsername()}" /></td>

                <td><c:out value="${user.getEmail()}" /></td>

                <td><c:out value="${user.getPhone()}" /></td>

             </tr>
        </c:forEach>
    </table>

</div>


</body>
</html>
