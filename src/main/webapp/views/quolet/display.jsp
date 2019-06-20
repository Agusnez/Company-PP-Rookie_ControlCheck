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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<h3><acme:display code="quolet.ticker" property="${quolet.ticker} "/></h3>

<jstl:if test="${quolet.publicationMoment != null }">
<acme:dateTimeInternacionaliseDisplay code="quolet.publicationMoment" value="${application.publicationMoment }" />
</jstl:if>

<acme:display code="quolet.body" property="${quolet.body} "/>
	
<acme:photo value="${quolet.picture }" code="quolet.picture" />

<br>

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${!quolet.finalMode }">
<acme:button name="edit" code="quolet.edit" onclick="javascript: relativeRedir('quolet/company/edit.do?quoletId=${quolet.id}');" />
</jstl:if>
<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('quolet/company/list.do?applicationId=${applicationId }');" />
</security:authorize>

<security:authorize access="hasRole('ROOKIE')">
<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('quolet/rookie/list.do?applicationId=${applicationId }');" />
</security:authorize>


