<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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


<%-- <security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize> --%>

<jstl:if test="${admin}">
	<div>
		<fieldset>
			
			<jstl:if test="${empty actor.spammer}">
				<spring:message code="actor.spammer" />: <spring:message code="actor.status.na" />
			</jstl:if>
			
			<jstl:if test="${not empty actor.spammer and !row.spammer}">
				<spring:message code="actor.spammer" />: <spring:message code="actor.status.notSpammer" />
			</jstl:if>
			
			<jstl:if test="${not empty actor.spammer and actor.spammer}">
				<spring:message code="actor.spammer" />: <spring:message code="actor.status.spammer" />
			</jstl:if>
			
			<br/>
			
			 <jstl:if test="${isCompany}">
				<jstl:if test="${empty actor.score}">
					<spring:message code="actor.score" />: <spring:message code="actor.status.na" />
				</jstl:if>
				
				<jstl:if test="${not empty actor.score}">
					<spring:message code="actor.score" />: <jstl:out value="${actor.score}"/>
				</jstl:if>
				
			</jstl:if>	
				
				
			
		</fieldset>
		
		<br/>
		
		
		<jstl:if test="${!(actor eq principal)}">
			<a href="actor/administrator/profile/deleteProfile.do?actorId=${actor.id}"><spring:message code="actor.deleteProfile"/></a>
			<br/>
		</jstl:if>
	</div>
</jstl:if>

<acme:display code="actor.name" property="${actor.name }" />

<acme:display code="actor.surname" property="${actor.surnames }" />

<security:authorize access="hasRole('COMPANY')">
<acme:display code="company.commercialName" property="${actor.commercialName }" />
</security:authorize> 


<spring:message code="actor.photo"/>: <br> <img src="${actor.photo }" width="10%" height="10%"/> <br>

<acme:display code="actor.email" property="${actor.email }" />

<acme:display code="actor.phone" property="${actor.phone }" />

<acme:display code="actor.address" property="${actor.address }" />

<acme:display code="actor.vat" property="${actor.vat }" />

<h2><spring:message code="creditCard.data" /></h2>

<acme:display code="actor.creditCard.holderName" property="${actor.creditCard.holderName }" />

<acme:display code="actor.creditCard.make" property="${actor.creditCard.make }" />

<acme:display code="actor.creditCard.number" property="${actor.creditCard.number }" />
	
<acme:display code="actor.creditCard.expMonth" property="${actor.creditCard.expMonth }" />
	
<acme:display code="actor.creditCard.expYear" property="${actor.creditCard.expYear }" />
	
<acme:display code="actor.creditCard.cvv" property="${actor.creditCard.cvv }" />
	


<jstl:if test="${!admin}">
	<acme:button name="socialProfile" code="actor.socialProfile" onclick="javascript: relativeRedir('socialProfile/administrator,company,rookie/list.do');" />


	<security:authorize access="isAuthenticated()">

		<acme:button name="edit" code="actor.edit" onclick="javascript: relativeRedir('profile/edit.do');" />

	</security:authorize>
</jstl:if>
<jstl:if test="${!admin}">
	<acme:button name="back" code="actor.back" onclick="javascript: relativeRedir('welcome/index.do');" />
</jstl:if>
<jstl:if test="${admin}">
	<acme:button name="back" code="actor.back" onclick="javascript: relativeRedir('actor/administrator/profile/list.do');" />
</jstl:if>


	

