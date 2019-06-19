<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Search Parade --%>

<display:table name="companies" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	
	<acme:column property="commercialName" titleKey="company.commercialName" value= "${row.commercialName} "/>
	
	<acme:column property="name" titleKey="company.name" value= "${row.name}:"/>
	
	<acme:column property="surnames" titleKey="company.surnames" value= "${row.surnames}:"/>
	
	<acme:column property="email" titleKey="company.email" value= "${row.email} "/>
	
	<acme:column property="phone" titleKey="company.phone" value="${row.phone }" />
	
	<display:column titleKey="company.photo">
		<a href="${row.photo }" target="_blank">${row.photo }</a>
	</display:column>
	
	<acme:url href="position/listByCompany.do?companyId=${row.id }" code="company.positions"/>


</display:table>
	
<acme:button name="back" code="company.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	