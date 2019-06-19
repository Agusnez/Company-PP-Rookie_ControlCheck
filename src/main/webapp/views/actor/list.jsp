<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><spring:message code="actor.rookies" /></h3>
	
	<display:table pagesize="5" name="rookies" id="row" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.companies" /></h3>
	
		<display:table pagesize="5" name="companies" id="row2" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row2.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row2.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row2.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.administrators" /></h3>
	
	<display:table pagesize="5" name="administrators" id="row3" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row3.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row3.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row3.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.providers" /></h3>
	
	<display:table pagesize="5" name="providers" id="row4" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row4.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row4.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row4.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.auditors" /></h3>
	
	<display:table pagesize="5" name="auditors" id="row5" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row5.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row5.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row5.id}" code="actor.contact"/>
	
</display:table>
	


	<acme:button name="back" code="actor.back" onclick="javascript: relativeRedir('message/actor/list.do');"/>
