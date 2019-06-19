<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" name="socialProfiles" id="row" 
requestURI="${requestURI }" class="displaytag">

	<acme:column property="nick" titleKey="socialProfile.nick" value= "${row.nick}: "/>
	
	<acme:column property="socialName" titleKey="socialProfile.socialName" value= "${row.socialName}: "/>
	
	<display:column titleKey="socialProfile.link">
		<a href="${row.link }" target="_blank">${row.link }</a>
	</display:column>
	
	
	<acme:url href="socialProfile/administrator,company,rookie/edit.do?socialProfileId=${row.id}" code="socialProfile.edit"/>
	
	<acme:url href="socialProfile/administrator,company,rookie/display.do?socialProfileId=${row.id}" code="socialProfile.display"/>
	
</display:table>

	<acme:button name="create" code="socialProfile.create" onclick="javascript: relativeRedir('socialProfile/administrator,company,rookie/create.do');"/>
	
	<acme:button name="back" code="socialProfile.back" onclick="javascript: relativeRedir('profile/displayPrincipal.do');"/>
<br />
