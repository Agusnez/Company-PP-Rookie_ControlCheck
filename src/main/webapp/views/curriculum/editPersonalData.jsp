<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="personalData/rookie/edit.do" modelAttribute="personalData">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculumId" />
	
	<acme:textbox path="statement" code="curriculum.statement" obligatory="true"/>
	
	<acme:textbox path="phone" code="curriculum.phone" id="phone" onblur="javascript: checkPhone();" obligatory="true"/>
	
	<acme:textbox path="linkGitHubProfile" code="curriculum.linkGitHubProfile" obligatory="true"/>
	
	<acme:textbox path="linkLinkedInProfile" code="curriculum.linkLinkedInProfile" obligatory="true"/>
  	
	<acme:submit name="save" code="curriculum.save" />	

	<acme:cancel code="curriculum.back" url="curriculum/rookie/display.do?curriculumId=${personalData.curriculumId }" />

</form:form>

<script type="text/javascript">
	function checkPhone() {
		var target = document.getElementById("phone");
		var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4}$)");

		if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == false) {
			if (confirm('<spring:message code="curriculum.phone.wrong" />') == false) {
				return true;
			
			}
		} else if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == true) {
			target.value = '${defaultCountry}' + " " + input;
		}
	}
</script>