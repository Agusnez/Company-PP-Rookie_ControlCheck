<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />

<jstl:if test="${!AmILogged }">
<form action="position/listByFilter.do" method="get">

<spring:message code="filter.keyword" />: <input type="text" id="keyword" name="keyword" />
<spring:message code="company.commercialName"/>: <input type="text" id="companyName" name="companyName"  />

<acme:submit name="search" code="filter.search"/>

</form>
</jstl:if>

<jstl:if test="${AmInFinder }">
<security:authorize access="hasRole('ROOKIE')">
<form:form action="${requestAction }" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox path="keyWord" code="filter.keyword" />
	
	<acme:textbox path="minimumSalary" code="finder.minimumSalary" />
	
	<acme:textbox path="maximumSalary" code="finder.maximumSalary" />
	
	<acme:textbox path="maximumDeadline" code="finder.maximumDeadline" />
	
	<input type="submit" name="find" value="<spring:message code="filter.search"/>"/>
	
	<input type="submit" name="clear" value="<spring:message code="finder.clear"/>"/>
	
</form:form> 
</security:authorize>
</jstl:if>

<display:table name="positions" id="row" requestURI="${requestURI }" pagesize="${pagesize }" >
	
	
	<acme:column property="ticker" titleKey="position.ticker" value= "${row.ticker}: "/>
	
	<acme:column property="company.commercialName" titleKey="position.company" value= "${row.company.commercialName}: "/>
	
	<acme:column property="title" titleKey="position.title" value= "${row.title} "/>
	
	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="position.deadline"> 
		<fmt:formatDate value="${row.deadline }" pattern="${format}" />
	</display:column>
	
	<acme:column property="profile" titleKey="position.profile" value= "${row.profile}: "/>
	
	<acme:column property="skills" titleKey="position.skills" value= "${row.skills}: "/>
	
	<acme:column property="technologies" titleKey="position.technologies" value= "${row.technologies}: "/>
	
	<acme:column property="offeredSalary" titleKey="position.offeredSalary" value= "${row.offeredSalary}: "/>
	
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${AmInCompanyController }" >
			<display:column titleKey="position.finalMode"> 
						<spring:message code="position.${row.finalMode }" />
			</display:column>
			
			<spring:message code="dateFormat" var="format"/>
			<display:column titleKey="position.dateCancel"> 
				<fmt:formatDate value="${row.cancellation }" pattern="${format}" />
			</display:column>
		</jstl:if>
	</security:authorize>
	
	
	<jstl:if test="${AmInCompanyController }">
	<acme:url href="position/company/display.do?positionId=${row.id }" code="position.display"/>
	</jstl:if>
	<jstl:if test="${!AmInCompanyController }">
	<acme:url href="position/display.do?positionId=${row.id }" code="position.display"/>
	</jstl:if>
	
	
	<acme:url href="audit/listByPosition.do?positionId=${row.id }" code="position.audits" />
	
	<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${requestURI=='position/auditor/listPosition.do'}">
			<acme:url href="position/auditor/select.do?positionId=${row.id }" code="position.select" />
		</jstl:if>
		
		<jstl:if test="${requestURI=='position/auditor/listMyPosition.do'}">
			<acme:url href="audit/auditor/create.do?positionId=${row.id }" code="position.audit" />
		</jstl:if>
	</security:authorize> 
	
	<security:authorize access="hasRole('ROOKIE')">
		<display:column>
			<jstl:if test="${row.deadline > now}">
				<a href="application/rookie/create.do?positionId=${row.id }"> <spring:message code="position.application"/></a>
			</jstl:if>
		</display:column>
	</security:authorize> 
	
	<security:authorize access="hasRole('PROVIDER')">
		<display:column>
			<jstl:if test="${row.deadline > now && row.cancellation==null}">
				<a href="sponsorship/provider/sponsor.do?positionId=${row.id }"> <spring:message code="position.sponsorship"/></a>
			</jstl:if>
		</display:column>
	</security:authorize> 
	
	<jstl:if test="${requestURI == 'position/list.do' || requestURI == 'position/listByFilter.do'}">
		<acme:url href="company/display.do?positionId=${row.id }" code="position.company.display"/>
	</jstl:if>

</display:table>

	<jstl:if test="${AmInCompanyController}">
	<security:authorize access="hasRole('COMPANY')">
	<a href="position/company/create.do"><spring:message code="position.create"/></a>
	</security:authorize>
	</jstl:if>
	
	<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${requestURI == 'position/auditor/listMyPosition.do' }">
	
	<h3><spring:message code="positionCancelled" /></h3>

	<display:table name="myPositionsCancelled" id="pc" requestURI="${requestURI }" pagesize="5">

	<acme:column property="ticker" titleKey="position.ticker" value= "${pc.ticker}: "/>
	
	<acme:column property="company.commercialName" titleKey="position.company" value= "${pc.company.commercialName}: "/>
	
	<acme:column property="title" titleKey="position.title" value= "${pc.title} "/>
	
	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="position.deadline"> 
		<fmt:formatDate value="${pc.deadline }" pattern="${format}" />
	</display:column>
	
	<acme:column property="profile" titleKey="position.profile" value= "${pc.profile}: "/>
	
	<acme:column property="skills" titleKey="position.skills" value= "${pc.skills}: "/>
	
	<acme:column property="technologies" titleKey="position.technologies" value= "${pc.technologies}: "/>
	
	<acme:column property="offeredSalary" titleKey="position.offeredSalary" value= "${pc.offeredSalary}: "/>
	
	<acme:url href="audit/listByPosition.do?positionId=${pc.id }" code="position.audits" />
	
	</display:table>
	</jstl:if>
	</security:authorize>
	
	<acme:button name="back" code="position.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	