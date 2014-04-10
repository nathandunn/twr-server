<%@ page import="edu.uoregon.secondlook.ComputerTranscript" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'computerTranscript.label', default: 'ComputerTranscript')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-computerTranscript" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                         default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-computerTranscript" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="returnDate"
                              title="${message(code: 'computerTranscript.returnDate.label', default: 'Request Date')}"/>

            <g:sortableColumn property="transcript"
                              title="${message(code: 'computerTranscript.transcript.label', default: 'Transcript Length')}"/>

            <g:sortableColumn property="status"
                              title="${message(code: 'computerTranscript.status.label', default: 'Status')}"/>

            <g:sortableColumn property="twr" title="${message(code: 'computerTranscript.twr.label', default: 'Twr')}"/>

            <g:sortableColumn property="audioFile"
                              title="${message(code: 'computerTranscript.twr.label', default: 'Audio File')}"/>

            %{--<th>Passage</th>--}%
            <g:sortableColumn property="audioFile.passage.externalId"
                              title="${message(code: 'computerTranscript.twr.label', default: 'Passage')}"/>



            %{--<g:sortableColumn property="transcriptErrors" title="${message(code: 'computerTranscript.transcriptErrors.label', default: 'Transcript Errors')}" />--}%
            %{----}%
            %{--<g:sortableColumn property="passageErrors" title="${message(code: 'computerTranscript.passageErrors.label', default: 'Passage Errors')}" />--}%

        </tr>
        </thead>
        <tbody>
        <g:each in="${computerTranscriptInstanceList}" status="i" var="computerTranscriptInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${computerTranscriptInstance.id}">${fieldValue(bean: computerTranscriptInstance, field: "requestDate")}</g:link></td>

                <td>${computerTranscriptInstance?.transcript.size()}</td>

                <td>${fieldValue(bean: computerTranscriptInstance, field: "status")}</td>

                <td>${fieldValue(bean: computerTranscriptInstance, field: "twr")}</td>
                <td>
                    <g:link action="show" id="${computerTranscriptInstance?.audioFile.id}" controller="audioFile">
                        ${computerTranscriptInstance.audioFile.fileName}
                    </g:link>
                </td>

                <td>
                    <g:link action="show" id="${computerTranscriptInstance?.audioFile?.passage.id}" controller="passage">
                        ${computerTranscriptInstance.audioFile.passage.externalId}
                        ${computerTranscriptInstance.audioFile.passage.name}
                    </g:link>
                </td>

                %{--<td>${fieldValue(bean: computerTranscriptInstance, field: "transcriptErrors")}</td>--}%
                %{----}%
                %{--<td>${fieldValue(bean: computerTranscriptInstance, field: "passageErrors")}</td>--}%

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${computerTranscriptInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
