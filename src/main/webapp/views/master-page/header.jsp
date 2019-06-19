<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme Rookies Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="broadcast/administrator/create.do"><spring:message code="master.page.broadcast" /></a></li>	
					<li><a href="actor/administrator/score/list.do"><spring:message code="master.page.score" /></a></li>
					<li><a href="actor/administrator/spammer/list.do"><spring:message code="master.page.spammer" /></a></li>
					<li><a href="actor/administrator/profile/list.do"><spring:message code="master.page.profiles" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.signUpAdmin" /></a></li>
					<li><a href="administrator/createAuditor.do"><spring:message code="master.page.signUpAuditor" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="problem/company/list.do"><spring:message code="master.page.list.problem" /></a></li>		
					<li><a href="position/company/list.do"><spring:message code="master.page.company.position.list" /></a></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.rookie.application" /></a></li>
					<li><a href="application/company/listObsoletes.do"><spring:message code="master.page.rookie.applicationObsoletes" /></a></li>

				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv"><spring:message	code="master.page.rookie" /></a>
				<ul>
					<li class="arrow"></li>

						<li><a href="curriculum/rookie/list.do"><spring:message code="master.page.curriculum" /></a></li>
						<li><a href="finder/rookie/find.do"><spring:message code="master.page.rookie.finder" /></a></li>
						<li><a href="application/rookie/list.do"><spring:message code="master.page.rookie.application" /></a></li>
						<li><a href="application/rookie/listObsoletes.do"><spring:message code="master.page.rookie.applicationObsoletes" /></a></li>
						

				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="position/auditor/listPosition.do"><spring:message code="master.page.auditor.position.list" /></a></li>
					<li><a href="position/auditor/listMyPosition.do"><spring:message code="master.page.auditor.myposition.list" /></a></li>
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.auditList" /></a></li>
				
				</ul>
			</li>
		</security:authorize>
			
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv"><spring:message	code="master.page.provider" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="sponsorship/provider/list.do"><spring:message code="master.page.sponsorship" /></a></li>
					<li><a href="item/provider/listProvider.do"><spring:message code="master.page.itemsProvider" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="register/createCompany.do"><spring:message code="master.page.company.signup" /></a></li>
			<li><a href="register/createRookie.do"><spring:message code="master.page.rookie.signup" /></a></li>
			<li><a href="register/createProvider.do"><spring:message code="master.page.provider.signup" /></a></li>
		</security:authorize>
		
		<security:authorize access="permitAll()">
			<li><a href="company/list.do"><spring:message code="master.page.company.list" /></a></li>
			<li><a href="position/list.do"><spring:message code="master.page.position.list" /></a></li>
			<li><a href="provider/list.do"><spring:message code="master.page.provider.list" /></a></li>
			<li><a href="item/list.do"><spring:message code="master.page.item.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li><a href="profile/displayPrincipal.do"><spring:message code="master.page.profile" /></a></li>
					<li><a href="message/actor/list.do"><spring:message code="master.page.message" /> </a></li>
					<security:authorize access="hasRole('ROOKIE')">
					<li><a href="data/rookie/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
					<li><a href="data/company/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>	
					<security:authorize access="hasRole('PROVIDER')">
					<li><a href="data/provider/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>	
					<security:authorize access="hasRole('AUDITOR')">
					<li><a href="data/auditor/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

