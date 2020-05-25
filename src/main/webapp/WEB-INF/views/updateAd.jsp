<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset='UTF-8'>
    <title>Update advertisment</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="js/script.js"></script>

</head>
<body>
    <form id="editForm"   class="form_sign_up"  method='post' action="${pageContext.servletContext.contextPath}/editAd"  enctype="multipart/form-data">
        <h2>Edit advertisment</h2>
        <input type="hidden" name='adId' value="<c:out value="${ad.getId()}" />">

        <table class="table">
            <thead>
            <tr>
                <th scope="col" width="25%"></th>
                <th scope="col" width="70%"></th>
            </tr>
            </thead>

            <tr><td>
                <b>Actuality:</b></td>
                <td><select name="isSold" id="isSold">
                    <option  <c:if test="${ad.getSold() == 'false'}"> selected </c:if>
                        value="false">In Sale</option>
                    <option  <c:if test="${ad.getSold() == 'true'}"> selected </c:if>
                            value="true">Sold</option>
                </select>
                </td></tr>

            <tr><td><b>Description: </b></td>
                <td><textarea rows="5" width="300px" name='description' id="description">
                   <c:out value="${ad.getDescr()}" />
                    </textarea><br><br>
                </td></tr>

            <tr><td><b>New photo:</b></td>
                <td><input type='file' name='file'><br>
                </td></tr>

            <tr><td><b>Price:</b></td>
                <td><input type='text' name='price' id='price' value="<c:out value="${ad.getPrice()}" />"><br>
                </td></tr>

            <tr><td>
                <button class="button1" type="submit">Apply</button>
            </td>

            <td>
                <input class="button1"  type='button' value='Main page' onclick="toMain()"/>
            </td>
            </tr>

        </table>
    </form>

</body>
</html>
