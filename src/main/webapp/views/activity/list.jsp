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


<label><spring:message code="activity.text" /> </label>
<input type="text" id="textSearch" /> 
<label><spring:message code="activity.textDay" /> </label>
<input type="text" id="textSearchDay" /> 
<label><spring:message code="activity.textHour" /> </label>
<input type="text" id="textSearchHour" /> 
<button type="button" class="btn btn-primary" >  <spring:message code="activity.search" /></button>


<security:authorize access="permitAll() and !hasRole('MANAGER') and !hasRole('CUSTOMER') and !hasRole('ADMINISTRATOR') and !hasRole('TRAINER')">

<jstl:if test="${a==1}">

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}"  
extraColumns="{avgStar: activity/avgStar.do}"/>

</jstl:if>

</security:authorize>

<security:authorize access="hasRole('MANAGER')"> 

<jstl:if test="${a==2}">

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}" 
extraColumns="{cancel: activity/cancel.do, addTrainer: activity/addTrainer.do, newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}" />

</jstl:if>

<jstl:if test="${a==1}">

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}"
extraColumns="{newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}" />

</jstl:if>

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')"> 

<jstl:if test="${a==2}">

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="annotations,customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}" 
extraColumns="{leave: customer/activity/leave.do, newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}"/>

</jstl:if>

<jstl:if test="${a==3}">

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="annotations,customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}"
extraColumns="{join: customer/activity/join.do, newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}" />

</jstl:if>

<jstl:if test="${a==1}"> 

<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="annotations,customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}" 
extraColumns="{newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}"/>

</jstl:if>

</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">


<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}" 
extraColumns="{newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}"/>


</security:authorize>


<security:authorize access="hasRole('TRAINER')">


<acme:list list="${activities}" requestURI="activity/list.do" hidden_fields="customers,id,version,isCancelled" 
entityUrl="{trainers: trainer/listByActivity.do, gym: gym/listByActivity.do, annotations: annotation/listByActivity.do}"  
extraColumns="{newAnnotation: annotation/create.do,
avgStar: activity/avgStar.do}"/>


</security:authorize>

<script>
$(document).ready(function(){
    $("button").click(function(){
        $.ajax({success: function(result){
        	var input, filter, table, tr, tdTitle,tdDescription,tdDay,tdHourIni,tdHourFin,i;
        	input = document.getElementById("textSearch");
        	inputDay = document.getElementById("textSearchDay");
        	inputHour = document.getElementById("textSearchHour");
        	filter1 = input.value.toUpperCase();
        	filter2 = inputDay.value.toUpperCase();
        	filter3 = inputHour.value.toUpperCase();
        	table = document.getElementById("row");
        	tr = table.getElementsByTagName("tr");
        	for (i = 0; i < tr.length; i++) {
        		tdTitle = tr[i].getElementsByTagName("td")[0];
        		tdDescription = tr[i].getElementsByTagName("td")[2];
        		tdDay = tr[i].getElementsByTagName("td")[3];
        		tdHourIni = tr[i].getElementsByTagName("td")[4];
        		tdHourFin = tr[i].getElementsByTagName("td")[5];
        		if (tdTitle || tdDescription || tdDay || tdHourIni || tdHourFin) {
        			if ((tdTitle.innerHTML.toUpperCase().indexOf(filter1) > -1 || 
        				tdDescription.innerHTML.toUpperCase().indexOf(filter1) > -1)
        				&& tdDay.innerHTML.toUpperCase().indexOf(filter2) > -1
        				&& (tdHourIni.innerHTML.toUpperCase().indexOf(filter3) > -1
        				|| tdHourFin.innerHTML.toUpperCase().indexOf(filter3) > -1)) {
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
