<%@ page import="edu.uoregon.secondlook.Transcription" %>
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

        <g:if test="${transcriptionInstance?.audioData}">
            <li class="fieldcontain">
                <span id="audioData-label" class="property-label"><g:message code="transcription.audioData.label"
                                                                             default="Audio Data"/>
                </span>
                <span class="property-value" aria-labelledby="audioData-label">
                    <g:formatNumber number="${transcriptionInstance.audioData.length / 1E6}" type="number"/> MB
                </span>

            </li>
        </g:if>

        <g:if test="${transcriptionInstance?.fileName}">
            <li class="fieldcontain">
                <span id="fileName-label" class="property-label"><g:message code="transcription.fileName.label"
                                                                            default="File Name"/></span>

                <span class="property-value" aria-labelledby="fileName-label"><g:fieldValue
                        bean="${transcriptionInstance}" field="fileName"/></span>

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

        <g:if test="${transcriptionInstance?.transcript}">
            <li class="fieldcontain">
                <span id="transcript-label" class="property-label"><g:message code="transcription.transcript.label"
                                                                              default="Transcript"/></span>

                <span class="property-value" aria-labelledby="transcript-label"><g:fieldValue
                        bean="${transcriptionInstance}" field="transcript"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${transcriptionInstance?.id}"/>
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
