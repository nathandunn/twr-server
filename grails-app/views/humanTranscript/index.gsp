
<%@ page import="edu.uoregon.secondlook.HumanTranscript" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'humanTranscript.label', default: 'HumanTranscript')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-humanTranscript" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-humanTranscript" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="processDate" title="${message(code: 'humanTranscript.processDate.label', default: 'Process Date')}" />
                        <g:sortableColumn property="audioFile" title="Audio File" />

						<g:sortableColumn property="transcript" title="${message(code: 'humanTranscript.transcript.label', default: 'Transcript')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'humanTranscript.status.label', default: 'Status')}" />
					
						<g:sortableColumn property="twr" title="${message(code: 'humanTranscript.twr.label', default: 'Twr')}" />
					
						%{--<g:sortableColumn property="transcriptErrors" title="${message(code: 'humanTranscript.transcriptErrors.label', default: 'Transcript Errors')}" />--}%
					%{----}%
						%{--<g:sortableColumn property="passageErrors" title="${message(code: 'humanTranscript.passageErrors.label', default: 'Passage Errors')}" />--}%
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${humanTranscriptInstanceList}" status="i" var="humanTranscriptInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${humanTranscriptInstance.id}">
                            <g:formatDate date="${humanTranscriptInstance.processDate}" type="date"/>
						</g:link></td>

                        <td>
                            <g:link action="show" controller="audioFile" id="${humanTranscriptInstance.audioFile.id}">
                                ${humanTranscriptInstance.audioFile.fileName}
                            </g:link>
                        </td>
					
						<td>
                            <g:if test="${humanTranscriptInstance?.processedTranscript?.size()>30}">
                                ${humanTranscriptInstance.processedTranscript.substring(0,30)}...
                            </g:if>
                            <g:else>
                                ${humanTranscriptInstance.processedTranscript}
                            </g:else>
                        </td>
					
						<td>${fieldValue(bean: humanTranscriptInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: humanTranscriptInstance, field: "twr")}</td>
					
						%{--<td>${fieldValue(bean: humanTranscriptInstance, field: "transcriptErrors")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: humanTranscriptInstance, field: "passageErrors")}</td>--}%
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${humanTranscriptInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
