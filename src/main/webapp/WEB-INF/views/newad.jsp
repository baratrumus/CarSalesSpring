<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
    <title>Create advertisment</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="${baseUrl}/js/script.js"></script>

    <script>
        $(getModelsById('1'));
    </script>

</head>
<body>

<form:form id="adForm"  class="form_sign_up" modelAttribute="FormDataWithFile" enctype="multipart/form-data"  onsubmit="return validateNewAd()"  method='post' action="/ad/create">
        <h2>Create advertisment</h2></br>

        <span class="errClass">  ${error} </span>

        <div class="twoBlockInline">
            <span class="twoInlineLable">Brand:</span>
            <span class="twoInlineElem"><select class="form-control" name="brands" id="brands" onchange="getModelsById('0');">
                            <c:forEach var="brand" items="${brandList}">
                                <option <c:if test="${brand.getId() == 1}"> selected </c:if>
                                        value="${brand.getId()}">${brand.getBrandName()}</option>
                            </c:forEach>
                    </select>
            </span>
        </div>

        <div class="twoBlockInline">
            <span class="twoInlineLable">Model:</span>
            <span class="twoInlineElem"> <select class="form-control" id="models" name="models"> </select></span>
        </div>

         <div class="twoBlockInline">
             <span class="twoInlineLable">Body type:</span>
             <span class="twoInlineElem"><select class="form-control" name="body" id="body">
                    <c:forEach var="body" items="${bodyList}">
                        <option value="${body.getId()}">${body.getBodyName()}</option>
                    </c:forEach>
                     </select>
             </span>
         </div>


         <div class="twoBlockInline">
             <span class="twoInlineLable">Engine:</span>
             <span class="twoInlineElem"><select class="form-control" name="engine" id="engine">
                    <c:forEach var="engine" items="${engineList}">
                        <option value="${engine.getId()}">${engine.getEngineName()}</option>
                    </c:forEach>
                </select>
             </span>
         </div>

         <div class="twoBlockInline">
             <span class="twoInlineLable">Mileage:</span>
             <span class="twoInlineElem">  <input class="form-control" type='text' path="miliage" name="mileage" id="mileage"/></span>
         </div>
            <form:errors path="mileage" />


         <div class="twoBlockInline">
             <span class="twoInlineLable">Car year:</span>
             <span class="twoInlineElem"><input class="form-control" type="text" name="caryear" id="caryear"/></span>
         </div>

         <div class="twoBlockInline">
             <span class="twoInlineLable">Color:</span>
             <span class="twoInlineElem"><input class="form-control" type='text' name='color' id="color"/></span>
         </div>


          <div class="twoBlockInline">
              <span class="twoInlineLable">Photo:</span>
              <span class="twoInlineElem"><input class="form-control" type="file" path="file" name="file" id="file"></span>
          </div>
            <form:errors path="file" />


          <div class="twoBlockInline">
              <span class="twoInlineLable">Price:</span>
              <span class="twoInlineElem"><input class="form-control" type='text' name='price' id="price"/></span>
          </div>

          <div class="twoBlockInline">
            <div class="twoInlineLable">Description:</div>
            <div class="twoInlineElem"><textarea class="form-control" rows="5" width="500px" name='description' id="description">
                 </textarea></div>
          </div>

    <div class="twoBlockInline marginSpace">

          <span class="centred">
            <button class="twoInlineLable button1" type="submit"   onclick="validateNewAd();">Apply</button>
            <input class="twoInlineLable button1"  type='button' value='Main page' onclick="toMain()"/>
          </span>
    </div>

</form:form>


</body>
</html>
