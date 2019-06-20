<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />



<display:table name="quolets" id="row" requestURI="${requestURI }" pagesize="5">
	
	<spring:message code="quolet.publicationMoment" var="publicationMomentHeader"/>
	<display:column title="${publicationMomentHeader}" >
		<jstl:if test="${language == 'es'}">
			<fmt:formatDate value="${row.publicationMoment}"  pattern="dd-MM-yy HH:mm"  />
		</jstl:if>
		<jstl:if test="${language == 'en'}">
			<fmt:formatDate value="${row.publicationMoment}" pattern="yy/MM/dd HH:mm" />
		</jstl:if>
	</display:column>
	
	<acme:column property="ticker" titleKey="quolet.ticker" value= "${row.ticker}: "/>
	
	<acme:column property="body" titleKey="quolet.body" value= "${row.body}: "/>
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column titleKey="quolet.finalMode"> 
				<spring:message code="quolet.${row.finalMode }" />
	</display:column>
	
	<acme:url href="quolet/${autoridad}/display.do?quoletId=${row.id }" code="quolet.display"/>
	</security:authorize>
	
	

</display:table>
	
	<security:authorize access="hasRole('COMPANY')">
	<a href="quolet/company/create.do?applicationId=${applicationId }"><spring:message code = "quolet.create"></spring:message></a>
	<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('application/company/display.do?applicationId=${applicationId }');" />
	</security:authorize>
	
	<security:authorize access="hasRole('ROOKIE')">
	<acme:button name="back" code="quolet.back" onclick="javascript: relativeRedir('application/rookie/display.do?applicationId=${applicationId }');" />
	</security:authorize>
	
	
<script type="text/javascript">
String.prototype.toDate = function(format)
{
  var normalized      = this.replace(/[^a-zA-Z0-9]/g, '-');
  var normalizedFormat= format.toLowerCase().replace(/[^a-zA-Z0-9]/g, '-');
  var formatItems     = normalizedFormat.split('-');
  var dateItems       = normalized.split('-');
	
  
  var monthIndex  = formatItems.indexOf("mm");
  var dayIndex    = formatItems.indexOf("dd");
  var yearIndex   = formatItems.indexOf("yy");
  var hourIndex     = formatItems.indexOf("hh");
  var minutesIndex  = formatItems.indexOf("ii");
  
  var today = new Date();
	
 
  var year1  = yearIndex>-1  ? dateItems[yearIndex]    : today.getFullYear();
  var year = 20+year1;
  var month = monthIndex>-1 ? dateItems[monthIndex]-1 : today.getMonth()-1;
  var day   = dayIndex>-1   ? dateItems[dayIndex]     : today.getDate();

  var hour    = hourIndex>-1      ? dateItems[hourIndex]    : today.getHours();
  var minute  = minutesIndex>-1   ? dateItems[minutesIndex] : today.getMinutes();
 
  return new Date(year,month,day,hour,minute);
};


	//TODO CAMBIO CONTROL: cambiar colores en hexadecimal(Mirar en w3school)
	var trTags = document.getElementsByTagName("tr");
	var actual = new Date();
	var mes1 = new Date();
	mes1.setMonth(actual.getMonth()-1);
	console.log(mes1);
	var mes2 = new Date();
	mes2.setMonth(actual.getMonth()-2);
	console.log(mes2);
	
	for (var i = 1; i < trTags.length; i++) {
	  var tdDate = trTags[i].children[0].innerText;
	  if(${language == 'es'}){
		  var fecha = tdDate.toDate("dd/mm/yy hh:ii");
	  }else {
		  var fecha = tdDate.toDate("yy/mm/dd hh:ii");
	  }
	  
	  console.log(fecha);
	  if (fecha>mes1) {
		  trTags[i].style.backgroundColor = "#4B0082";
	  } else if(fecha>mes2 && fecha<=mes1){
		  trTags[i].style.backgroundColor = "#2F4F4F";
	  } else{
		  trTags[i].style.backgroundColor = "#FFEFD5";
	  }
	}
</script>	
	
	
	
	