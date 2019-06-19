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

<security:authorize access="hasRole('PROVIDER')">

<div>
	<a target="_blank" href="${sponsorship.target}"><img src="${sponsorship.banner }" alt="Banner"  width="10%" height="10%" /></a>
</div>

<acme:display code="sponsorship.cost" property="${sponsorship.cost }" />

<acme:display code="sponsorship.sponsor" property="${sponsorship.provider.name}" />

<acme:display code="sponsorship.creditCard.holderName" property="${sponsorship.creditCard.holderName}" />

<acme:display code="sponsorship.creditCard.make" property="${sponsorship.creditCard.make}" />

<acme:display code="sponsorship.creditCard.number" property="${sponsorship.creditCard.number}" />

<acme:display code="sponsorship.creditCard.expMonth" property="${sponsorship.creditCard.expMonth}" />

<acme:display code="sponsorship.creditCard.expYear" property="${sponsorship.creditCard.expYear}" />

<acme:button name="back" code="sponsorship.back" onclick="javascript: relativeRedir('sponsorship/provider/list.do');" />

</security:authorize>

