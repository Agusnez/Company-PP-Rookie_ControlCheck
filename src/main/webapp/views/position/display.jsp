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
<jsp:useBean id="now" class="java.util.Date" />


<h2><acme:display code="position.ticker" property="${position.ticker }" /></h2>

<acme:display code="position.title" property="${position.title }" />

<acme:display code="position.description" property="${position.description}" />

<div><spring:message code="position.deadline" />:
<spring:message code="dateFormat" var="format"/>
<fmt:formatDate value="${position.deadline}" pattern="${format}"/>
</div>

<acme:display code="position.profile" property="${position.profile}" />

<acme:display code="position.skills" property="${position.skills}" />

<acme:display code="position.technologies" property="${position.technologies}" />

<acme:display code="position.offeredSalary" property="${position.offeredSalary}" />

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${!position.finalMode && security && position.cancellation==null}">
	<acme:button name="edit" code="position.edit" onclick="javascript: relativeRedir('position/company/edit.do?positionId=${position.id }');" />
</jstl:if>
<jstl:if test="${position.finalMode && security && position.cancellation==null && position.deadline > now}">
	<acme:button name="edit" code="position.cancellation" onclick="javascript: relativeRedir('position/company/cancel.do?positionId=${position.id }');" />
</jstl:if>
</security:authorize>

<jstl:if test="${AmInCompanyController }">
<acme:button name="back" code="position.back" onclick="javascript: relativeRedir('position/company/list.do');" />
</jstl:if>
<jstl:if test="${!AmInCompanyController }">
<acme:button name="back" code="position.back" onclick="javascript: relativeRedir('position/list.do');" />
</jstl:if>

<jstl:if test="${find}">
	<div>
		<a target="_blank" href="${targetSponsorship}"><img src="${bannerSponsorship }" alt="Banner" width="10%" height="10%" /></a>
	</div>
</jstl:if>



