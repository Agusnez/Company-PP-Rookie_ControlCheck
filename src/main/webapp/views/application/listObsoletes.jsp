<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="applicationObsoletes" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row.position.ticker}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.submitMoment}" pattern="${timeFormat}" />
	</display:column>
	
	<acme:column property="curriculum.personalData.statement" titleKey="application.curriculum" value= "${row.curriculum.personalData.statement}: "/>
	
	<display:column>
		<security:authorize access="hasRole('ROOKIE')">
			<a href="application/rookie/display.do?applicationId=${row.id}"><spring:message code="application.display"/></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<a href="application/company/display.do?applicationId=${row.id}"><spring:message code="application.display"/></a>
		</security:authorize>
	</display:column>	
	
</display:table>

<br>

	<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('welcome/index.do');" />