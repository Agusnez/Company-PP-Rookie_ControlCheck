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

<fieldset>

	<jstl:if test="${empty company.score}">
		<spring:message code="company.score" />: <spring:message code="company.status.na" />
	</jstl:if>
	
	<jstl:if test="${not empty company.score}">
		<spring:message code="company.score" />: <jstl:out value="${company.score}"/>
	</jstl:if>

</fieldset>
		
<br/>	

<acme:display code="company.commercialName" property="${company.commercialName }" />

<acme:display code="company.name" property="${company.name }" />

<acme:display code="company.surnames" property="${company.surnames }" />

<acme:display code="company.email" property="${company.email }" />

<acme:display code="company.phone" property="${company.phone }" />

<spring:message code="company.photo"/>: <br> <img src="${company.photo }"/> <br>


<acme:button name="back" code="company.back" onclick="javascript: relativeRedir('${backUri }');" />


