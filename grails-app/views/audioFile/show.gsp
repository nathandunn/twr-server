<%@ page import="edu.uoregon.secondlook.AudioFile" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'audioFile.label', default: 'AudioFile')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <g:javascript src="diff_match_patch_uncompressed.js"/>
    <r:require modules="jquery"/>
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
        <li><g:link class="list" action="audioNeedingTranscription">Audio File Needing Transcription</g:link></li>
    </ul>
</div>

<div id="show-audioFile" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list audioFile">

        <li class="fieldcontain">
            <span id="fileName-label" class="property-label"><g:message code="audioFile.fileName.label"
                                                                        default="File Name"/></span>

            <span class="property-value" aria-labelledby="fileName-label"><g:fieldValue bean="${audioFileInstance}"
                                                                                        field="fileName"/></span>

        </li>

        <li class="fieldcontain">
            <span id="audioData-label" class="property-label"><g:message code="audioFile.audioData.label"
                                                                         default="Audio Data"/></span>

            <span class="property-value" aria-labelledby="audioData-label">
                <g:if test="${audioFileInstance?.audioData}">
                    <g:formatNumber number="${audioFileInstance.audioData.length / 1E6}" type="number"/> MB
                    <g:link action="downloadBinary" id="${audioFileInstance.id}">Download</g:link>
                </g:if>
                <g:else>
                    ------
                </g:else>
            </span>

        </li>

        <li class="fieldcontain">
            <span id="passage-label" class="property-label"><g:message code="audioFile.passage.label"
                                                                       default="Passage"/></span>

            <span class="property-value" aria-labelledby="passage-label"><g:link controller="passage" action="show"
                                                                                 id="${audioFileInstance?.passage?.id}">${audioFileInstance?.passage?.display}</g:link></span>

        </li>

        <li class="fieldcontain">
            <span id="externalStudentId-label" class="property-label"><g:message
                    code="audioFile.externalStudentId.label" default="External Student Id"/></span>

            <span class="property-value" aria-labelledby="externalStudentId-label">
                <g:fieldValue
                        bean="${audioFileInstance}" field="externalStudentId"/>
            </span>

        </li>

        <li class="fieldcontain">
            <span id="callbackUrl-label" class="property-label"><g:message code="audioFile.callbackUrl.label"
                                                                           default="Callback Url"/></span>

            <span class="property-value" aria-labelledby="callbackUrl-label"><g:fieldValue
                    bean="${audioFileInstance}" field="callbackUrl"/></span>

        </li>



        <li class="fieldcontain">
            <span id="computerTranscripts-label" class="property-label"><g:message
                    code="audioFile.computerTranscripts.label" default="Computer Transcripts"/></span>

            <span class="property-value" aria-labelledby="computerTranscripts-label">
                <g:if test="${computerTranscripts}">
                    <g:each in="${computerTranscripts}" var="transcriptEngine" status="st">
                        Engine:
                        <g:link action="show" controller="transcriptionEngine" id="${transcriptEngine.key.id}">
                            ${transcriptEngine?.key.name}:</g:link>

                        <g:each in="${transcriptEngine.value}" var="computerTranscript" status="st2">
                            <g:link action="show" controller="computerTranscript" id="${computerTranscript.id}">
                                <g:formatDate date="${computerTranscript.requestDate}" type="date"/>
                                ${computerTranscript.status} ${computerTranscript.twr}
                            </g:link>
                            <g:if test="${st2.intValue() < transcriptEngine.value.size() - 1}">
                                &bull;
                            </g:if>
                        </g:each>

                        <g:if test="${st.intValue() < computerTranscripts.size() - 1}">
                            <br/>
                        </g:if>
                    %{--${transcriptEngine.value.size()}--}%
                    %{--<g:link controller="computerTranscript" action="show" id="${c.id}">--}%
                    %{--<g:formatDate date="${c.requestDate}" type="date"/>--}%
                    %{--[${c.twr}]--}%
                    %{--[${c.status}]--}%
                    %{--[${c?.transcriptionEngine?.name}]--}%
                    %{--</g:link>--}%

                    </g:each>
                </g:if>
                <g:else>
                    ------
                </g:else>

                <br/>
                <g:link action="create" controller="computerTranscript"
                        params="[audioFile: audioFileInstance.id]">Add Computer Transcript</g:link>
            </span>
        </li>


        <li class="fieldcontain">
            <span id="humanTranscripts-label" class="property-label"><g:message
                    code="audioFile.humanTranscripts.label" default="Human Transcripts"/></span>

            <span class="property-value" aria-labelledby="humanTranscripts-label">
                <g:if test="${audioFileInstance?.humanTranscripts}">
                    <g:each in="${audioFileInstance.humanTranscripts}" var="h">
                        <g:link controller="humanTranscript" action="show" id="${h.id}">
                            <g:formatDate date="${h.processDate}" type="date"/>
                        %{--<g:if test="${c.status == edu.uoregon.secondlook.TranscriptionStatus.FINISHED}">--}%
                            TWR[${h.twr}]
                        %{--</g:if>--}%
                        %{--<g:else>--}%
                            [${h.status}]</g:link>
                    </g:each>
                </g:if>
                <g:else>
                    -------
                </g:else>
                <br/>
                <g:link action="create" controller="humanTranscript"
                        params="[audioFile: audioFileInstance.id]">Add Human Transcript</g:link>
            </span>

        </li>

        <li class="fieldcontain">
            <span id="diff-label" class="property-label">
                Diffs
            </span>




            <span class="property-value" aria-labelledby="note-label">
                %{--<g:fieldValue bean="${audioFileInstance}" field="note"/>--}%
                <g:select id="patch1" name="patch1" from="${audioFileInstance.humanTranscripts}" optionValue="display"
                          optionKey="id"/>
                vs
                %{--<g:select name="patch2" from="${availableTranscripts}"/>--}%
                <g:select id="patch2" name="patch2" from="${audioFileInstance.computerTranscripts}"
                          optionValue="display" optionKey="id"/>
                <div id="diff-box">
                    Not implemented yet
                </div>
            </span>
        </li>

        <li class="fieldcontain">
            <span id="note-label" class="property-label"><g:message code="audioFile.note.label"
                                                                    default="Note"/></span>

            <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${audioFileInstance}"
                                                                                    field="note"/></span>

        </li>

        <li class="fieldcontain">
            <span id="processingQueues-label" class="property-label"><g:message
                    code="audioFile.processingQueues.label" default="Processing Queues"/></span>

            <g:each in="${audioFileInstance.processingQueues}" var="p">
                <span class="property-value" aria-labelledby="processingQueues-label"><g:link
                        controller="processingQueue" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
            </g:each>

        </li>

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
<script>

    function applyDiffs(humanId, computerId) {
//        $('#diff-box').text('hum: ' + humanId + ' comp:' + computerId);

        %{--'<g:createLink action="getTranscripts" controller="audioFile" />'--}%

        $.post("${createLink( action:'getTranscripts',controller: 'audioFile')}?humanId="+humanId+"&computerId="+computerId+""
                , function (data) {
//                    alert(data['computerTranscript']);
                    var dmp = new diff_match_patch();
//                    $('#diff-box').text(data.computerTranscript + ' vs '  + data.humanTranscript);
//                    var diffString = dmp.diff_commonSuffix(data.computerTranscript,data.humanTranscript);
                    var diffString = dmp.diff_main(data.computerTranscript,data.humanTranscript)
                    $('#diff-box').html(dmp.diff_prettyHtml(diffString));
//                    alert('computer: '+values['computerTranscript']);
//                    alert('human: '+values.humanTranscript);
                });

    }

    $('#patch1').change(function () {
        var val = $('#patch2').find(":selected").val();
        applyDiffs(this.value, val);
    });
    $('#patch2').change(function () {
        var val = $('#patch1').find(":selected").val();
        applyDiffs(val, this.value);
    });
</script>

</body>
</html>
