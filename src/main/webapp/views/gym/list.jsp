<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="permitAll() and !hasRole('MANAGER') and !hasRole('CUSTOMER') and !hasRole('ADMINISTRATOR') and !hasRole('TRAINER')">

<jstl:if test="${a==3}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym.do,annotations: annotation/listByGym.do}" 
extraColumns="{avgStar: gym/avgStar.do}"/>

</jstl:if>

</security:authorize>

<security:authorize access="hasRole('MANAGER')"> 

<jstl:if test="${a==2}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym3.do,annotations: annotation/listByGym.do}" editUrl="gym/edit.do" deleteUrl="gym/delete.do"  
extraColumns="{newActivity: manageractor/activity/create.do, addTrainer: manageractor/trainer/addByGym.do, newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}"/>

</jstl:if>

<jstl:if test="${a==1}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym3.do,annotations: annotation/listByGym.do}" 
extraColumns="{newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}"/>

</jstl:if>

<jstl:if test="${a==3}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym3.do,annotations: annotation/listByGym.do}" 
extraColumns="{newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}"/>
 
</jstl:if>

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')"> 

<jstl:if test="${a==2}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym2.do,annotations: annotation/listByGym.do}" 
extraColumns="{leave: customer/gym/leave.do, newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}"  />

</jstl:if>

<jstl:if test="${a==3}">

<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym.do,annotations: annotation/listByGym.do}" 
extraColumns="{join: customer/gym/join.do, newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}" />

</jstl:if>


</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">


<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym.do,annotations: annotation/listByGym.do}" 
extraColumns="{newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}"/>


</security:authorize>


<security:authorize access="hasRole('TRAINER')">


<acme:list list="${gyms}" requestURI="gym/list.do" hidden_fields="customers,trainers,id,version,isDelete" 
entityUrl="{activities: activity/listByGym.do,annotations: annotation/listByGym.do}"
extraColumns="{newAnnotation: annotation/create.do, avgStar: gym/avgStar.do}" />


</security:authorize>

