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

<security:authorize access="hasRole('AUDITOR')">

<div><spring:message code="audit.moment" />:
<spring:message code="dateFormat" var="format"/>
<spring:message code="timeFormat" var="timeFormat"/>
<fmt:formatDate type="date" value="${audit.moment }" pattern="${format}" />
<fmt:formatDate type="time" value="${audit.moment }" pattern="${timeFormat}" />
</div>

<acme:display code="audit.text" property="${audit.text }" />

<acme:display code="audit.score" property="${audit.score}" />

<acme:display code="audit.finalMode" property="${audit.finalMode}" />

<acme:display code="audit.position" property="${audit.position.title}" />

<jstl:if test="${!audit.finalMode && audit.position.cancellation == null}">
	<acme:button name="edit" code="audit.edit" onclick="javascript: relativeRedir('audit/auditor/edit.do?auditId=${audit.id }');" />
</jstl:if>

<acme:button name="back" code="audit.back" onclick="javascript: relativeRedir('welcome/index.do');" />

</security:authorize>

