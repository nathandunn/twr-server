
<%@ page import="edu.uoregon.secondlook.AudioFile" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'audioFile.label', default: 'AudioFile')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-audioFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="list" action="audioNeedingTranscription">Audio File Needing Transcription</g:link></li>
			</ul>
		</div>
		<div id="list-audioFile" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						%{--<g:sortableColumn property="audioData" title="${message(code: 'audioFile.audioData.label', default: 'Audio Data')}" />--}%
                        <g:sortableColumn property="fileName" title="${message(code: 'audioFile.fileName.label', default: 'File Name')}" />

						%{--<g:sortableColumn property="externalStudentId" title="${message(code: 'audioFile.externalStudentId.label', default: 'External Student Id')}" />--}%
					
						%{--<g:sortableColumn property="callbackUrl" title="${message(code: 'audioFile.callbackUrl.label', default: 'Callback Url')}" />--}%
					
						%{--<g:sortableColumn property="note" title="${message(code: 'audioFile.note.label', default: 'Note')}" />--}%
					

						<th><g:message code="audioFile.passage.label" default="Passage" /></th>
                        <th>Transcripts (H/C)</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${audioFileInstanceList}" status="i" var="audioFileInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${audioFileInstance.id}">${audioFileInstance.fileName}</g:link></td>
					
						%{--<td>${fieldValue(bean: audioFileInstance, field: "externalStudentId")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: audioFileInstance, field: "callbackUrl")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: audioFileInstance, field: "note")}</td>--}%
					

						<td>
                        <g:link action="show" controller="passage" id="${audioFileInstance.passageId}">${audioFileInstance.passage.externalId} - ${audioFileInstance.passage.name}</g:link>
						</td>
                        <td>
                            ${audioFileInstance?.humanTranscripts.size()} /
                            ${audioFileInstance?.computerTranscripts.size()}
                        </td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${audioFileInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
