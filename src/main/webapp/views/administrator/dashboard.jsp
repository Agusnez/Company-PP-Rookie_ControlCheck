
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fieldset>
<legend><spring:message code="dashboard.statscompany" /></legend>
<p><spring:message code="dashboard.statscompany.avg" />: <c:if test="${empty avgPC}"> N/A </c:if><c:if test="${not empty avgPC}">${avgPC}</c:if></p>
<p><spring:message code="dashboard.statscompany.min" />: <c:if test="${empty minPC}"> N/A </c:if><c:if test="${not empty minPC}">${minPC}</c:if></p>
<p><spring:message code="dashboard.statscompany.max" />: <c:if test="${empty maxPC}"> N/A </c:if><c:if test="${not empty maxPC}">${maxPC}</c:if></p>
<p><spring:message code="dashboard.statscompany.std" />: <c:if test="${empty stdPC}"> N/A </c:if><c:if test="${not empty stdPC}">${stdPC}</c:if></p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.statsrookie" /></legend>
<p><spring:message code="dashboard.statsrookie.avg" />: <c:if test="${empty avgAH}"> N/A </c:if><c:if test="${not empty avgAH}">${avgAH}</c:if></p>
<p><spring:message code="dashboard.statsrookie.min" />: <c:if test="${empty minAH}"> N/A </c:if><c:if test="${not empty minAH}">${minAH}</c:if></p>
<p><spring:message code="dashboard.statsrookie.max" />: <c:if test="${empty maxAH}"> N/A </c:if><c:if test="${not empty maxAH}">${maxAH}</c:if></p>
<p><spring:message code="dashboard.statsrookie.std" />: <c:if test="${empty stdAH}"> N/A </c:if><c:if test="${not empty stdAH}">${stdAH}</c:if></p>
<br/>
</fieldset>
<br/>

<p><spring:message code="dashboard.topcompanies" /></p>

<display:table name="topC" id="row">
</display:table>

<br/>
<p><spring:message code="dashboard.toprookies" /></p>
<display:table name="topH" id="row">
</display:table>

<br/>

<fieldset>
<legend><spring:message code="dashboard.statssalaries" /></legend>
<p><spring:message code="dashboard.statssalaries.avg" />: <c:if test="${empty avgS}"> N/A </c:if><c:if test="${not empty avgS}">${avgS}</c:if></p>
<p><spring:message code="dashboard.statssalaries.min" />: <c:if test="${empty minS}"> N/A </c:if><c:if test="${not empty minS}">${minS}</c:if></p>
<p><spring:message code="dashboard.statssalaries.max" />: <c:if test="${empty maxS}"> N/A </c:if><c:if test="${not empty maxS}">${maxS}</c:if></p>
<p><spring:message code="dashboard.statssalaries.std" />: <c:if test="${empty stdS}"> N/A </c:if><c:if test="${not empty stdS}">${stdS}</c:if></p>
<br/>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.positions" /></legend>
<p><spring:message code="dashboard.positions.best" />:<c:if test="${empty bP}">
    N/A
</c:if><c:if test="${not empty bP}">
    ${bP.title}</c:if></p>
<p><spring:message code="dashboard.positions.worst" />: <c:if test="${empty wP}">
    N/A
</c:if><c:if test="${not empty wP}">
    ${wP.title}
</c:if></p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statscv" /></legend>
<p><spring:message code="dashboard.statscv.avg" />: <c:if test="${empty avgCH}"> N/A </c:if><c:if test="${not empty avgCH}">${avgCH}</c:if></p>
<p><spring:message code="dashboard.statscv.min" />: <c:if test="${empty minCH}"> N/A </c:if><c:if test="${not empty minCH}">${minCH}</c:if></p>
<p><spring:message code="dashboard.statscv.max" />: <c:if test="${empty maxCH}"> N/A </c:if><c:if test="${not empty maxCH}">${maxCH}</c:if></p>
<p><spring:message code="dashboard.statscv.std" />: <c:if test="${empty stdCH}"> N/A </c:if><c:if test="${not empty stdCH}">${stdCH}</c:if></p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statsfinder" /></legend>
<p><spring:message code="dashboard.statsfinder.avg" />: <c:if test="${empty avgRF}"> N/A </c:if><c:if test="${not empty avgRF}">${avgRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.min" />: <c:if test="${empty minRF}"> N/A </c:if><c:if test="${not empty minRF}">${minRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.max" />: <c:if test="${empty maxRF}"> N/A </c:if><c:if test="${not empty maxRF}">${maxRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.std" />: <c:if test="${empty stdRF}"> N/A </c:if><c:if test="${not empty stdRF}">${stdRF}</c:if></p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statsfinder" /></legend>
<p><spring:message code="dashboard.statsfinder.avg" />: <c:if test="${empty avgRF}"> N/A </c:if><c:if test="${not empty avgRF}">${avgRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.min" />: <c:if test="${empty minRF}"> N/A </c:if><c:if test="${not empty minRF}">${minRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.max" />: <c:if test="${empty maxRF}"> N/A </c:if><c:if test="${not empty maxRF}">${maxRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.std" />: <c:if test="${empty stdRF}"> N/A </c:if><c:if test="${not empty stdRF}">${stdRF}</c:if></p>
<p><spring:message code="dashboard.statsfinder.ratio" />: <c:if test="${empty ratioEF}"> N/A </c:if><c:if test="${not empty ratioEF}">${ratioEF}</c:if></p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.statspositionscore" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgSP}"> N/A </c:if><c:if test="${not empty avgSP}">${avgSP}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minSP}"> N/A </c:if><c:if test="${not empty minSP}">${minSP}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxSP}"> N/A </c:if><c:if test="${not empty maxSP}">${maxSP}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdSP}"> N/A </c:if><c:if test="${not empty stdSP}">${stdSP}</c:if></p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.scorecompanies" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgSC}"> N/A </c:if><c:if test="${not empty avgSC}">${avgSC}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minSC}"> N/A </c:if><c:if test="${not empty minSC}">${minSC}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxSC}"> N/A </c:if><c:if test="${not empty maxSC}">${maxSC}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdSC}"> N/A </c:if><c:if test="${not empty stdSC}">${stdSC}</c:if></p>
<br/>
</fieldset>
<br/>

<p><spring:message code="dashboard.topauditscorecompanies" /></p>

<display:table name="topSC" id="row">
</display:table>
<br/>

<fieldset>
<legend><spring:message code="dashboard.avgtopsalaries" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgTC}"> N/A </c:if><c:if test="${not empty avgTC}">${avgTC}</c:if></p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.itemprovider" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgSa}"> N/A </c:if><c:if test="${not empty avgSa}">${avgSa}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minSa}"> N/A </c:if><c:if test="${not empty minSa}">${minSa}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxSa}"> N/A </c:if><c:if test="${not empty maxSa}">${maxSa}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdSa}"> N/A </c:if><c:if test="${not empty stdSa}">${stdSa}</c:if></p>
<br/>
</fieldset>
<br/>

<p><spring:message code="dashboard.topproviders" /></p>

<display:table name="topP" id="row">
</display:table>
<br/>

<fieldset>
<legend><spring:message code="dashboard.sponsorprovider" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgSPr}"> N/A </c:if><c:if test="${not empty avgSPr}">${avgSPr}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minSPr}"> N/A </c:if><c:if test="${not empty minSPr}">${minSPr}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxSPr}"> N/A </c:if><c:if test="${not empty maxSPr}">${maxSPr}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdSPr}"> N/A </c:if><c:if test="${not empty stdSPr}">${stdSPr}</c:if></p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.sponsorposition" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgSPo}"> N/A </c:if><c:if test="${not empty avgSPo}">${avgSPo}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minSPo}"> N/A </c:if><c:if test="${not empty minSPo}">${minSPo}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxSPo}"> N/A </c:if><c:if test="${not empty maxSPo}">${maxSPo}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdSPo}"> N/A </c:if><c:if test="${not empty stdSPo}">${stdSPo}</c:if></p>
<br/>
</fieldset>
<br/>

<p><spring:message code="dashboard.superiorproviders" /></p>

<display:table name="supP" id="row">
</display:table>
<br/>



<acme:button name="back" code="back" onclick="javascript: relativeRedir('welcome/index.do');" />

