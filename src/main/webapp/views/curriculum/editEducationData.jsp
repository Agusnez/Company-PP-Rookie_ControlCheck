<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="educationData/rookie/edit.do" modelAttribute="educationData">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculumId" />
	
	<acme:textbox code="curriculum.degree" path="degree" obligatory="true"/>
	
	<acme:textbox code="curriculum.institution" path="institution" obligatory="true"/>
	
	<acme:textbox code="curriculum.mark" path="mark" obligatory="true"/>
	
	<acme:textbox code="curriculum.startDate" path="startDate" placeholder="yyyy/mm/dd" obligatory="true"/>
	
	<acme:textbox code="curriculum.endDate" path="endDate" placeholder="yyyy/mm/dd" />
	
	<acme:submit name="save" code="curriculum.save" />
	
	<jstl:if test="${educationData.id != 0 }">
		<acme:submit name="delete" code="curriculum.delete" />
	</jstl:if>
	
	<acme:cancel code="curriculum.back" url="curriculum/rookie/display.do?curriculumId=${educationData.curriculumId }" />
	
</form:form> 