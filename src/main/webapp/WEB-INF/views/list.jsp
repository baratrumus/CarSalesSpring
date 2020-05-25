<%@ page contentType="text/html;image/*;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Advertisments</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

    <style><%@include file="/css/style.css"%></style>

    <c:set var = "baseUrl" scope = "session" value = "${pageContext.servletContext.contextPath}"/>

    <style>
        body {
            background-image: url("${baseUrl}/img/car_back.jpg");
            background-size: contain, cover;
            background-repeat: no-repeat;
         }
    </style>

    <script type="text/javascript" src="js/script.js"></script>

</head>

<body>

<input id="sessionRoleName" type='hidden' name='roles' value="${sessionScope.roleName}" /><br>

<div class="container_header" id="topBar">
    <div class="statusArea" id="topBarIn">
        <b> Hello, <c:out value="${sessionScope.login}" />.</b>
        <c:if test="${sessionScope.roleName == 'Admin'}">
            You can edit all users and advertisments.
        </c:if>
        <c:if test="${adCreated == 'yes'}">
         <p><b>Your advertisment created.</b></p>
        </c:if>
        <c:if test="${adEdited == 'yes'}">
            <p><b>Your advertisment updated.</b></p>
        </c:if>
        <c:if test="${adDeleted == 'yes'}">
            <p><b>User removed. </b></p>
        </c:if>
        <c:if test="${userCreated != null}">
            <p>User <b><c:out value="${userCreated.getLogin()}" /></b> created.</p>
        </c:if>
        <c:if test="${userEdited == 'yes'}">
            <p><b>Your account updated. </b></p>
        </c:if>
        <span id="rolesButtons"></span>
    </div>
</div>


<div class="container-my">
    <div class="statusArea">
        <c:if test="${sessionScope.roleName == 'Guest'}">
                <ul>
                   <li>
                       <form action="signup" method='get'>
                            <button type="submit" class="button1">
                                <span class="glyphicon glyphicon-pencil"></span> Sign Up
                            </button>
                       </form>
                   </li>
                    <li class=""li_space> </li>
                   <li>
                       <form action="signin" method='get'>
                            <button type="submit" class="button1">
                                <span class="glyphicon glyphicon-pencil"></span> Sign In
                            </button>
                       </form>
                   </li>
                   <li>
                        <button type="submit" class="buttonTR" onclick="showTestRoles()">
                            <span class="glyphicon glyphicon-pencil"></span> Test Roles
                        </button>
                   </li>
                </ul>
        </c:if>
        <c:if test="${((sessionScope.roleName == 'Admin') || (sessionScope.roleName == 'User'))}">
            <ul>
                <li>
                    <form action="/ad/create" method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Create Advertisment
                        </button>
                    </form>
                </li>
                <li>
                    <form action="/users/update/${sessionScope.id}" method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Edit account
                        </button>
                    </form>
                </li>
                <li>
                    <form action='signout' method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Sign Out
                        </button>
                    </form>
                </li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.roleName == 'Admin'}">
            <ul>
                <li>
                    <form action='showusers' method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Edit users
                        </button>
                    </form>
                </li>

            </ul>
        </c:if>
    </div>
</div>

<div class="container-my">
    <div class="filterArea">
    <form method='get' action="${baseUrl}/">
        <table class="tableFilter"><tr>

            <td style="font-size: 16px;"><b>Filters:</b></td>
            <c:if test="${sessionScope.roleName != 'Guest'}">
                <td>  <b>Your ads:</b>
                    <input name="onlyUserAds" type="checkbox" value="yes"
                           <c:if test="${onlyUserAds != null}">checked </c:if>
                    /></td>
            </c:if>


            <td>
                <b>Last day:</b>
                <input name="lastDay" type="checkbox" value="yes"
                       <c:if test="${lastDay != null}">checked </c:if>
                /></td>

            <td>
                <b>In sale now:</b>
                <input name="inSale" type="checkbox" value="yes"
                       <c:if test="${inSale != null}">checked </c:if>
                /></td>

            <td>
                <b>with Photo:</b>
                <input name="withPhoto" type="checkbox" value="yes"
                       <c:if test="${withPhoto != null}">checked </c:if>
                /></td>

            <td>

                <b>By brand:</b>
                <input name="checkBrand" type="checkbox" value="yes"
                       <c:if test="${checkBrand != null}">checked </c:if> />

                <select name="brands" id="brands"  width="100px">
                    <c:forEach var="brand" items="${brandList}">
                        <option  <c:if test="${brand.getId() == brandId}"> selected  </c:if>
                                value="${brand.getId()}">${brand.getBrandName()} </option>
                    </c:forEach>
                </select>
            </td>

            <td>
                <input class="buttonSmall"  type='submit' value='Apply'/>
            </td>
        </tr></table>
    </form>
    </div>
</div>

<div class="container-my">
    <table class="table table-striped" border='1' cellpadding='3'>
        <thead>
            <tr>
                <th scope="col" width="15%">Actions</th>
                <th scope="col" width="5%">Id</th>
                <th scope="col" width="12%">Actuality/Price</th>
                <th scope="col" width="10%">Creation date</th>
                <th scope="col" width="10%">Car</th>
                <th scope="col" width="20%">Description</th>
                <th scope="col" width="220px">Photo</th>
            </tr>
        </thead>

    <c:forEach var="ad" items="${listOfAds}">
       <tr><td>
           <c:if test="${((sessionScope.roleName == 'Admin') || (sessionScope.id == ad.getUserId().getId()))}">

               <form method='get' action="editAd">
                   <input type="hidden" name='adId' value="<c:out value="${ad.getId()}" />">
                   <input type='submit' value='Edit'/>
               </form>
               <br>
               <form method='post' action="${baseUrl}/">
                        <input type="hidden" name='delId' value="<c:out value="${ad.getId()}" />">
                        <input type='submit' value='Delete'/>
               </form>
           </c:if>

          <div style="margin-top: 20px;"/>
               <button type="button" data-toggle="collapse"
                       data-target="#collapse1<c:out value="${ad.getId()}" />"
                       aria-expanded="false" aria-controls="collapse1<c:out value="${ad.getId()}" />">
                   Contacts
               </button>
          </div>

           <div class="collapse" id="collapse1<c:out value="${ad.getId()}" />">
               <div style="margin-top: 20px;">
                   <b>Name:</b><br>
                   <c:out value="${ad.getUserId().getLogin()}" /><br>
                   <b>Phone:</b><br>
                   <c:out value="${ad.getUserId().getPhone()}" /><br>
                   <b>Email:</b><br>
                   <c:out value="${ad.getUserId().getEmail()}" /><br>
               </div>
           </div>
           </td>

           <td><c:out value="${ad.getId()}" /></td>

           <td>
           <c:if test="${ad.getSold() == true}">
               <span class="red">Sold</span>
           </c:if>
           <c:if test="${ad.getSold() == false}">
               <span class="green">In sale</span>
           </c:if>

           <br><br><b>Price: <c:out value="${ad.getPrice()}" /></b>
           </td>


           <td> <c:out value="${ad.getDateTime()}" /></td>

           <td>
               Brand:<br>
               <b><c:out value="${ad.getCarDetails().getBrand().getBrandName()}" /></b><br>
               Model:<br>
               <b><c:out value="${ad.getCarDetails().getModel().getModelName()}" /></b><br>
               Body type:<br>
               <b><c:out value="${ad.getCarDetails().getBody().getBodyName()}" /></b><br>
               Engine:<br>
               <b><c:out value="${ad.getCarDetails().getEngine().getEngineName()}" /></b><br>
               Year:<br>
               <b><c:out value="${ad.getCarDetails().getCarYear()}" /></b><br>
               Color:<br>
               <b><c:out value="${ad.getCarDetails().getColor()}" /></b><br>
           </td>


           <td>
               <c:out value="${ad.getDescr()}" />
           </td>

            <c:set var="isPhoto" scope="page">${ad.isPhotoExists()}</c:set>

            <td><div  class="centred_preview" >
                <c:if test="${isPhoto == false}">
                    <img class="car_image" src="${baseUrl}/img/noPhoto.jpg" width="200px"/>
                </c:if>
                <c:if test="${isPhoto == true}">
                    <img src="data:image/jpeg;base64, <c:out value="${ad.getPhotoBase64()}" />"  width="200px"/>
                </c:if>
            </div>
            </td>


       </tr>
    </c:forEach>

    </table>

</div>


</body>
</html>
