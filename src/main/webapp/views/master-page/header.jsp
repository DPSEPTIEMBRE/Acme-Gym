<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-LookSee Co., Inc." />
</div>

<div style="width: 30%">
	<nav class="navbar navbar-default" style="margin-bottom: 0px">
		<div class="container-fluid">
			<div class="navbar-header">
				<ul class="nav navbar-nav">
					<security:authorize access="isAnonymous()">
						<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
						<li><a class="fNiv" href="gym/list.do"><spring:message code="master.page.gym.list" /></a></li>
						<li><a class="fNiv" href="activity/list.do"><spring:message code="master.page.activities.list" /></a></li>
					</security:authorize>
					
					<security:authorize access="isAuthenticated()">
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><security:authentication property="principal.username" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				          </ul>
				        </li>
					</security:authorize>
				</ul>
			</div>
		</div>
	</nav>
	<a href="?language=en"> <img src="images/flag_en.png" /> </a>
	<a href="?language=es"> <img src="images/flag_es.png" /> </a>
</div>
