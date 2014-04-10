
<%@ page import="edu.uoregon.secondlook.ComputerTranscript" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'computerTranscript.label', default: 'ComputerTranscript')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-computerTranscript" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-computerTranscript" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list computerTranscript">
			
				<g:if test="${computerTranscriptInstance?.returnDate}">
				<li class="fieldcontain">
					<span id="returnDate-label" class="property-label"><g:message code="computerTranscript.returnDate.label" default="Return Date" /></span>
					
						<span class="property-value" aria-labelledby="returnDate-label"><g:formatDate date="${computerTranscriptInstance?.returnDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.transcript}">
				<li class="fieldcontain">
					<span id="transcript-label" class="property-label"><g:message code="computerTranscript.transcript.label" default="Transcript" /></span>
					
						<span class="property-value" aria-labelledby="transcript-label"><g:fieldValue bean="${computerTranscriptInstance}" field="transcript"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="computerTranscript.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${computerTranscriptInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.twr}">
				<li class="fieldcontain">
					<span id="twr-label" class="property-label"><g:message code="computerTranscript.twr.label" default="Twr" /></span>
					
						<span class="property-value" aria-labelledby="twr-label"><g:fieldValue bean="${computerTranscriptInstance}" field="twr"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.transcriptErrors}">
				<li class="fieldcontain">
					<span id="transcriptErrors-label" class="property-label"><g:message code="computerTranscript.transcriptErrors.label" default="Transcript Errors" /></span>
					
						<span class="property-value" aria-labelledby="transcriptErrors-label"><g:fieldValue bean="${computerTranscriptInstance}" field="transcriptErrors"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.passageErrors}">
				<li class="fieldcontain">
					<span id="passageErrors-label" class="property-label"><g:message code="computerTranscript.passageErrors.label" default="Passage Errors" /></span>
					
						<span class="property-value" aria-labelledby="passageErrors-label"><g:fieldValue bean="${computerTranscriptInstance}" field="passageErrors"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.note}">
				<li class="fieldcontain">
					<span id="note-label" class="property-label"><g:message code="computerTranscript.note.label" default="Note" /></span>
					
						<span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${computerTranscriptInstance}" field="note"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.transcriptionEngine}">
				<li class="fieldcontain">
					<span id="transcriptionEngine-label" class="property-label"><g:message code="computerTranscript.transcriptionEngine.label" default="Transcription Engine" /></span>
					
						<span class="property-value" aria-labelledby="transcriptionEngine-label"><g:link controller="transcriptionEngine" action="show" id="${computerTranscriptInstance?.transcriptionEngine?.id}">${computerTranscriptInstance?.transcriptionEngine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.audioFile}">
				<li class="fieldcontain">
					<span id="audioFile-label" class="property-label"><g:message code="computerTranscript.audioFile.label" default="Audio File" /></span>
					
						<span class="property-value" aria-labelledby="audioFile-label"><g:link controller="audioFile" action="show" id="${computerTranscriptInstance?.audioFile?.id}">${computerTranscriptInstance?.audioFile?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${computerTranscriptInstance?.requestDate}">
				<li class="fieldcontain">
					<span id="requestDate-label" class="property-label"><g:message code="computerTranscript.requestDate.label" default="Request Date" /></span>
					
						<span class="property-value" aria-labelledby="requestDate-label"><g:formatDate date="${computerTranscriptInstance?.requestDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:computerTranscriptInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${computerTranscriptInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
