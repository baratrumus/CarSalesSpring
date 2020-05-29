<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Error</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <style>
        <%@include file="/css/style.css"%>
    </style>

</head>

<body  class="text-center">

    <h1><span class="centred">${errorName}</span></h1>
    <div class="row">
        <div>${description}"></div>
    </div>

</body>
</html>

