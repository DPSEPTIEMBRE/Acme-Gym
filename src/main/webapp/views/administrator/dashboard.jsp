<%--
 * dashboard.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<!-- El mínimo, máximo, media y desviación estándar del número de gimnasios por mánager -->
	<b><spring:message code="administrator.minGymsManager" /> </b>
	<jstl:out value="${avgGymsManager[0]}" />
	<br />
	<b><spring:message code="administrator.maxGymsManager" /> </b>
	<jstl:out value="${avgGymsManager[1]}" />
	<br />
	<b><spring:message code="administrator.avgGymsManager" /> </b>
	<jstl:out value="${avgGymsManager[2]}" />
	<br />
	<b><spring:message code="administrator.desvGymsManager" /> </b>
	<jstl:out value="${avgGymsManager[3]}" />
	<br />

	<!-- El mínimo, máximo, media y desviación estándar del número de gimnasios por cliente. -->
	<b><spring:message code="administrator.minGymsCustomer" /> </b>
	<jstl:out value="${avgGymsCustomer[0]}" />
	<br />
	<b><spring:message code="administrator.maxGymsCustomer" /> </b>
	<jstl:out value="${avgGymsCustomer[1]}" />
	<br />
	<b><spring:message code="administrator.avgGymsCustomer" /> </b>
	<jstl:out value="${avgGymsCustomer[2]}" />
	<br />
	<b><spring:message code="administrator.desvGymsCustomer" /> </b>
	<jstl:out value="${avgGymsCustomer[3]}" />
	<br />

	<!-- El mínimo, máximo, media y desviación estándar del número de clientes por gimnasio. -->
	<b><spring:message code="administrator.minCustomersGym" /> </b>
	<jstl:out value="${avgCustomersGym[0]}" />
	<br />
	<b><spring:message code="administrator.maxCustomersGym" /> </b>
	<jstl:out value="${avgCustomersGym[1]}" />
	<br />
	<b><spring:message code="administrator.avgCustomersGym" /> </b>
	<jstl:out value="${avgCustomersGym[2]}" />
	<br />
	<b><spring:message code="administrator.desvCustomersGym" /> </b>
	<jstl:out value="${avgCustomersGym[3]}" />
	<br />

	<!-- El gimnasio que ofrece más actividades. La cuenta debe ignorar actividades canceladas. -->
	<b><spring:message code="administrator.gymMoreActivities" /></b>
	<jstl:forEach var="gyms" items="${gymMoreActivities}" varStatus="loop">
		<jstl:out value="${gyms}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<!-- Los clientes que se han apuntado a más actividades. -->
	<b><spring:message code="administrator.gymMoreActivities" /></b>
	<jstl:forEach var="customers" items="${customerMoreActivities}"
		varStatus="loop">
		<jstl:out value="${customers}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<!-- La media y desviación estándar del número de notas por entidad apropiada. -->
	<b><spring:message
			code="administrator.avgAnnotationAdministratorWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationAdministratorWritten[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationAdministratorWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationAdministratorWritten[1]}" />
	<br />
	<b><spring:message
			code="administrator.avgAnnotationAdministratorStored" /> </b>
	<jstl:out value="${avgDesvAnnotationAdministratorStored[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationAdministratorStored" /> </b>
	<jstl:out value="${avgDesvAnnotationAdministratorStored[1]}" />
	<br />

	<b><spring:message code="administrator.avgAnnotationManagerWritten" />
	</b>
	<jstl:out value="${avgDesvAnnotationManagerWritten[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationManagerWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationManagerWritten[1]}" />
	<br />
	<b><spring:message code="administrator.avgAnnotationManagerStored" />
	</b>
	<jstl:out value="${avgDesvAnnotationManagerStored[0]}" />
	<br />
	<b><spring:message code="administrator.desvAnnotationManagerStored" />
	</b>
	<jstl:out value="${avgDesvAnnotationManagerStored[1]}" />
	<br />

	<b><spring:message
			code="administrator.avgAnnotationCustomerWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationCustomerWritten[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationCustomerWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationCustomerWritten[1]}" />
	<br />
	<b><spring:message code="administrator.avgAnnotationCustomerStored" />
	</b>
	<jstl:out value="${avgDesvAnnotationCustomerStored[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationCustomerStored" /> </b>
	<jstl:out value="${avgDesvAnnotationCustomerStored[1]}" />
	<br />

	<b><spring:message code="administrator.avgAnnotationTrainerWritten" />
	</b>
	<jstl:out value="${avgDesvAnnotationTrainerWritten[0]}" />
	<br />
	<b><spring:message
			code="administrator.desvAnnotationTrainerWritten" /> </b>
	<jstl:out value="${avgDesvAnnotationTrainerWritten[1]}" />
	<br />
	<b><spring:message code="administrator.avgAnnotationTrainerStored" />
	</b>
	<jstl:out value="${avgDesvAnnotationTrainerStored[0]}" />
	<br />
	<b><spring:message code="administrator.desvAnnotationTrainerStored" />
	</b>
	<jstl:out value="${avgDesvAnnotationTrainerStored[1]}" />
	<br />

	<!-- La media y desviación estándar del número de estrellas por entidad apropiada. -->
	<b><spring:message code="administrator.avgStarsAdministrator" /> </b>
	<jstl:out value="${avgDesvStarsAdministrator[0]}" />
	<br />
	<b><spring:message code="administrator.desvStarsAdministrator" />
	</b>
	<jstl:out value="${avgDesvStarsAdministrator[1]}" />
	<br />

	<b><spring:message code="administrator.avgStarsManager" /> </b>
	<jstl:out value="${avgDesvStarsManager[0]}" />
	<br />
	<b><spring:message code="administrator.desvStarsManager" /> </b>
	<jstl:out value="${avgDesvStarsManager[1]}" />
	<br />

	<b><spring:message code="administrator.avgStarsCustomer" /> </b>
	<jstl:out value="${avgDesvStarsCustomer[0]}" />
	<br />
	<b><spring:message code="administrator.desvStarsCustomer" /> </b>
	<jstl:out value="${avgDesvStarsCustomer[1]}" />
	<br />

	<b><spring:message code="administrator.avgStarsTrainer" /> </b>
	<jstl:out value="${avgDesvStarsTrainer[0]}" />
	<br />
	<b><spring:message code="administrator.desvStarsTrainer" /> </b>
	<jstl:out value="${avgDesvStarsTrainer[1]}" />
	<br />

	<!-- La media de estrellas por actor, agrupadas por país. -->
	<b><spring:message
			code="administrator.avgStarsAdministratorCountry" /> </b>
	<jstl:out value="${avgStarsAdministratorCountry}" />
	<br />
	<b><spring:message code="administrator.avgStarsManagerCountry" />
	</b>
	<jstl:out value="${avgStarsManagerCountry}" />
	<br />
	<b><spring:message code="administrator.avgStarsCustomerCountry" />
	</b>
	<jstl:out value="${avgStarsCustomerCountry}" />
	<br />
	<b><spring:message code="administrator.avgStarsTrainerCountry" />
	</b>
	<jstl:out value="${avgStarsTrainerCountry}" />
	<br />

	<!-- La media de estrellas por actor, agrupadas por ciudad. -->
	<b><spring:message code="administrator.avgStarsAdministratorCity" />
	</b>
	<jstl:out value="${avgStarsAdministratorCity}" />
	<br />
	<b><spring:message code="administrator.avgStarsManagerCity" /> </b>
	<jstl:out value="${avgStarsManagerCity}" />
	<br />
	<b><spring:message code="administrator.avgStarsCustomerCity" /> </b>
	<jstl:out value="${avgStarsCustomerCity}" />
	<br />
	<b><spring:message code="administrator.avgStarsTrainerCity" /> </b>
	<jstl:out value="${avgStarsTrainerCity}" />
	<br />

</security:authorize>
