
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="audit/auditor/edit.do" modelAttribute="audit">
	
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="position" />
	
	<acme:textarea path="text" code="audit.text" obligatory="true"/>
	
	<acme:textbox path="score" code="audit.score" obligatory="true" placeholder="0.0"/>
	
  	<acme:choose path="finalMode" code="audit.finalMode" value1="true" value2="false" label1="Final" label2="No Final" />
	
	<acme:submit name="save" code="audit.save" />	

	<acme:cancel code="audit.cancel" url="audit/auditor/list.do" />
	
	<jstl:if test="${audit.id != 0}">
		<acme:delete code="audit.delete" />
	</jstl:if>	
	

</form:form>    