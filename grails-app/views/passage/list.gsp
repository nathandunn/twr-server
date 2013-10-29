
<%@ page import="edu.uoregon.secondlook.Passage" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'passage.label', default: 'Passage')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-passage" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-passage" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="externalId" title="${message(code: 'passage.externalId.label', default: 'External Id')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'passage.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="text" title="${message(code: 'passage.text.label', default: 'Text')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${passageInstanceList}" status="i" var="passageInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${passageInstance.id}">${fieldValue(bean: passageInstance, field: "externalId")}</g:link></td>
					
						<td>${fieldValue(bean: passageInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: passageInstance, field: "text")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${passageInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
