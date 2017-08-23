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

<label><spring:message code="trainer.text" /> </label>
<input type="text" id="trainerSearch" /> 
<button type="button" class="btn btn-primary" >  <spring:message code="trainer.search" /></button>

<security:authorize access="hasRole('MANAGER')"> 

<jstl:if test="${a==1}">

<acme:list list="${trainers}" requestURI="trainer/list.do" hidden_fields="userAccount,annotationWriter,id,version" 
extraColumns="{addToGym: manager/trainer/addToGym.do, newAnnotation: annotation/create.do}" 
entityUrl="{annotationStore: annotation/listByTrainer.do}"/>

</jstl:if>

<jstl:if test="${a==2}">

<acme:list list="${trainers}" requestURI="trainer/list.do" hidden_fields="userAccount,annotationWriter,id,version" 
extraColumns="{addToGym: activity/addToActivity.do, newAnnotation: annotation/create.do}" 
entityUrl="{annotationStore: annotation/listByTrainer.do}"/>

</jstl:if>

<jstl:if test="${a!=1 && a!=2}">

<acme:list list="${trainers}" requestURI="trainer/list.do" hidden_fields="userAccount,annotationWriter,id,version"
entityUrl="{annotationStore: annotation/listByTrainer.do}" extraColumns="{newAnnotation: annotation/create.do}"/>

</jstl:if>

</security:authorize>

<security:authorize access="permitAll() and !hasRole('MANAGER')"> 

<acme:list list="${trainers}" requestURI="trainer/list.do" hidden_fields="userAccount,annotationWriter,id,version"
entityUrl="{annotationStore: annotation/listByTrainer.do}" />


</security:authorize>

<script>
$(document).ready(function(){
    $("button").click(function(){
        $.ajax({success: function(result){
        	var input, filter, table, tr, tdName,tdSurname,i;
        	input = document.getElementById("trainerSearch");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("row");
        	tr = table.getElementsByTagName("tr");
        	for (i = 0; i < tr.length; i++) {
        		tdName = tr[i].getElementsByTagName("td")[0];
        		tdSurname = tr[i].getElementsByTagName("td")[1];
        		if (tdName || tdSurname) {
        			if ((tdName.innerHTML.toUpperCase().indexOf(filter) > -1 || 
        				tdSurname.innerHTML.toUpperCase().indexOf(filter) > -1)){
	          	        tr[i].style.display = "";
	          	      } else {
	          	        tr[i].style.display = "none";
	          	      }
        			
        			}
          	      
        	}
        }});
    });
});
</script>