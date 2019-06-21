<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<acme:display code="application.problem.title" property="${application.problem.title} "/>

<acme:display code="application.problem.statement" property="${application.problem.statement} "/>

<acme:display code="application.problem.hint" property="${application.problem.hint} "/>

<spring:message code="application.problem.attachments" />:
		<c:forEach items="${application.problem.attachments}" var="attachment">
				<a href="${attachment}" target="_blank">${attachment}</a><br/>
		</c:forEach>

<acme:dateTimeInternacionaliseDisplay code="application.moment" value="${application.moment }" dateFormat="dateFormat" timeFormat="timeFormat"/>

<acme:display code="application.status" property="${application.status} "/>
	
<acme:display code="application.answer" property="${application.answer} "/>

<acme:dateTimeInternacionaliseDisplay code="application.submitMoment" value="${application.submitMoment }" dateFormat="dateFormat" timeFormat="timeFormat"/>
	
<acme:display code="application.position.ticker" property="${application.position.ticker} "/>
	
<acme:display code="application.curriculum" property="${application.curriculum.personalData.statement} "/>

<security:authorize access="hasRole('COMPANY')">
<acme:button name="quolets" code="application.quolets" onclick="javascript: relativeRedir('quolet/company/list.do?applicationId=${application.id}');" />
<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('application/company/list.do');" />
</security:authorize>

<security:authorize access="hasRole('ROOKIE')">
<acme:button name="quolets" code="application.quolets" onclick="javascript: relativeRedir('quolet/rookie/list.do?applicationId=${application.id}');" />
<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('application/rookie/list.do');" />
</security:authorize>



