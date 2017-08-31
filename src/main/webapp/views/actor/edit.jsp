<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="isAuthenticated()">

	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:set var="url" value="actor/save-customer.do" />
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:set var="url" value="actor/save-manager.do" />
	</security:authorize>

	<security:authorize access="hasRole('TRAINER')">
		<jstl:set var="url" value="actor/save-trainer.do" />
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:set var="url" value="actor/save-administrator.do" />
	</security:authorize>


<form:form action="${url}" modelAttribute="actor">

		<security:authorize access="hasRole('CUSTOMER')">
			<form:hidden path="activities" />
			<form:hidden path="gyms" />
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<form:hidden path="gyms" />
		</security:authorize>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount.username" />
		<form:hidden path="userAccount.password" />
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="annotationWriter" />
		<form:hidden path="annotationStore" />

	    <div class="form-group" style="width: 20%;"> 
	    
		<label> <spring:message code="actor.actorName"/> </label>
		<br />
		<input class="form-control" value="${actor.actorName}" type="text" name="actorName"/>
		<form:errors cssClass="error" path="actorName" /> <br />
		
		<label> <spring:message code="actor.surname"/> </label><br />
		<input class="form-control" value="${actor.surname}" type="text" name="surname"/>
		<form:errors cssClass="error" path="surname" /> <br />
		
		<label> <spring:message code="actor.email"/> </label><br />
		<input class="form-control" value="${actor.email}" type="text" name="email"/>
		<form:errors cssClass="error" path="email" /> <br />
		
		<label> <spring:message code="actor.phone"/> </label><br />
		<input class="form-control" value="${actor.phone}" type="text" name="phone"/>
		<form:errors cssClass="error" path="phone" /> <br />
		
		<label> <spring:message code="actor.postalAddress"/> </label><br />
		<input class="form-control" value="${actor.postalAddress}" type="text" name="postalAddress"/>
		<form:errors cssClass="error" path="postalAddress" /> <br />
		
		
		<label> <spring:message code="actor.city"/> </label><br />
		<input class="form-control" value="${actor.city}" type="text" name="city"/>
		<form:errors cssClass="error" path="city" /> <br />
		
		
		<label> <spring:message code="actor.country"/> </label><br />
		<input class="form-control" value="${actor.country}" type="text" name="country"/>
		<form:errors cssClass="error" path="country" /> <br />
		
		</div>
		
		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>

</security:authorize>





