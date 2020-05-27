<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Create advertisment</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="js/script.js"></script>

    <script>
        $(getModelsById('1'));
    </script>

</head>
<body>


<form id="adForm"   class="form_sign_up" onsubmit="return validateNewAd()"  method='post' action="${pageContext.servletContext.contextPath}/createAd"  enctype="multipart/form-data">
        <h2>Create advertisment</h2>

        <table class="table">
            <thead>
            <tr>
                <th scope="col" width="30%"></th>
                <th scope="col" width="50%"></th>
            </tr>
            </thead>

            <tr><td>
            <b>Brand:</b></td>
            <td><select name="brands" id="brands"  width="100px" onchange="getModelsById('0');">
                    <c:forEach var="brand" items="${brandList}">
                        <option <c:if test="${brand.getId() == 1}"> selected </c:if>
                                value="${brand.getId()}">${brand.getBrandName()}</option>
                    </c:forEach>
                </select>
            </td></tr>


            <tr><td><b>Model:</b></td>

                <td><select id="models" name="models" width="100px">

                </select>
                </td></tr>


            <tr><td><b>Body type:</b></td>
                <td><select name="body" id="body" width="100px">
                    <c:forEach var="body" items="${bodyList}">
                        <option value="${body.getId()}">${body.getBodyName()}</option>
                    </c:forEach>
                </select>
                </td></tr>


            <tr><td>
                <b>Engine:</b></td>
                <td><select name="engine" id="engine"  width="100px">
                    <c:forEach var="engine" items="${engineList}">
                        <option value="${engine.getId()}">${engine.getEngineName()}</option>
                    </c:forEach>
                </select>
                </td></tr>

            <tr><td><b>Car year:</b></td>
                <td><input type='text' name='caryear' id="caryear"/><br><br>
                </td></tr>

            <tr><td><b>Color:</b></td>
                <td><input type='text' name='color' id="color"/><br><br>
                </td></tr>

            <tr><td><b>Description:</b></td>
                <td><textarea rows="5" width="300px" name='description' id="description">
                    </textarea><br><br>
                </td></tr>

            <tr><td><b>Photo:</b></td>
                <td><input type="file" name="file" id="file"><br>
                </td></tr>

            <tr><td><b>Price:</b></td>
                <td><input type='text' name='price' id="price"/><br>
                </td></tr>

            <tr><td>
                <button class="button1" type="submit"   onclick="validateNewAd();">Apply</button>
            </td>
            <td>
            <input class="button1"  type='button' value='Main page' onclick="toMain()"/>
            </td>
            </tr>

        </table>
</form>


</body>
</html>
