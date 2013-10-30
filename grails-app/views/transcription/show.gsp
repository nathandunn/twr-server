<%@ page import="edu.uoregon.secondlook.TranscriptionStatus; edu.uoregon.secondlook.Transcription" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'transcription.label', default: 'Transcription')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-transcription" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-transcription" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list transcription">

        <g:if test="${transcriptionInstance?.fileName}">
            <li class="fieldcontain">
                <span id="fileName-label" class="property-label"><g:message code="transcription.fileName.label"
                                                                            default="File Name"/></span>

                <span class="property-value" aria-labelledby="fileName-label"><g:fieldValue
                        bean="${transcriptionInstance}" field="fileName"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="status-label" class="property-label"><g:message code="transcription.status.label"
                                                                      default="File Name"/></span>

            <span class="property-value" aria-labelledby="status-label"><g:fieldValue
                    bean="${transcriptionInstance}" field="status"/></span>

        </li>

        <g:if test="${transcriptionInstance?.audioData}">
            <li class="fieldcontain">
                <span id="audioData-label" class="property-label"><g:message code="transcription.audioData.label"
                                                                             default="Audio Data"/>
                </span>
                <span class="property-value" aria-labelledby="audioData-label">
                    <g:formatNumber number="${transcriptionInstance.audioData.length / 1E6}" type="number"/> MB
                    <a href="">Download</a>
                </span>
            </li>
        </g:if>

        <g:if test="${transcriptionInstance?.externalStudentId}">
            <li class="fieldcontain">
                <span id="externalStudentId-label" class="property-label"><g:message code="transcription.externalStudentId.label"
                                                                               default="External Student ID"/></span>

                <span class="property-value" aria-labelledby="externalStudentId-label">
                    ${transcriptionInstance.externalStudentId}
                </span>

            </li>
        </g:if>

        <g:if test="${transcriptionInstance?.requestDate}">
            <li class="fieldcontain">
                <span id="requestDate-label" class="property-label"><g:message code="transcription.requestDate.label"
                                                                               default="Request Date"/></span>

                <span class="property-value" aria-labelledby="requestDate-label"><g:formatDate
                        date="${transcriptionInstance?.requestDate}"/></span>

            </li>
        </g:if>

        <g:if test="${transcriptionInstance?.returnDate}">
            <li class="fieldcontain">
                <span id="returnDate-label" class="property-label"><g:message code="transcription.returnDate.label"
                                                                              default="Return Date"/></span>

                <span class="property-value" aria-labelledby="returnDate-label"><g:formatDate
                        date="${transcriptionInstance?.returnDate}"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="passage-label" class="property-label"><g:message code="transcription.passage.label"
                                                                       default="Passage"/></span>

            <span class="property-value" aria-labelledby="passage-label">
                <g:link action="show" controller="passage" id="${transcriptionInstance.passage.id}">
                    ${transcriptionInstance.passage.name}
                </g:link>
            </span>

        </li>

        <g:if test="${transcriptionInstance?.transcript}">
            <li class="fieldcontain">
                <span id="transcript-label" class="property-label"><g:message code="transcription.transcript.label"
                                                                              default="Transcript"/></span>

                <span class="property-value" aria-labelledby="transcript-label"><g:fieldValue
                        bean="${transcriptionInstance}" field="transcript"/></span>

            </li>

            <li class="fieldcontain">
                <span id="twr-label" class="property-label"><g:message code="twrion.twr.label"
                                                                       default="TWR"/></span>

                <span class="property-value" aria-labelledby="twr-label"><g:fieldValue
                        bean="${transcriptionInstance}" field="twr"/></span>

            </li>
        </g:if>

        <g:if test="${transcriptionInstance?.processingQueues}">
            <li class="fieldcontain">
                <span id="processingQueue-label" class="property-label"><g:message
                        code="processingQueue.processingQueue.label"
                        default="Processing Queue"/></span>

                <span class="property-value" aria-labelledby="processingQueue-label">
                    <g:each in="${transcriptionInstance.processingQueues}" var="processingQueue">
                        <g:link action="show" controller="processingQueue"
                                id="${processingQueue.id}">Queue Details</g:link>
                    </g:each>
                </span>

            </li>
        </g:if>

    </ol>


    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${transcriptionInstance?.id}"/>
            <g:if test="${transcriptionInstance.status == TranscriptionStatus.RECEIVED}">
                <g:link action="submitTranscript" controller="processingQueue" id="${transcriptionInstance.id}">
                    Submit Transcript
                </g:link>
            </g:if>
            <g:else>
                <g:link action="submitTranscript" controller="processingQueue" id="${transcriptionInstance.id}">
                    Submit Again
                </g:link>
            </g:else>

            <g:link class="edit" action="edit" id="${transcriptionInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
