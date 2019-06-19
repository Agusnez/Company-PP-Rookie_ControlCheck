<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Search Parade --%>

<display:table name="providers" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	
	<acme:column property="providerMake" titleKey="provider.providerMake" value= "${row.providerMake} "/>
	
	<acme:column property="name" titleKey="provider.name" value= "${row.name}:"/>
	
	<acme:column property="surnames" titleKey="provider.surnames" value= "${row.surnames}:"/>
	
	<acme:column property="email" titleKey="provider.email" value= "${row.email} "/>
	
	<acme:column property="phone" titleKey="provider.phone" value="${row.phone }" />
	
	<display:column titleKey="provider.photo">
		<a href="${row.photo }" target="_blank">${row.photo }</a>
	</display:column>
	
	
	<acme:url href="item/listByProvider.do?providerId=${row.id }" code="provider.items"/>


</display:table>
	
<acme:button name="back" code="provider.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	