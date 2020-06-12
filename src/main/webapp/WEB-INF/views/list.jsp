<%@ page contentType="text/html;image/*;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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

    <script type="text/javascript" src="${baseUrl}/js/script.js"></script>

</head>

<body>
<security:authorize access="isAuthenticated()" var="isAuthenticated"/>

<security:authorize access="isAuthenticated()">
    <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isAuthorized"/>
    <security:authentication property="principal" var="principal"/>
    <security:authorize access="hasRole('ROLE_ADMIN')" var="securRole"/>
</security:authorize>

<div class="container_header" id="topBar">

    <div class="statusArea" id="topBarIn">
        <security:authorize access="isAnonymous()">
            <b> Hello, Guest.</b>
            <c:if test="${param.logout == 'true'}">
                <b>You have been logged out.</b>
            </c:if>
        </security:authorize>


        <security:authorize access="isAuthenticated()">
             <b> You are logged as ${principal.username}. </b>
        </security:authorize>

        <security:authorize access="hasRole('ROLE_ADMIN')">
             <b>You can edit all users and advertisments.</b>
        </security:authorize>

        <c:if test="${sessionScope.adCreated == 'yes'}">
            <p><b>Your advertisment created.</b></p>
        </c:if>

        <c:if test="${sessionScope.adEdited == 'yes'}">
            <p><b>Your advertisment updated.</b></p>
        </c:if>

        <c:if test="${sessionScope.adDeleted == 'yes'}">
            <p><b>Advertisment removed.</b></p>
        </c:if>

        <c:if test="${sessionScope.uEdited == 'yes'}">
             <p><b>Your account is successfully updated.</b></p>
        </c:if>

        <span id="rolesButtons"></span>

        <c:set var = "adCreated" scope = "session" value = ""/>
        <c:set var = "uEdited" scope = "session" value = ""/>
        <c:set var = "adDeleted" scope = "session" value = ""/>
        <c:set var = "adEdited" scope = "session" value = ""/>
    </div>
</div>

<div class="spaceFill"></div>

<div class="container-my">
    <div class="statusArea">
        <c:if test="${not isAuthenticated}">
                <ul>
                   <li>
                       <form:form action="/users/signup" method='get'>
                            <button type="submit" class="button1">
                                <span class="glyphicon glyphicon-pencil"></span> Sign Up
                            </button>
                       </form:form>
                   </li>
                   <li> </li>
                   <li>
                       <form:form action="login" method='get'>
                            <button type="submit" class="button1">
                                <span class="glyphicon glyphicon-pencil"></span> Login
                            </button>
                       </form:form>
                   </li>
                   <li>
                        <button type="submit" class="buttonTR" onclick="showTestRoles()">
                            <span class="glyphicon glyphicon-pencil"></span> Test Roles
                        </button>
                   </li>
                </ul>
        </c:if>
        <c:if test="${isAuthenticated}">
            <ul>
                <li>
                    <form:form action="/ad/create" method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Create Advertisment
                        </button>
                    </form:form>
                </li>
                <li>
                    <form action="/users/update/${sessionScope.userId}" method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Edit account
                        </button>
                    </form>
                </li>
                <li>


                  <form action="<c:url value='/logout'/>" method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Log Out
                        </button>
                  </form>

                </li>
            </ul>
        </c:if>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <ul>
                <li>
                    <form:form action='${baseUrl}/users/admin' method='get'>
                        <button type="submit" class="button1">
                            <span class="glyphicon glyphicon-pencil"></span> Edit users
                        </button>
                    </form:form>
                </li>

            </ul>
        </security:authorize>
    </div>
</div>

<div class="container-my">
    <div class="filterArea">
    <form:form method='get' action="${baseUrl}/">
        <table class="tableFilter"><tr>

            <td style="font-size: 16px;"><b>Filters:</b></td>
            <c:if test="${isAuthorized}">
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
    </form:form>
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

           <c:if test="${((principal.getAuthorities() == 'ROLE_ADMIN') || (sessionScope.userId == ad.getUserId().getId()))}">

               <form:form method='get' action="/ad/update/${ad.getId()}">
                   <input type="hidden" name='adId' value="<c:out value="${ad.getId()}" />">
                   <input type='submit' value='Edit'/>
               </form:form>
               <br>
               <form:form method='post' action="/ad/delete">
                        <input type="hidden" name='delId' value="<c:out value="${ad.getId()}" />">
                        <input type='submit' value='Delete'/>
               </form:form>
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
                   <c:out value="${ad.getUserId().getUsername()}" /><br>
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

           <br><br><b>Price: <c:out value="${ad.getPrice()}" />$</b>
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
               Mileage:<br>
               <b><c:out value="${ad.getCarDetails().getMileage()}" /></b><br>
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
