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

<div style="width:50%">
	<table class="table table-bordered">
    <thead>
    <tr>
    	<th><spring:message code="administrator.metrica" /></th>
        <th><spring:message code="administrator.valor" /></th>
    </tr>
    </thead>
    <tbody>    
      <tr class="success">
        <td><spring:message code="administrator.a1" /></td>
        <td>${a1}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a2" /></td>
        <td>${a2}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a3" /></td>
        <td>${a3}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a4" /></td>
        <td>${a4}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a5" /></td>
        <td>${a5}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a6" /></td>
        <td>${a6}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a7" /></td>
        <td>${a7}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a8" /></td>
        <td>${a8}</td>
      </tr>
      <tr class="success">
        <td><spring:message code="administrator.a9" /></td>
        <td>${a9}</td>
      </tr>
    </tbody>
  </table>
</div>

