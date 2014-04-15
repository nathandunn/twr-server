
<%@ page import="edu.uoregon.secondlook.ProcessingQueue" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'processingQueue.label', default: 'ProcessingQueue')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-processingQueue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-processingQueue" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="entryDate" title="${message(code: 'processingQueue.entryDate.label', default: 'Entry Date')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'processingQueue.status.label', default: 'Status')}" />
					
						<th><g:message code="processingQueue.transcription.label" default="Computer Transcript" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${processingQueueInstanceList}" status="i" var="processingQueueInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${processingQueueInstance.id}">${fieldValue(bean: processingQueueInstance, field: "entryDate")}</g:link></td>
					
						<td>${fieldValue(bean: processingQueueInstance, field: "status")}</td>
					
						%{--<td>${fieldValue(bean: processingQueueInstance, field: "transcription")}</td>--}%
                        <td>
                        %{--${fieldValue(bean: processingQueueInstance, field: "transcription")}--}%
                        <g:link action="show" controller="computerTranscript" id="${processingQueueInstance?.computerTranscript?.id}">
                            ${processingQueueInstance?.computerTranscript?.audioFile?.fileName}
                        </g:link>
                    </td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${processingQueueInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
