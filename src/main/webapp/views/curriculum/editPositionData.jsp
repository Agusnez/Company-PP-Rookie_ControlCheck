<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="positionData/rookie/edit.do" modelAttribute="positionData">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculumId" />
	
	<acme:textbox code="curriculum.title" path="title" obligatory="true"/>
	
	<acme:textbox code="curriculum.description" path="description" obligatory="true"/>
	
	<acme:textbox code="curriculum.startDate" path="startDate" placeholder="yyyy/mm/dd" obligatory="true"/>
	
	<acme:textbox code="curriculum.endDate" path="endDate" placeholder="yyyy/mm/dd"/>
	
	<acme:submit name="save" code="curriculum.save" />
	
	<jstl:if test="${positionData.id != 0 }">
		<acme:submit name="delete" code="curriculum.delete" />
	</jstl:if>
	
	<acme:cancel code="curriculum.back" url="curriculum/rookie/display.do?curriculumId=${positionData.curriculumId }" />
	
</form:form> 