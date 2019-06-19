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

<acme:display code="item.name" property="${item.name} "/>
	
<acme:display code="item.description" property="${item.description} "/>
	
<div><spring:message code="item.link" />:
<a href="${item.link}" target="_blank">${item.link }</a>
</div>
	
<spring:message code="item.pictures" />: <br>
<c:forEach items="${item.pictures}" var="item">
    		<fieldset>
				<img src="${item}" alt="pictures" width="10%" height="10%"/>
			</fieldset>
    		<br>
</c:forEach>



<acme:button name="delete" code="item.delete" onclick="javascript: relativeRedir('item/provider/delete.do?itemId=${item.id}');" />

<acme:button name="back" code="item.back" onclick="javascript: relativeRedir('item/provider/listProvider.do');" />