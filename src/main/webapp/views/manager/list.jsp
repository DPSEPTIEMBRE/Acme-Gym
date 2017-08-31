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


<security:authorize access="hasRole('ADMINISTRATOR')"> 

<jstl:if test="${a==1}">

<acme:list list="${managers}" requestURI="manageractor/list.do" hidden_fields="gyms,userAccount,annotationWriter,id,version" 
entityUrl="{annotationStore: annotation/listByManager.do}" 
extraColumns="{newAnnotation: annotation/create.do, avgStar: manageractor/avgStar.do}" />

</jstl:if>

<jstl:if test="${a==2}">

<acme:list list="${managers}" requestURI="manageractor/list.do" hidden_fields="gyms,userAccount,annotationWriter,id,version" 
extraColumns="{remove: administrator/manageractor/remove.do, newAnnotation: annotation/create.do, avgStar: manageractor/avgStar.do}" 
entityUrl="{annotationStore: annotation/listByManager.do}"/>

</jstl:if>

<jstl:if test="${a==3}">

<acme:list list="${managers}" requestURI="manageractor/list.do" hidden_fields="gyms,userAccount,annotationWriter,id,version" 
extraColumns="{admit: administrator/manageractor/admit.do, newAnnotation: annotation/create.do, avgStar: manageractor/avgStar.do}" 
entityUrl="{annotationStore: annotation/listByManager.do}"/>
</jstl:if>

</security:authorize>
