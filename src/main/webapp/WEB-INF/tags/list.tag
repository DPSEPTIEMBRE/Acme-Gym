<!-- 
	author: jjvalle
	version: 1.0
 -->

<%@tag import="org.springframework.context.ApplicationContext"%>
<%@tag import="org.springframework.beans.factory.annotation.Autowired"%>
<%@tag import="org.springframework.context.MessageSource"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.LinkedList"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.Date"%>
<%@tag import="java.util.Collection"%>
<%@tag import="java.util.List"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.DomainEntity"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@tag language="java" body-content="empty" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ attribute name="requestURI" required="true" rtexprvalue="true" %>
<%@ attribute name="list" required="true" rtexprvalue="true" type="java.util.Collection" %>
<%@ attribute name="pagesize" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="hidden_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="entityUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="editUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="columNames" required="false" rtexprvalue="true" type="java.lang.String" %>

<%
	if(pagesize == null) {
		pagesize = 10; }
%>

<h1>${messageSource}</h1>

<%
	if(list.isEmpty()) {
%>
	<display:table  name="${list}" id="row" requestURI="${requestURI}" pagesize="${pagesize}" class="table table-hover"></display:table>
<%
	} else {
%>
	<display:table style="border:none;" name="${list}" id="row" requestURI="${requestURI}" pagesize="${pagesize}" class="table table-hover">
<%
		DomainEntity entity = (DomainEntity) row;
		List<String> hidden = new LinkedList<String>();
		if(hidden_fields != null && !hidden_fields.trim().isEmpty()) {
			hidden.addAll(Arrays.asList(hidden_fields.split(",")));
			for(String s : hidden) {
				s = s.trim(); } }
		
		Map<String, String> map = new HashMap<String, String>();
		if(entityUrl != null && !entityUrl.trim().isEmpty()) {
			StringBuilder repaired = new StringBuilder(entityUrl);
			repaired.deleteCharAt(0);
			repaired.setLength(repaired.length() -1);
			
			for(String u : repaired.toString().split(",")) {
				String[] arr = u.trim().split(":");
				map.put(arr[0], arr[1]);
			}
		}
		
		for(Field e : entity.getClass().getDeclaredFields()) {
			e.setAccessible(true);
			if(hidden.contains(e.getName())) {
				continue;
			}
			
			Map<String, String> colums = new HashMap<String, String>();
			if(columNames != null && !columNames.trim().isEmpty()) {
				StringBuilder repaired = new StringBuilder(columNames);
				repaired.deleteCharAt(0);
				repaired.setLength(repaired.length() -1);
				
				for(String u : repaired.toString().split(",")) {
					String[] arr = u.trim().split(":");
					colums.put(arr[0], arr[1]);
				}
			}
			
			//entity.getClass().getSimpleName().toLowerCase() +  "." + e.getName()
%>
			
			<spring:message code='<%=map.containsKey(e.getName()) ? String.format("%s.%s", row.getClass().getSimpleName().toLowerCase(), e.getName()) : "acme.colum"%>' var="title" />
			<display:column title="${title}" sortable ="false">
<%
				Object obj = e.get(row);
				if(obj instanceof Collection) {
					Collection<?> collection = (Collection<?>) obj;
					if(!collection.isEmpty()) {
%>
<%
				if(collection.iterator().next() instanceof DomainEntity) {
					int ac = 1;
					for(Object d : collection) {
						DomainEntity domain = (DomainEntity) d;
%>
						<a href="<%=map.containsKey(e.getName()) ? map.get(e.getName()) + "?q=" + domain.getId() : "#"%>">
<%
						if(map.containsKey(e.getName())) {
%>
						<spring:message code='<%=row.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' /> (<%=ac++ %>) <br />
<%
						}
%>
						</a>
<%
					}
				} else {
					for(Object d : collection) {
%>
						<%=d %>
<%
				} }
%>
<%
					} } else {
%>
<%
						if(map.containsKey(e.getName()) && obj instanceof DomainEntity) {
%>
							<a href="<%=map.get(e.getName()) + "?q=" + ((DomainEntity) obj).getId()%>">
								<spring:message code='<%=row.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' />
							</a>
<%
						} else {
%>
							<%=obj.toString() %>
<%
						}
%>
<%
				}
%>
			</display:column>
<%
		}
%>
<%
		if(editUrl != null) {
%>
			<display:column sortable ="false">
				<a href="<%=editUrl + "?q=" + entity.getId()%>"><spring:message code="acme.edit"/></a>
			</display:column>
<%
		}
%>
<%
		if(deleteUrl != null) {
%>
			<display:column sortable ="false">
				<a href="<%=deleteUrl + "?q=" + entity.getId()%>"><spring:message code="acme.delete"/></a>
			</display:column>
<%
		}
%>
	</display:table>
<%
	}
%>
