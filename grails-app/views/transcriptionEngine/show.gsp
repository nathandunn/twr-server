<%@ page import="edu.uoregon.secondlook.TranscriptionEngine" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'transcriptionEngine.label', default: 'TranscriptionEngine')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-transcriptionEngine" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                          default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-transcriptionEngine" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list transcriptionEngine">

        <li class="fieldcontain">
            <span id="name-label" class="property-label"><g:message code="transcriptionEngine.name.label"
                                                                    default="Name"/></span>

            <span class="property-value" aria-labelledby="name-label"><g:fieldValue
                    bean="${transcriptionEngineInstance}" field="name"/></span>

        </li>

        <li class="fieldcontain">
            <span id="lookup-label" class="property-label"><g:message code="transcriptionEngine.lookup.label"
                                                                      default="Lookup"/></span>

            <span class="property-value" aria-labelledby="lookup-label"><g:fieldValue
                    bean="${transcriptionEngineInstance}" field="lookup"/></span>

        </li>



        <li class="fieldcontain">
            <span id="note-label" class="property-label"><g:message code="transcriptionEngine.note.label"
                                                                    default="Note"/></span>

            <span class="property-value" aria-labelledby="note-label"><g:fieldValue
                    bean="${transcriptionEngineInstance}" field="note"/></span>

        </li>

        <li class="fieldcontain">
            <span id="defaultEngine-label" class="property-label">
                <g:message code="transcriptionEngine.defaultEngine.label" default="Default Engine"/>
            </span>
            <span class="property-value" aria-labelledby="defaultEngine-label">
                <g:fieldValue bean="${transcriptionEngineInstance}" field="defaultEngine"/>
            </span>
        </li>

        <li class="fieldcontain">
            <span id="defaultEngine-label" class="property-label">
                Average TWR Difference (Human - Computer )
            </span>
            <span class="property-value" aria-labelledby="defaultEngine-label">
                |${averageHumanTwr}
                -
                ${averageComputerTwr}|
                 =  ${Math.abs(averageComputerTwr-averageHumanTwr) as Integer}
            </span>
        </li>

        <li class="fieldcontain">
            <span id="defaultEngine-label" class="property-label">
                Average Error
            </span>
            <span class="property-value not-implemented" aria-labelledby="defaultEngine-label">
                ${averageErrorDifference}
            </span>
        </li>

        <li class="fieldcontain">
            <span id="transcriptionEngines-label" class="property-label">
                Computer Transcripts
                <g:link action="createRemaining" id="${transcriptionEngineInstance.id}">Compute Remaining</g:link>
            </span>

            <span class="property-value" aria-labelledby="note-label">
                <g:each in="${computerTranscripts}" var="transcript">
                    <g:link action="show" id="${transcript.id}" controller="computerTranscript">
                        ${transcript.audioFile.fileName}
                    </g:link>
                    &bull;
                </g:each>
            </span>

        </li>

    </ol>
    <g:form url="[resource: transcriptionEngineInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${transcriptionEngineInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
