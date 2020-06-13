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

    <script type="text/javascript" src="${baseUrl}/js/script.js"></script>

</head>
<body>
    <form:form id="editForm"  class="form_sign_up" onsubmit="return validatePrice()"  modelAttribute="FormDataWithFile"  method='post' action="/ad/update"  enctype="multipart/form-data">
        <h2>Edit advertisment</h2><br/>

        <span class="errClass">  ${error} </span>


        <div class="twoBlockInline">
            <span class="twoInlineLable">Actuality:</span>
            <span class="twoInlineElem">
                <select  class="form-control" name="sold" id="sold">
                    <option  <c:if test="${ad.getSold() == 'false'}"> selected </c:if>
                            value="false">In Sale</option>
                    <option  <c:if test="${ad.getSold() == 'true'}"> selected </c:if>
                            value="true">Sold</option>
                </select>
            </span>
        </div>

        <div class="twoBlockInline">
            <span class="twoInlineLable">Description:</span>
            <span class="twoInlineElem">
                <textarea  class="form-control" rows="5" width="500px" name='description' id="description">
                   <c:out value="${ad.getDescr()}" />
                </textarea>
            </span>
        </div>

        <div class="twoBlockInline">
            <span class="twoInlineLable">New photo:</span>
            <span class="twoInlineElem">
                <input class="form-control" type='file' name='file'>
            </span>
        </div>

        <div class="twoBlockInline">
            <span class="twoInlineLable"></span>
            <div id="priceError" class="twoBlockInline"></div>
        </div>

        <div class="twoBlockInline">
            <span class="twoInlineLable">Price:</span>
            <span class="twoInlineElem">
                <input class="form-control" type='text' name='price' id='price' value="<c:out value="${ad.getPrice()}" />"/>
            </span>
        </div>

        <div class="twoBlockInline marginSpace">

          <span class="centred">
            <button class="twoInlineLable button1" type="submit">Apply</button>
            <input class="twoInlineLable button1"  type='button' value='Main page' onclick="toMain()"/>
          </span>
        </div>

        <input type="hidden" name='adId' value="<c:out value="${ad.getId()}" />">

    </form:form>

</body>
</html>
