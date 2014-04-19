
<%@ page import="edu.uoregon.secondlook.ResearcherUser" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researcherUser.label', default: 'ResearcherUser')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-researcherUser" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-researcherUser" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="username" title="${message(code: 'researcherUser.username.label', default: 'Username')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'researcherUser.name.label', default: 'Name')}" />
					
						%{--<g:sortableColumn property="passwordHash" title="${message(code: 'researcherUser.passwordHash.label', default: 'Password Hash')}" />--}%
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${researcherUserInstanceList}" status="i" var="researcherUserInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${researcherUserInstance.id}">${fieldValue(bean: researcherUserInstance, field: "username")}</g:link></td>
					
						<td>${fieldValue(bean: researcherUserInstance, field: "name")}</td>
					
						%{--<td>${fieldValue(bean: researcherUserInstance, field: "passwordHash")}</td>--}%
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${researcherUserInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
