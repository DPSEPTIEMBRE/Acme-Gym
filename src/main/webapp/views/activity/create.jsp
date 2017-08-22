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

<form:form action="${url}" modelAttribute="activity">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isCancelled" />
		<form:hidden path="trainers" />
		<form:hidden path="gym" />
		<form:hidden path="annotations" />
		<form:hidden path="customers" />

	    <div class="form-group" style="width: 20%;"> 
	    
	    <label> <spring:message code="activity.title"/> </label><br />
		<input class="form-control" value="${activity.title}" type="text" name="title"/>
		<form:errors cssClass="error" path="title" /> <br />
		
		<label> <spring:message code="activity.pictures"/> </label><br />
		<input class="form-control" value="${activity.pictures}" type="text" name="pictures"/>
		<form:errors cssClass="error" path="pictures" /> 
		<div class="alert alert-info" >
		<spring:message code="activity.pictures.format"/>
		</div><br />
	    
		<label> <spring:message code="activity.description"/> </label>
		<br />
		<textarea class="form-control" name="description" > ${activity.description} </textarea>
		<form:errors cssClass="error" path="description" /> <br />
		
		<label> <spring:message code="activity.dayWeek"/> </label><br />
		<input class="form-control" value="${activity.dayWeek}" type="number" min="1" max="7" name="dayWeek"/>
		<form:errors cssClass="error" path="dayWeek" /> <br />
		
		<label> <spring:message code="activity.startTime"/> </label><br />
		<input class="form-control" value="${activity.startTime}" type="text" name="startTime"/>
		<form:errors cssClass="error" path="startTime" /> 
		<div class="alert alert-info">
		<spring:message code="activity.hour.format"/>
		</div><br />
		
		<label> <spring:message code="activity.endTime"/> </label><br />
		<input class="form-control" value="${activity.endTime}" type="text" name="endTime"/>
		<form:errors cssClass="error" path="endTime" /> 
		<div class="alert alert-info">
		<spring:message code="activity.hour.format"/>
		</div><br />
		
		<label> <spring:message code="activity.numSeats"/> </label><br />
		<input class="form-control" value="${activity.numSeats}" type="number" name="numSeats"/>
		<form:errors cssClass="error" path="numSeats" /> <br />
	
		</div>
		
		<spring:message code="activity.save" var="actorSaveHeader"/>
		<spring:message code="activity.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.location='welcome/index.do';" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>

</security:authorize>


