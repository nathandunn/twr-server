<%@ page import="edu.uoregon.secondlook.TranscriptionStatus; edu.uoregon.secondlook.ComputerTranscript" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'computerTranscript.label', default: 'ComputerTranscript')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-computerTranscript" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                         default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-computerTranscript" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]"/></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list computerTranscript">

    <g:if test="${computerTranscriptInstance?.returnDate}">
        <li class="fieldcontain">
            <span id="returnDate-label" class="property-label"><g:message code="computerTranscript.returnDate.label"
                                                                          default="Return Date"/></span>

            <span class="property-value" aria-labelledby="returnDate-label"><g:formatDate
                    date="${computerTranscriptInstance?.returnDate}"/></span>

        </li>
    </g:if>


    <g:if test="${computerTranscriptInstance?.status}">
        <li class="fieldcontain">
            <span id="status-label" class="property-label"><g:message code="computerTranscript.status.label"
                                                                      default="Status"/></span>

            <span class="property-value" aria-labelledby="status-label"><g:fieldValue
                    bean="${computerTranscriptInstance}" field="status"/></span>

        </li>
    </g:if>

    <g:if test="${computerTranscriptInstance?.twr}">
        <li class="fieldcontain">
            <span id="twr-label" class="property-label"><g:message code="computerTranscript.twr.label"
                                                                   default="Twr"/></span>

            <span class="property-value" aria-labelledby="twr-label"><g:fieldValue
                    bean="${computerTranscriptInstance}" field="twr"/>
            ...
            ${computerTranscriptInstance?.twr >= 2 ? computerTranscriptInstance.audioFile.passage.getWord(computerTranscriptInstance.twr - 2) : '?'}
            ${computerTranscriptInstance?.twr >= 1 ? computerTranscriptInstance.audioFile.passage.getWord(computerTranscriptInstance.twr - 1) : '?'}
                <b>${computerTranscriptInstance.audioFile.passage.getWord(computerTranscriptInstance.twr)}</b>
                ${computerTranscriptInstance.audioFile.passage.getWord(computerTranscriptInstance.twr + 1)}
                ${computerTranscriptInstance.audioFile.passage.getWord(computerTranscriptInstance.twr + 2)}
                ...
            </span>

        </li>
    </g:if>

%{--<g:if test="${computerTranscriptInstance?.transcriptErrors}">--}%
%{--<li class="fieldcontain">--}%
%{--<span id="transcriptErrors-label" class="property-label"><g:message--}%
%{--code="computerTranscript.transcriptErrors.label" default="Transcript Errors"/></span>--}%

%{--<span class="property-value" aria-labelledby="transcriptErrors-label"><g:fieldValue--}%
%{--bean="${computerTranscriptInstance}" field="transcriptErrors"/></span>--}%

%{--</li>--}%
%{--</g:if>--}%


    <li class="fieldcontain">
        <span id="transcript-label" class="property-label"><g:message code="computerTranscript.transcript.label"
                                                                      default="Computer Transcript"/></span>

        <span class="property-value" aria-labelledby="transcript-label">
            %{--<g:fieldValue bean="${computerTranscriptInstance}" field="transcript"/>--}%
            <g:if test="${computerTranscriptInstance?.transcript}">
                Charaters: ${computerTranscriptInstance?.transcript.size()}
                <g:link action="downloadTimingsFile" id="${computerTranscriptInstance.id}">
                    Timings File
                </g:link>
                &nbsp;
                <g:link action="downloadTranscripts" id="${computerTranscriptInstance.id}">
                    Transcript
                </g:link>
            %{--${computerTranscriptInstance?.transcript}--}%
            </g:if>
            <g:else>
                ------
            </g:else>
        </span>

    </li>

    <li class="fieldcontain">
        <span id="humanAnnotation-label" class="property-label"><g:message code="computerTranscript.transcript.label"
                                                                           default="Human Annotation"/></span>
        <span class="property-value" aria-labelledby="humanAnnotation-label">
            <g:if test="${computerTranscriptInstance?.humanAnnotation}">
                ${computerTranscriptInstance.humanAnnotation}
            </g:if>
            <g:else>
                ------
            </g:else>
        </span>
    </li>

%{--<g:if test="${computerTranscriptInstance?.passageErrors}">--}%
%{--<li class="fieldcontain">--}%
%{--<span id="passageErrors-label" class="property-label"><g:message--}%
%{--code="computerTranscript.passageErrors.label" default="Passage Errors"/></span>--}%

%{--<span class="property-value" aria-labelledby="passageErrors-label"><g:fieldValue--}%
%{--bean="${computerTranscriptInstance}" field="passageErrors"/></span>--}%

%{--</li>--}%
%{--</g:if>--}%


    <g:if test="${computerTranscriptInstance?.transcriptionEngine}">
        <li class="fieldcontain">
            <span id="transcriptionEngine-label" class="property-label"><g:message
                    code="computerTranscript.transcriptionEngine.label" default="Transcription Engine"/></span>

            <span class="property-value" aria-labelledby="transcriptionEngine-label"><g:link
                    controller="transcriptionEngine" action="show"
                    id="${computerTranscriptInstance?.transcriptionEngine?.id}">${computerTranscriptInstance?.transcriptionEngine?.name}</g:link></span>

        </li>
    </g:if>

    <g:if test="${computerTranscriptInstance?.audioFile}">
        <li class="fieldcontain">
            <span id="audioFile-label" class="property-label"><g:message code="computerTranscript.audioFile.label"
                                                                         default="Audio File"/></span>

            <span class="property-value" aria-labelledby="audioFile-label"><g:link controller="audioFile"
                                                                                   action="show"
                                                                                   id="${computerTranscriptInstance?.audioFile?.id}">${computerTranscriptInstance?.audioFile?.fileName}</g:link></span>
        </li>
    </g:if>


    <li class="fieldcontain">
        <span id="processingDirectory-label" class="property-label"><g:message code="computerTranscript.processingDirectory.label"
                                                                     default="Processing Directory"/></span>

        <span class="property-value" aria-labelledby="processingDirectory-label">
            ${computerTranscriptInstance?.processingDirectory ?: '------'}
        </span>
    </li>

    <g:if test="${computerTranscriptInstance?.requestDate}">
        <li class="fieldcontain">
            <span id="requestDate-label" class="property-label"><g:message
                    code="computerTranscript.requestDate.label" default="Request Date"/></span>

            <span class="property-value" aria-labelledby="requestDate-label"><g:formatDate
                    date="${computerTranscriptInstance?.requestDate}"/></span>

        </li>
    </g:if>

    <li class="fieldcontain">
        <span id="note-label" class="property-label"><g:message code="computerTranscript.note.label"
                                                                default="Note"/></span>

        <span class="property-value" aria-labelledby="note-label">
            <g:if test="${computerTranscriptInstance?.note}">
                <g:fieldValue bean="${computerTranscriptInstance}" field="note"/>
            </g:if>
            <g:else>
                ------
            </g:else>
        </span>

    </li>

    <g:if test="${computerTranscriptInstance?.processingQueue}">
        <li class="fieldcontain">
            <span id="processingQueue-label" class="property-label"><g:message
                    code="processingQueue.processingQueue.label"
                    default="Processing Queue"/></span>

            <span class="property-value" aria-labelledby="processingQueue-label">
                %{--<g:each in="${computerTranscriptInstance.processingQueue}" var="processingQueue">--}%
                <g:link action="show" controller="processingQueue"
                        id="${computerTranscriptInstance.processingQueue.id}">Queue Details</g:link>
                %{--</g:each>--}%
            </span>

        </li>
    </g:if>

</ol>
<g:form url="[resource: computerTranscriptInstance, action: 'delete']" method="DELETE">
    <fieldset class="buttons">

        <g:if test="${computerTranscriptInstance.status == TranscriptionStatus.RECEIVED}">
            <g:link action="submitComputerTranscript" controller="processingQueue"
                    id="${computerTranscriptInstance.id}">
                Submit Transcript
            </g:link>
        </g:if>
        <g:else>
            <g:link action="submitComputerTranscript" controller="processingQueue"
                    id="${computerTranscriptInstance.id}">
                Submit Again
            </g:link>

            <g:link action="recalculateTwr" controller="computerTranscript" id="${computerTranscriptInstance.id}">
                Recalculate Twr
            </g:link>
        </g:else>

        %{--<g:if test="${computerTranscriptInstance.callbackUrl}">--}%
        %{--<g:link action="doCallback" controller="transcription" id="${computerTranscriptInstance.id}">--}%
        %{--Do Callback--}%
        %{--</g:link>--}%
        %{--</g:if>--}%

        <g:link class="edit" action="edit" resource="${computerTranscriptInstance}"><g:message
                code="default.button.edit.label" default="Edit"/></g:link>
        <g:actionSubmit class="delete" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
</g:form>
</div>
</body>
</html>
