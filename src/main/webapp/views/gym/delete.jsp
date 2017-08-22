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

<security:authorize access="hasRole('MANAGER')">

<h1><spring:message code="gym.delete"/></h1>

<spring:message code="gym.erase" var="gymSaveHeader"/>
<spring:message code="gym.cancel" var="gymCancelHeader"/>
<input onclick="window.location='gym/delete-delete.do?q=${gym}';" class="btn btn-primary" name="save" value="${gymSaveHeader}" />
<input onclick="window.location='welcome/index.do';" class="btn btn-warning" type="button" name="cancel" value="${gymCancelHeader}"/>
		


</security:authorize>