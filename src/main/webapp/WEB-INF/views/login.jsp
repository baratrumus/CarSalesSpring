<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="">
<head>
    <title>User login</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <c:set var = "baseUrl" scope = "session" value = "${pageContext.servletContext.contextPath}"/>

    <spring:url value="${baseUrl}/css/style.css" var="style_main"/>
    <spring:url value="${baseUrl}/css/login.css" var="login_style"/>

    <link href="${style_main}" type="text/css" rel="stylesheet" />
    <link href="${login_style}" type="text/css" rel="stylesheet" />

<%--    <style> <%@include file="/css/style.css"%>  </style>--%>

    <script type="text/javascript" src="js/script.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>

</head>

<body  class="text-center">
    <form class="form-signin"  method='post' action="<c:url value='/login?targetUrl=${targetUrl}'/>">

        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isAuthorized"/>

        <h2 class="form-signin-heading"><spring:message code="loginForm.message"/></h2>

        <c:if test="${not empty param.error}">
            <div class="errClass"><spring:message code="loginForm.passError"/></div>
        </c:if>

        <c:if test="${not empty param.created}">
            <div class="successClass"><spring:message code="loginForm.successReg"/></div>
        </c:if>

        <input type="text" name='username' id="username" class="form-control"
               placeholder="<spring:message code="loginForm.login"/>" required autofocus/>

        <input type="password" name='password' id="password" class="form-control"
               placeholder="<spring:message code="loginForm.password"/>" required/>

        <div>
            <span>
                <label class="checkbox">
                    <input name="remember-me" id="remember-me" type="checkbox"/> <span> <spring:message code="loginForm.remember"/> </span>
                </label>

                <a href="/login?locale=ru"><img src="${baseUrl}/img/ru.png"/></a>
                <a href="/login?locale=en"><img src="${baseUrl}/img/en.png"/></a>
            </span>

            <input class="button1"  type='submit' value='<spring:message code="loginForm.signInBtn"/>'/>
            <input class="button1"  type='button' value='<spring:message code="loginform.mainBtn"/>' onclick="toMain()"/>
        </div>

    </form>

</body>
</html>
