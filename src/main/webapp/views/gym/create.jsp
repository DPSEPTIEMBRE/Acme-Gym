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


<form:form action="${url}" modelAttribute="gym">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="trainers" />
		<form:hidden path="customers" />
		<form:hidden path="activities" />
		<form:hidden path="annotations" />
		<form:hidden path="isDelete" />

	    <div class="form-group" style="width: 20%;"> 
	    
	    <label> <spring:message code="gym.logo"/> </label><br />
		<input class="form-control" value="${gym.logo}" type="text" name="logo"/>
		<form:errors cssClass="error" path="logo" /> <br />
		
		<label> <spring:message code="gym.name"/> </label><br />
		<input class="form-control" value="${gym.name}" type="text" name="name"/>
		<form:errors cssClass="error" path="name" /> <br />
	    
		<label> <spring:message code="gym.address"/> </label>
		<br />
		<input class="form-control" value="${gym.address}" type="text" name="address"/>
		<form:errors cssClass="error" path="address" /> <br />
		
		<label> <spring:message code="gym.fee"/> </label><br />
		<input class="form-control" value="${gym.fee}" type="number" step="0.25" name="fee"/>
		<form:errors cssClass="error" path="fee" /> <br />
		
		</div>
		
		<spring:message code="gym.save" var="gymSaveHeader"/>
		<spring:message code="gym.cancel" var="gymCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${gymSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${gymCancelHeader}"/>
		
		
	</form:form>

</security:authorize>