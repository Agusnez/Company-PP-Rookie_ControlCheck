<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />



<display:table name="quolets" id="row" requestURI="${requestURI }" pagesize="5">
	
	
	<acme:column property="ticker" titleKey="quolet.ticker" value= "${row.ticker}: "/>
	
	<acme:dateTimeInternacionaliseTable titleKey="quolet.publicationMoment" value="${row.publicationMoment }"/>
	
	<acme:column property="body" titleKey="quolet.body" value= "${row.body}: "/>
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column titleKey="quolet.finalMode"> 
				<spring:message code="quolet.${row.finalMode }" />
	</display:column>
	
	<acme:url href="quolet/${autoridad}/display.do?quoletId=${row.id }" code="quolet.display"/>
	</security:authorize>
	
	

</display:table>
	
	<security:authorize access="hasRole('COMPANY')">
	<a href="quolet/company/create.do?applicationId=${applicationId }"><spring:message code = "quolet.create"></spring:message></a>
	<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('application/company/display.do?applicationId=${applicationId }');" />
	</security:authorize>
	
	<security:authorize access="hasRole('ROOKIE')">
	<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('application/rookie/display.do?applicationId=${applicationId }');" />
	</security:authorize>
	
	
	
	
	
	
	