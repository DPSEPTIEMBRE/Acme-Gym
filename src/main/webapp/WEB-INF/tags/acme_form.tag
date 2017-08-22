<!-- 
	author: jjvalle
	version: 1.0
 -->

<%@tag import="java.lang.reflect.Modifier"%>
<%@tag import="java.util.LinkedList"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.Date"%>
<%@tag import="java.util.Collection"%>
<%@tag import="java.util.List"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.DomainEntity"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@tag language="java" body-content="scriptless" %>

<%@ attribute name="url" required="true" rtexprvalue="true" %>
<%@ attribute name="type" required="false" rtexprvalue="true" %>
<%@ attribute name="cancel" required="false" rtexprvalue="true" %>
<%@ attribute name="entity" required="true" rtexprvalue="true" type="domain.DomainEntity" %>
<%@ attribute name="numberMin" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="numberMax" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="numberSteps" required="false" rtexprvalue="true" type="java.lang.Double" %>
<%@ attribute name="areaFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="hiddenFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="another_mapped_classes" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="skip_fields" required="false" rtexprvalue="true" type="java.lang.String" %>

<form:form action="${url}" modelAttribute="<%=entity.getClass().getSimpleName().toLowerCase() %>" method="POST" >
<%
	List<String> areas = new ArrayList<String>();
	if(areaFields != null) {
		areas.addAll(Arrays.asList(areaFields.split(","))); }
	
	Class<? extends DomainEntity> clazz = entity.getClass();
	if(type == null || type.trim().isEmpty()) {
		type = "create"; }
	
	boolean show = type.equalsIgnoreCase("edit");
	
	List<Class<? extends DomainEntity>> another = new LinkedList<Class<? extends DomainEntity>>();
	List<Field> fields = new LinkedList<Field>(Arrays.asList(clazz.getDeclaredFields()));
	
	if(another_mapped_classes != null && !another_mapped_classes.trim().isEmpty()) {
		for(String e : another_mapped_classes.split(",")) {
			fields.addAll(Arrays.asList(((Class<? extends DomainEntity>) Class.forName(e.trim())).getDeclaredFields()));
		}
	}
	
	List<String> skiped = new LinkedList<String>();
	
	if(skip_fields != null && !skip_fields.trim().isEmpty()) {
		skiped.addAll(Arrays.asList(skip_fields.split(",")));
	}
%>

<%
	for(Field e : fields) {
		if(skiped.contains(e.getName())) {
			continue; }
		
		if(Modifier.isStatic(e.getModifiers())) {
			continue;
		}
		
		if(hiddenFields != null && hiddenFields.contains(e.getName())) {
			
%>
		<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>

<%
		if(DomainEntity.class.isAssignableFrom(e.getType())) {
%>
			<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>

<%
		if(Collection.class.isAssignableFrom(e.getType())) {
%>
			<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>
		<div class="form-group" style="width: 55%;">
<%
			if(!Boolean.class.isAssignableFrom(e.getType())) {
%>
			<label for="email"><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' /></>
<%
			}
%>
<%
			if(e.getType().equals(String.class)) {
				if(areas.contains(e.getName())) {
%>
					<textarea name="<%=e.getName()%>" style="resize: none;" class="form-control" rows="5" id="<%=e.getName()%>"><%=show ? e.get(entity).toString() : "" %></textarea>
<%
				} else {
%>
					<input <%=show ? String.format("value='%s'", e.get(entity).toString()) : "" %> name="<%=e.getName()%>" type="text" class="form-control" id="<%=e.getName()%>">
<%
			} } else if(Number.class.isAssignableFrom(e.getType())) {
%>
				<input <%=show ? String.format("value='%s'", e.get(entity).toString()) : "" %> name="<%=e.getName()%>" type="number" <%=numberMin != null ? "min='" + numberMin + "'" : ""%> <%=numberMax != null ? "max='" + numberMax + "'" : ""%> <%=numberSteps != null ? "step='" + numberSteps + "'" : ""%> class="form-control" id="<%=e.getName()%>">
<%
			} else if(Date.class.isAssignableFrom(e.getType())) {
%>
				<input <%=show ? String.format("value='%s'", e.get(entity).toString()) : "" %> name="<%=e.getName()%>" type="date" class="form-control" id="<%=e.getName()%>">
<%
			} else if(Boolean.class.isAssignableFrom(e.getType())) {
%>
				
				<div class="checkbox">
				  <input type="checkbox" value=""><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' />
				</div>
<%
			}
%>
		</div>
<%
	}
%>

	<%getJspBody().invoke(out); %>

	<input name="save" type="submit" class="btn btn-primary" value="<spring:message code="acme.save"/>">
	<input onclick="location.href = '${cancel}';" type="button" class="btn btn-warning" value="<spring:message code="acme.cancel" />">
	<button type="reset" class="btn btn-danger" value="Reset"><spring:message code="acme.clear"/></button>

</form:form>