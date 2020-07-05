[![Build Status](https://travis-ci.org/baratrumus/CarSalesSpring.svg?branch=master)](https://travis-ci.org/baratrumus/CarSalesSpring)
[![codecov](https://codecov.io/gh/baratrumus/CarSalesSpring/branch/master/graph/badge.svg)](https://codecov.io/gh/baratrumus/CarSalesSpring)



## Car sales site application. Version 2.0 Spring

##### Application <a href="https://carsales42.herokuapp.com">deployed to Heroku</a>.
App is not realy adapted to mobiles.  Please use desktop.
Wait a bit, heroku has init and loading lag.


#### General functionality.
Application have main page with all advertisements. This page is allowed for all users, including guests.
Guest can sign up, sign in or use Test Role button for quick authorization and authentication .
When authorized user appends new Ad, he chooses brand, model, bodytype, engine, photo, description, mileage, price.
Admin can make CRUD operations on all users.


#### Technologies
* Spring MVC, Spring Security. 
* PostgreSQL, HSQLDB, Liquibase, Hibernate with Spring Data
* Junit, Mockito
* JSP, HTML, CSS, Bootstrap, Javascript, JQuery, JSON, Ajax
* SL4J, LOG4J
* Travis CI, Codecov
* Heroku deployed
* Maven and plugins checkstyle, jacoco, webapp-runner


 #### Local run 
* Clone project. 
* You are to have JDK, Maven, Tomcat installed.
* Also you need PostgreSql. Create empty base named carsdb.
* Run CarsLocalRun.but from app folder 
* Open in browser localhost:8082/
* Port can be changed in bat file, if you have it buzy.
 

#### Authorities:
* Guest: looks all advertisements
* User: Guest func + CRUD of his own advertisements, update own profile data. 
* Admin: User func + CRUD of all users.


#### Ads Filters:
Last day, In sale now, with Photo, By Brand.


## Details 

##### Project divided by layers: View, Controller, Service, Repository

##### Remember me function implemented. 
As user login with set checkbox 'Remember me' - token saved to base and to browser cookies.  As long as user logged and dont press logout - he will have auto login function in token life time - set one day.
Also Context remembers target page, that user looked last.

##### Localization implemented. Resource Bundle 'ru', 'en'.

##### Postgresql used as general db. But there is an opportunity to get HSQLDB. You need only check hsqldb maven profile. 

##### All structure and initial data are populated via Liquibase.

##### Javascript form validation is used.

##### Ajax used to populate models after brand choise.

##### Annotation configuration implemented.


## General views

 ![Sign up with validation](https://github.com/baratrumus/CarSalesSpring/raw/master/readmePics/signup.JPG) 
 
 ![Main screen for guest](https://github.com/baratrumus/CarSalesSpring/raw/master/readmePics/guestMain.JPG) 
  
 ![Main screen for user](https://github.com/baratrumus/CarSalesSpring/raw/master/readmePics/userMain.JPG) 
   
 ![New advertisement with validation](https://github.com/baratrumus/CarSalesSpring/raw/master/readmePics/newAd.JPG) 
 


TO DO :
BaseSpringApp Locale RU EN main page
Controller tests

