<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<display:table name="problems" id="row" requestURI="${requestURI }" pagesize="5">

	
	<acme:column property="title" titleKey="problem.title" value= "${row.title}: "/>
	
	<acme:column property="statement" titleKey="problem.statement" value= "${row.statement}: "/>
	
	<acme:column property="hint" titleKey="problem.hint" value= "${row.hint}: "/>
	
	
	
	<display:column titleKey="problem.attachments">
		<c:forEach items="${row.attachments}" var="attachment">
				<a href="${attachment}" target="_blank">${attachment}</a><br/>
		</c:forEach>

	</display:column>
	
	
	<display:column titleKey="problem.finalMode"> 
				<spring:message code="problem.${row.finalMode }" />
	</display:column>
	
	<display:column titleKey="problem.positions">
		<c:forEach items="${row.positions}" var="item">
    		${item.title}
    		<br>
		</c:forEach>
	</display:column>
	
	
	<security:authorize access="hasRole('COMPANY')">
		<acme:url href="problem/company/display.do?problemId=${row.id }" code="problem.display" />
	</security:authorize>

	</display:table>
	
	<security:authorize access="hasRole('COMPANY')">
		<a href="problem/company/create.do"><spring:message code="problem.create"/></a>
	</security:authorize>
		
	<acme:button name="back" code="problem.back" onclick="javascript: relativeRedir('welcome/index.do');" />




