<%@ page import="edu.uoregon.secondlook.Transcription" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'transcription.label', default: 'Transcription')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-transcription" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-transcription" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            %{--<g:sortableColumn property="audioData" title="${message(code: 'transcription.audioData.label', default: 'Audio Data')}" />--}%

            <g:sortableColumn property="fileName"
                              title="${message(code: 'transcription.fileName.label', default: 'File Name')}"/>

            <g:sortableColumn property="transcript"
                              title="${message(code: 'transcription.transcript.label', default: 'Transcript')}"/>

            <th>Audio Data</th>


            <g:sortableColumn property="requestDate"
                              title="${message(code: 'transcription.requestDate.label', default: 'Request Date')}"/>

            <g:sortableColumn property="returnDate"
                              title="${message(code: 'transcription.returnDate.label', default: 'Return Date')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${transcriptionInstanceList}" status="i" var="transcriptionInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${transcriptionInstance.id}">${fieldValue(bean: transcriptionInstance, field: "fileName")}</g:link></td>


                <td>
                    %{--${fieldValue(bean: transcriptionInstance, field: "transcript")}--}%
                    <g:if test="${transcriptionInstance.transcript}">

                        ${transcriptionInstance.twr}
                        ${transcriptionInstance?.transcript?.size()} characters

                        <g:link action="downloadTranscript" id="${transcriptionInstance.id}">
                            Download
                        </g:link>
                    </g:if>
                    <g:else>
                        Not generated
                    </g:else>

                </td>

                <td>
                    <g:formatNumber number="${transcriptionInstance.audioData.length / 1E6}" type="number"/> MB
                </td>

                <td><g:formatDate date="${transcriptionInstance.requestDate}"/></td>

                <td><g:formatDate date="${transcriptionInstance.returnDate}"/></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${transcriptionInstanceTotal}"/>
    </div>
</div>
</body>
</html>
