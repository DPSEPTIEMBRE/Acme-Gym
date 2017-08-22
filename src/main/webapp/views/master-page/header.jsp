<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="security.LoginService"%>
<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-LookSee Co., Inc." />
</div>

<div style="width: 60%">
	<nav class="navbar navbar-default" style="margin-bottom: 0px">
		<div class="container-fluid">
			<div class="navbar-header">
				<ul class="nav navbar-nav">
					<security:authorize access="isAnonymous()">
					<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
					<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.signup" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="actor/signup.do?q=2"><spring:message code="master.page.manager.signup" /> </a></li>
							<li><a href="actor/signup.do?q=1"><spring:message code="master.page.customer.signup" /> </a></li>
				          </ul>
				        </li>
					</security:authorize>
					<security:authorize access="isAuthenticated()">
					<security:authentication property="principal.id" var="id" />
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.profile" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="actor/edit.do?userAccountID=${id}"><spring:message code="master.page.actor.edit" /></a></li>
							<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				          </ul>
				        </li>
					</security:authorize>
					<security:authorize access="hasRole('MANAGER')">
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.manager.gym" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="manager/gym/list.do?q=${id}&a=2"><spring:message code="master.page.manager.gym.list" /> </a></li>
							<li><a href="manager/gym/create.do"><spring:message code="master.page.manager.gym.create" /> </a></li>
				          </ul>
				        </li>
				        
				        <li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.manager.trainers" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="manager/trainer/list.do?q=${id}"><spring:message code="master.page.manager.trainers.list" /> </a></li>
							<li><a href="actor/signup.do?q=3"><spring:message code="master.page.manager.trainers.create" /> </a></li>
				          </ul>
				        </li>
					</security:authorize>
					<security:authorize access="hasRole('CUSTOMER')">
						<li><a class="fNiv" href="customer/gym/list.do?q=${id}&a=2"><spring:message code="master.page.manager.gym" /></a></li>
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.customer.activities" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a class="fNiv" href="customer/activity/list.do?q=${id}&a=2"><spring:message code="master.page.customer.activities2" /> </a></li>
							<li><a class="fNiv" href="customer/activity/list.do?q=${id}&a=3"><spring:message code="master.page.customer.activities1" /> </a></li>
				          </ul>
				        </li>
					</security:authorize>
					<security:authorize access="hasRole('ADMINISTRATOR')">
					<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.administrator.manager" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          	<li><a class="fNiv" href="administrator/manager/list.do?a=1"><spring:message code="master.page.administrator.manager1" /> </a></li>
							<li><a class="fNiv" href="administrator/manager/list.do?a=3"><spring:message code="master.page.administrator.manager2" /> </a></li>
							<li><a class="fNiv" href="administrator/manager/list.do?a=2"><spring:message code="master.page.administrator.manager3" /> </a></li>
				          </ul>
				    </li>
				    <li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.administrator.tabu" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          	<li><a class="fNiv" href="tabuWord/list.do"><spring:message code="master.page.administrator.tabuList" /> </a></li>
				          	<li><a class="fNiv" href="tabuWord/create.do"><spring:message code="master.page.administrator.tabuCreate" /> </a></li>
				          </ul>
				    </li>
				    <li><a class="fNiv" href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /> </a></li>
					</security:authorize>
					<li><a class="fNiv" href="gym/list.do?a=3&userAccountID=${id}"><spring:message code="master.page.gym.list" /></a></li>
					<li><a class="fNiv" href="activity/list.do?a=1"><spring:message code="master.page.activities.list" /></a></li>
					
				</ul>
			</div>
		</div>
	</nav>
	<a href="?language=en"> <img src="images/flag_en.png" /> </a>
	<a href="?language=es"> <img src="images/flag_es.png" /> </a>
</div>
