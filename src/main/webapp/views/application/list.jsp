<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><spring:message code="applicationAccepted" /></h3>

<display:table name="applicationAccepted" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row.position.ticker}: "/>
	
	<acme:column property="problem.title" titleKey="application.problem.title" value= "${row.problem.title}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.submitMoment }" pattern="${timeFormat}" />
	</display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:url href="curriculum/rookie/display.do?curriculumId=${row.curriculum.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/rookie/display.do?applicationId=${row.id}"><spring:message code="application.display"/></a>
		</display:column>
			
	</security:authorize>	
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:url href="curriculum/company/display.do?curriculumId=${row.curriculum.id}&applicationId=${row.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/company/display.do?applicationId=${row.id}"><spring:message code="application.display"/></a>
		</display:column>
		
	</security:authorize>
	
</display:table>

<br>

<h3><spring:message code="applicationRejected" /></h3>

<display:table name="applicationRejected" id="row2" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row2.position.ticker}: "/>
	
	<acme:column property="problem.title" titleKey="application.problem.title" value= "${row2.problem.title}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row2.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row2.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row2.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row2.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row2.submitMoment }" pattern="${timeFormat}" />
	</display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:url href="curriculum/rookie/display.do?curriculumId=${row2.curriculum.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/rookie/display.do?applicationId=${row2.id}"><spring:message code="application.display"/></a>
		</display:column>	
		
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:url href="curriculum/company/display.do?curriculumId=${row2.curriculum.id}&applicationId=${row2.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/company/display.do?applicationId=${row2.id}"><spring:message code="application.display"/></a>
		</display:column>
		
	</security:authorize>
	
</display:table>

<br>

<h3><spring:message code="applicationSubmitted" /></h3>

<display:table name="applicationSubmitted" id="row3" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row3.position.ticker}: "/>
	
	<acme:column property="problem.title" titleKey="application.problem.title" value= "${row3.problem.title}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row3.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row3.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row3.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row3.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row3.submitMoment }" pattern="${timeFormat}" />
	</display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:url href="curriculum/rookie/display.do?curriculumId=${row3.curriculum.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/rookie/display.do?applicationId=${row3.id}"><spring:message code="application.display"/></a>
		</display:column>
		
		<display:column>
				<a href="application/rookie/edit.do?applicationId=${row3.id}"><spring:message code="application.edit"/></a>
		</display:column>
			
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:url href="curriculum/company/display.do?curriculumId=${row3.curriculum.id}&applicationId=${row3.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/company/display.do?applicationId=${row3.id}"><spring:message code="application.display"/></a>
		</display:column>
		
		<display:column>
				<a href="application/company/accept.do?applicationId=${row3.id}"><spring:message code="application.accept"/></a>
		</display:column>
		
		<display:column>
				<a href="application/company/reject.do?applicationId=${row3.id}"><spring:message code="application.reject"/></a>
		</display:column>
		
	</security:authorize>
	
</display:table>

<br>

<h3><spring:message code="applicationPending" /></h3>

<display:table name="applicationPending" id="row4" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row4.position.ticker}: "/>
	
	<acme:column property="problem.title" titleKey="application.problem.title" value= "${row4.problem.title}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row4.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row4.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row4.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row4.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row4.submitMoment }" pattern="${timeFormat}" />
	</display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:url href="curriculum/rookie/display.do?curriculumId=${row4.curriculum.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/rookie/display.do?applicationId=${row4.id}"><spring:message code="application.display"/></a>
		</display:column>
			
		<display:column>
				<a href="application/rookie/edit.do?applicationId=${row4.id}"><spring:message code="application.edit"/></a>
		</display:column>
		
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:url href="curriculum/company/display.do?curriculumId=${row4.curriculum.id}&applicationId=${row4.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/company/display.do?applicationId=${row4.id}"><spring:message code="application.display"/></a>
		</display:column>
		
	</security:authorize>
	
</display:table>

<br>

<h3><spring:message code="applicationPositionCancelled" /></h3>

<display:table name="applicationCancelled" id="row5" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row5.position.ticker}: "/>
	
	<acme:column property="problem.title" titleKey="application.problem.title" value= "${row5.problem.title}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row5.answer}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="timeFormat"/>
	<display:column titleKey="application.moment"> 
		<fmt:formatDate type="date" value="${row5.moment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row5.moment }" pattern="${timeFormat}" />
	</display:column>
	
	<display:column titleKey="application.submitMoment"> 
		<fmt:formatDate type="date" value="${row5.submitMoment }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row5.submitMoment }" pattern="${timeFormat}" />
	</display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:url href="curriculum/rookie/display.do?curriculumId=${row5.curriculum.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/rookie/display.do?applicationId=${row5.id}"><spring:message code="application.display"/></a>
		</display:column>
		
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:url href="curriculum/company/display.do?curriculumId=${row5.curriculum.id}&applicationId=${row5.id}" code = "application.curriculum" />
	
		<display:column>
				<a href="application/company/display.do?applicationId=${row5.id}"><spring:message code="application.display"/></a>
		</display:column>
		
	</security:authorize>
	
</display:table>

<br>


	<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('welcome/index.do');" />