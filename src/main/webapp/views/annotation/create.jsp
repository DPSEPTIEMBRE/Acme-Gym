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


<form:form action="${url}" modelAttribute="annotation">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="actorWrites" />
		<form:hidden path="actorStores" />
		<form:hidden path="gym" />
		<form:hidden path="activity" />

	    <div class="form-group" style="width: 20%;"> 
	    
	    <label> <spring:message code="annotation.momentWritten"/> </label><br />
		<input class="form-control" value="${annotation.momentWritten}" type="text" name="momentWritten"/>
		<form:errors cssClass="error" path="momentWritten" />
		<div class="alert alert-info">
		<spring:message code="annotation.momentWritten.format"/>
		</div><br />
		
		<label> <spring:message code="annotation.text"/> </label><br />
		<textarea class="form-control" name="text">${annotation.text}</textarea>
		<form:errors cssClass="error" path="text" /> <br />
	    
		<label> <spring:message code="annotation.rate"/> </label><br />
		<input class="form-control" value="${annotation.rate}" type="number" min="0" max="3" name="rate"/>
		<form:errors cssClass="error" path="rate" /> <br />
		</div>
		
		<spring:message code="annotation.save" var="actorSaveHeader"/>
		<spring:message code="annotation.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.location='welcome/index.do';" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>



