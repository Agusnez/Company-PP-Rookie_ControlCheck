<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="quolet/company/edit.do" modelAttribute="quolet">
	
	<form:hidden path="id" />
	<form:hidden path="application" />
	<form:hidden path="version" />
	
	
	<acme:textbox code="quolet.body" path="body" obligatory="true"/>

	<acme:textbox code="quolet.picture" path="picture" obligatory="true" size="100"/>
	
	<acme:choose path="finalMode" code="problem.finalMode" value1="false" value2="true" label1="No Final" label2="Final"/>
	
	<br>
	
	<acme:submit name="save" code="quolet.save" />
	
	<acme:cancel code="quolet.cancel" url="quolet/company/list.do?applicationId=${applicationId}" />


</form:form>  