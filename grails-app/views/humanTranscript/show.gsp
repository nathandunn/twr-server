
<%@ page import="edu.uoregon.secondlook.HumanTranscript" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'humanTranscript.label', default: 'HumanTranscript')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-humanTranscript" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-humanTranscript" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list humanTranscript">

                <li class="fieldcontain">
                    <span id="audioFile-label" class="property-label"><g:message code="humanTranscript.audioFile.label" default="Audio File" /></span>

                    <span class="property-value" aria-labelledby="audioFile-label"><g:link controller="audioFile" action="show" id="${humanTranscriptInstance?.audioFile?.id}">${humanTranscriptInstance?.audioFile?.fileName}</g:link></span>

                </li>

                <li class="fieldcontain">
                    <span id="originalFile-label" class="property-label"><g:message code="audioFile.originalFile.label"
                                                                                 default="Auxillary File"/></span>

                    <span class="property-value" aria-labelledby="originalFile-label">
                        <g:if test="${humanTranscriptInstance?.originalFile}">
                            <g:formatNumber number="${humanTranscriptInstance.originalFile.length / 1E6}" type="number"/> MB
                            <g:link action="downloadBinary" id="${humanTranscriptInstance.id}">Download</g:link>
                        </g:if>
                        <g:else>
                            ------
                        </g:else>
                    </span>

                </li>

				<li class="fieldcontain">
					<span id="processDate-label" class="property-label"><g:message code="humanTranscript.processDate.label" default="Process Date" /></span>
					
						<span class="property-value" aria-labelledby="processDate-label"><g:formatDate date="${humanTranscriptInstance?.processDate}" /></span>
					
				</li>



				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="humanTranscript.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${humanTranscriptInstance}" field="status"/></span>
					
				</li>

				<g:if test="${humanTranscriptInstance?.twr}">
				<li class="fieldcontain">
					<span id="twr-label" class="property-label"><g:message code="humanTranscript.twr.label" default="Twr" /></span>
					
						<span class="property-value" aria-labelledby="twr-label"><g:fieldValue bean="${humanTranscriptInstance}" field="twr"/></span>
					
				</li>
				</g:if>


				<li class="fieldcontain">
					<span id="researcher-label" class="property-label"><g:message code="humanTranscript.researcher.label" default="Researcher" /></span>
					
						<span class="property-value" aria-labelledby="researcher-label"><g:fieldValue bean="${humanTranscriptInstance}" field="researcher"/></span>
					
				</li>

                <li class="fieldcontain">
                    <span id="transcript-label" class="property-label"><g:message code="humanTranscript.transcript.label" default="Transcript" /></span>

                    <span class="property-value" aria-labelledby="transcript-label"><g:fieldValue bean="${humanTranscriptInstance}" field="transcript"/></span>

                </li>

				<li class="fieldcontain">
					<span id="processedTranscript-label" class="property-label"><g:message code="humanTranscript.processedTranscript.label" default="Processed Transcript" /></span>
					
						<span class="property-value" aria-labelledby="processedTranscript-label"><g:fieldValue bean="${humanTranscriptInstance}" field="processedTranscript"/></span>
					
				</li>


                <li class="fieldcontain">
                    <span id="transcriptErrors-label" class="property-label"><g:message code="humanTranscript.transcriptErrors.label" default="Transcript Errors" /></span>

                    <span class="property-value" aria-labelledby="transcriptErrors-label"><g:fieldValue bean="${humanTranscriptInstance}" field="transcriptErrors"/></span>

                </li>

                <li class="fieldcontain">
                    <span id="passageErrors-label" class="property-label"><g:message code="humanTranscript.passageErrors.label" default="Passage Errors" /></span>

                    <span class="property-value" aria-labelledby="passageErrors-label"><g:fieldValue bean="${humanTranscriptInstance}" field="passageErrors"/></span>

                </li>

                <li class="fieldcontain">
                    <span id="note-label" class="property-label"><g:message code="humanTranscript.note.label" default="Note" /></span>

                    <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${humanTranscriptInstance}" field="note"/></span>

                </li>


			</ol>
			<g:form url="[resource:humanTranscriptInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${humanTranscriptInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
