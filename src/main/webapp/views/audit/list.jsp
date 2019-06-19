<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<display:table name="audits" id="row" requestURI="${requestURI }" pagesize="5">

	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="audit.moment"> 
		<fmt:formatDate type="date" value="${row.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<acme:column property="text" titleKey="audit.text" value= "${row.text}: "/>
	
	<acme:column property="score" titleKey="audit.score" value= "${row.score}: "/>
	
	<display:column titleKey="audit.finalMode"> 
				<spring:message code="audit.${row.finalMode }" />
	</display:column>
	
	<acme:column property="position.title" titleKey="audit.position" value= "${row.position.title}: "/>
	
	<security:authorize access="hasRole('AUDITOR')">
		<acme:url href="audit/auditor/display.do?auditId=${row.id }" code="audit.display" />
	</security:authorize>

	</display:table>
	
	<jstl:if test="${requestURI=='audit/auditor/list.do' }">
	<h3><spring:message code="listAuditsCancelled" /></h3>
	
	<display:table name="auditsCancelled" id="row2" requestURI="${requestURI }" pagesize="5">

	
	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="audit.moment"> 
		<fmt:formatDate value="${row2.moment }" pattern="${format}" />
	</display:column>
	
	<acme:column property="text" titleKey="audit.text" value= "${row2.text}: "/>
	
	<acme:column property="score" titleKey="audit.score" value= "${row2.score}: "/>
	
	<display:column titleKey="audit.finalMode"> 
				<spring:message code="audit.${row2.finalMode }" />
	</display:column>
	
	<acme:column property="position.title" titleKey="audit.position" value= "${row2.position.title}: "/>
	
	<security:authorize access="hasRole('AUDITOR')">
		<acme:url href="audit/auditor/display.do?auditId=${row2.id }" code="audit.display" />
	</security:authorize>

	</display:table>
	</jstl:if>
			
	<acme:button name="back" code="audit.back" onclick="javascript: relativeRedir('welcome/index.do');" />




