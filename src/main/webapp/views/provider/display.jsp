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


<acme:display code="provider.providerMake" property="${provider.providerMake }" />

<acme:display code="provider.name" property="${provider.name }" />

<acme:display code="provider.surnames" property="${provider.surnames }" />

<acme:display code="provider.email" property="${provider.email }" />

<acme:display code="provider.phone" property="${provider.phone }" />


<spring:message code="provider.photo"/>: <br> <img src="${provider.photo }"/> <br>


<acme:button name="back" code="provider.back" onclick="javascript: relativeRedir('${backUri }');" />


