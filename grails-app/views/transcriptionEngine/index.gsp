
<%@ page import="edu.uoregon.secondlook.TranscriptionEngine" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transcriptionEngine.label', default: 'TranscriptionEngine')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-transcriptionEngine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-transcriptionEngine" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						%{--<g:sortableColumn property="note" title="${message(code: 'transcriptionEngine.note.label', default: 'Note')}" />--}%
					%{----}%
						%{--<g:sortableColumn property="lookup" title="${message(code: 'transcriptionEngine.lookup.label', default: 'Lookup')}" />--}%
					%{----}%
						<g:sortableColumn property="name" title="${message(code: 'transcriptionEngine.name.label', default: 'Name')}" />

                        %{--<th>Average TWR (Human vs Computer)</th>--}%
                        <th># Computer Transcripts</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${transcriptionEngineInstanceList}" status="i" var="transcriptionEngineInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${transcriptionEngineInstance.id}">${transcriptionEngineInstance.name}</g:link></td>

                        <td>
                            ${edu.uoregon.secondlook.ComputerTranscript.countByTranscriptionEngine(transcriptionEngineInstance)}
                        </td>



					%{----}%
						%{--<td>${fieldValue(bean: transcriptionEngineInstance, field: "lookup")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: transcriptionEngineInstance, field: "name")}</td>--}%
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${transcriptionEngineInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
