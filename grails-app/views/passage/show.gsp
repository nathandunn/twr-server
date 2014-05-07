<%@ page import="edu.uoregon.secondlook.Passage" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'passage.label', default: 'Passage')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <r:require module="jquery"/>
</head>

<body>
<a href="#show-passage" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-passage" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <ol class="property-list passage">

        <g:if test="${passageInstance?.externalId}">
            <li class="fieldcontain">
                <span id="externalId-label" class="property-label"><g:message code="passage.externalId.label"
                                                                              default="External Id"/></span>

                <span class="property-value" aria-labelledby="externalId-label"><g:fieldValue bean="${passageInstance}"
                                                                                              field="externalId"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="name-label" class="property-label"><g:message code="passage.name.label"
                                                                    default="Name"/></span>

            <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${passageInstance}"
                                                                                    field="name"/></span>
        </li>

        <li class="fieldcontain">
            <span id="audioFiles-label" class="property-label">Audio Files</span>

            <span class="property-value" aria-labelledby="name-label">
                <g:if test="${passageInstance.audioFiles}">
                    <g:each in="${passageInstance.audioFiles}" var="audioFile" status="st">
                        ${audioFile?.humanTranscripts.size() == 0 ? '<b color="red">' : ''}
                        <g:link action="show" controller="audioFile" id="${audioFile.id}">${audioFile.fileName}
                            (${audioFile?.humanTranscripts.size()} / ${audioFile?.computerTranscripts.size()})</g:link>
                        ${audioFile?.humanTranscripts.size() == 0 ? '</b>' : ''}
                        <g:if test="${st.intValue() < passageInstance.audioFiles.size() - 1}">
                            &bull;
                        </g:if>

                    </g:each>
                </g:if>
                <g:else>
                    ---------
                </g:else>
            </span>
        </li>

        <li class="fieldcontain">
            <span id="transcriptionEngine-label" class="property-label">Transcription Engine Errors</span>

            <span class="property-value" aria-labelledby="name-label">
                <table>
                    <thead>
                    <tr>
                        <th>Engine</th><th>Count (C,H)</th><th>Avg TWR |C-H|</th>
                    </tr>
                    </thead>
                    <g:each in="${engineData}" var="engine">
                        <tr>
                            <td>
                                <g:link action="show" controller="transcriptionEngine" id="${engine.key.id}">
                                    ${engine.key.name}
                                </g:link>
                            </td>
                            <td>
                                ${engine.value.computerCount},${engine.value.humanCount}
                            </td>
                            <td>
                                |${engine.value.computerTwr}- ${engine.value.humanTwr}| = ${engine.value.calculateTwrError}
                            </td>
                        </tr>
                    </g:each>
                </table>
            </span>
        </li>

        <script>
            $(document).ready(function() {
                $(".numberShow").hide();
            });
        </script>

        <g:if test="${passageInstance?.text}">
            <li class="fieldcontain">
                <span id="text-label" class="property-label"><g:message code="passage.text.label"
                                                                        default="Text"/></span>
                <span class="property-value" aria-labelledby="text-label">
                    <input type="button" onclick="$('.numberShow').toggle('slow');" value="Toggle Numbers"/>
                    <br/>
                    ${passageInstance.numberedText}
                </span>
            </li>

        %{--<li class="fieldcontain">--}%
        %{--<span id="text-label" class="property-label"><g:message code="passage.text.label"--}%
        %{--default="Numbered Text"/></span>--}%
        %{--<span class="property-value" aria-labelledby="text-label">--}%
        %{--</span>--}%
        %{--</li>--}%

        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${passageInstance?.id}"/>
            <g:link class="edit" action="edit" id="${passageInstance?.id}"><g:message code="default.button.edit.label"
                                                                                      default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
