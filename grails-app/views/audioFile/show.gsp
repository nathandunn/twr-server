<%@ page import="edu.uoregon.secondlook.AudioFile" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'audioFile.label', default: 'AudioFile')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-audioFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-audioFile" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list audioFile">

        <g:if test="${audioFileInstance?.audioData}">
            <li class="fieldcontain">
                <span id="audioData-label" class="property-label"><g:message code="audioFile.audioData.label"
                                                                             default="Audio Data"/></span>

            </li>
        </g:if>

        <g:if test="${audioFileInstance?.externalStudentId}">
            <li class="fieldcontain">
                <span id="externalStudentId-label" class="property-label"><g:message
                        code="audioFile.externalStudentId.label" default="External Student Id"/></span>

                <span class="property-value" aria-labelledby="externalStudentId-label"><g:fieldValue
                        bean="${audioFileInstance}" field="externalStudentId"/></span>

            </li>
        </g:if>

        <g:if test="${audioFileInstance?.callbackUrl}">
            <li class="fieldcontain">
                <span id="callbackUrl-label" class="property-label"><g:message code="audioFile.callbackUrl.label"
                                                                               default="Callback Url"/></span>

                <span class="property-value" aria-labelledby="callbackUrl-label"><g:fieldValue
                        bean="${audioFileInstance}" field="callbackUrl"/></span>

            </li>
        </g:if>

        <g:if test="${audioFileInstance?.note}">
            <li class="fieldcontain">
                <span id="note-label" class="property-label"><g:message code="audioFile.note.label"
                                                                        default="Note"/></span>

                <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${audioFileInstance}"
                                                                                        field="note"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="computerTranscripts-label" class="property-label"><g:message
                    code="audioFile.computerTranscripts.label" default="Computer Transcripts"/></span>

            <span class="property-value" aria-labelledby="computerTranscripts-label">
            <g:if test="${audioFileInstance?.computerTranscripts}">
                <g:each in="${audioFileInstance.computerTranscripts}" var="c">
                    <g:link controller="computerTranscript" action="show" id="${c.id}">
                    <g:formatDate date="${c.requestDate}" type="date"/>
                    <g:if test="${c.status == edu.uoregon.secondlook.TranscriptionStatus.FINISHED}">
                        TWR[${c.twr}]
                    </g:if>
                    <g:else>
                        Status[${c.status}]
                    </g:else>

                </g:link>
                </g:each>
            </g:if>
            <g:else>
                ------
            </g:else>
            </span>
        </li>

        <g:if test="${audioFileInstance?.fileName}">
            <li class="fieldcontain">
                <span id="fileName-label" class="property-label"><g:message code="audioFile.fileName.label"
                                                                            default="File Name"/></span>

                <span class="property-value" aria-labelledby="fileName-label"><g:fieldValue bean="${audioFileInstance}"
                                                                                            field="fileName"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="humanTranscripts-label" class="property-label"><g:message
                    code="audioFile.humanTranscripts.label" default="Human Transcripts"/></span>

            <span class="property-value" aria-labelledby="humanTranscripts-label">
                <g:if test="${audioFileInstance?.humanTranscripts}">
                    <g:each in="${audioFileInstance.humanTranscripts}" var="h">
                        <g:link controller="humanTranscript" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link>
                    </g:each>
                </g:if>
                <g:else>
                    -------
                </g:else>
            </span>

        </li>

        <g:if test="${audioFileInstance?.passage}">
            <li class="fieldcontain">
                <span id="passage-label" class="property-label"><g:message code="audioFile.passage.label"
                                                                           default="Passage"/></span>

                <span class="property-value" aria-labelledby="passage-label"><g:link controller="passage" action="show"
                                                                                     id="${audioFileInstance?.passage?.id}">${audioFileInstance?.passage?.name}</g:link></span>

            </li>
        </g:if>

        <g:if test="${audioFileInstance?.processingQueues}">
            <li class="fieldcontain">
                <span id="processingQueues-label" class="property-label"><g:message
                        code="audioFile.processingQueues.label" default="Processing Queues"/></span>

                <g:each in="${audioFileInstance.processingQueues}" var="p">
                    <span class="property-value" aria-labelledby="processingQueues-label"><g:link
                            controller="processingQueue" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: audioFileInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${audioFileInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
