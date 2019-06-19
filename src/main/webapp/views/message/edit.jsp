<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${enlace}" modelAttribute="message">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipientId" />
	<form:hidden path="senderId" />
	
	<acme:textbox code="message.subject" path="subject" obligatory="true"/>

	<acme:textarea code="message.body" path="body" obligatory="true"/>
	
	<acme:textbox code="message.tags" path="tags" />
	
	<p><spring:message code="message.tagSystem" /></p>
	
	<br>
	
	<acme:submit name="save" code="message.save" />
	
	<acme:cancel code="message.cancel" url="actor/list.do" />


</form:form>  