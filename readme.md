[![Build Status](https://travis-ci.org/baratrumus/CarSalesSpring.svg?branch=master)](https://travis-ci.org/baratrumus/CarSalesSpring)
[![codecov](https://codecov.io/gh/baratrumus/CarSalesSpring/branch/master/graph/badge.svg)](https://codecov.io/gh/baratrumus/CarSalesSpring)



## Car sales site application. Version 2.0 Spring

#### Technologies
* Maven
* Spring MVC, Spring Security. 
* PostgreSQL, HSQLDB, Liquibase, Hibernate with Spring Data
* Junit, Mockito
* JSP, HTML, CSS, Bootstrap, Javascript, JQuery, JSON, Ajax
* SL4J, LOG4J


 #### General functionality.
Application have main page with all advertisments. This page is allowed for all users, including guests.
Guest can sign up, sign in or use Test Role button for quick authorization and authentication .
When authorized user appends new Ad, he chooses brand, model, bodytype, engine, photo, description, mileage, price.
Admin can make CRUD operations on all users.


#### Authorities:
Guest: looks all advertisments
User: Guest func + CRUD of his own advertisments, update own profile data. 
Admin: User func + CRUD of all users.


#### Ads Filters:
Last day, In sale now, with Photo, By Brand.


## Details 

#### Remember me function implemented. 
As user login with set checkbox 'Remember me' - token saved to base and to browser cookies.  As long as user logged and dont press logout - he will have auto login function in token life time - set one day.
Also Context remembers target page, that user looked last.

##### Localization implemented. Resource Bundle 'ru', 'en'.

##### Postgresql used as general db. But there is an opportunity to get HSQLDB. You need only check hsqldb maven profile. 

##### All structure and initial data are populated via Liquibase.

##### Javascript form validation is used.

##### Ajax used to populate models after brand choise.

##### Annotation configuration implemented.


## General views

 ![Sign up with validation](withSpring/readmePisc/signup.jpg) 
 
 ![Main screen for guest](readmePisc/guestMain.jpg) 
  
 ![Main screen for user](readmePisc/userMain.jpg) 
   
 ![New advertisment with validation](readmePisc/newAd.jpg) 
 


TO DO :
BaseSpringApp Locale MessageSource RU EN

