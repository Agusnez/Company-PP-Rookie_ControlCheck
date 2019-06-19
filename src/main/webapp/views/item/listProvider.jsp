<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />



<display:table name="items" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	<acme:column property="name" titleKey="item.name" value= "${row.name}: "/>
	
	<display:column titleKey="item.link">
		<a href="${row.link }" target="_blank">${row.link }</a>
	</display:column>


	<security:authorize access="hasRole('PROVIDER')">
	
		<display:column>
				<a href="item/provider/display.do?itemId=${row.id}"><spring:message code = "item.display"/></a>
		</display:column>
			
		<display:column>	
		<a href="item/provider/edit.do?itemId=${row.id}"><spring:message code = "item.edit"/></a>		
		</display:column>	
	</security:authorize>	
	
	<jstl:if test="${requestURI == 'item/list.do'}">
		<acme:url href="provider/display.do?itemId=${row.id }" code="item.provider.display"/>
	</jstl:if>

</display:table>

	<security:authorize access="hasRole('PROVIDER')">

	<acme:button name="create" code="item.create" onclick="javascript: relativeRedir('item/provider/create.do');"/>
	
	</security:authorize>
	
	<acme:button name="back" code="item.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	