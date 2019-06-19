
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="position/company/edit.do" modelAttribute="position">
	
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:textbox path="title" code="position.title" obligatory="true"/>
	
	<acme:textbox path="description" code="position.description" obligatory="true"/>
	
	<acme:textbox path="deadline" code="position.deadline" obligatory="true" placeholder="yyyy/MM/dd"/>
	
	<acme:textbox path="profile" code="position.profile" obligatory="true"/>
	
	<acme:textbox path="skills" code="position.skills" obligatory="true"/>
	
	<acme:textbox path="technologies" code="position.technologies" obligatory="true"/>
	
	<acme:textbox path="offeredSalary" code="position.offeredSalary" obligatory="true"/>
	
	<acme:choose path="finalMode" code="problem.finalMode" value1="false" value2="true" label1="No Final" label2="Final"/>
	
	<h4><spring:message code="position.finalMode.explanation"/></h4>
	
	
	
	<acme:submit name="save" code="position.save" />	

	<acme:cancel code="position.cancel" url="position/company/list.do" /><br>
	
	<jstl:if test="${position.id != 0}">
		<h4><spring:message code="position.delete.explanation"/></h4>
		<acme:delete code="position.delete" />
		
	</jstl:if>	
	

</form:form>   
