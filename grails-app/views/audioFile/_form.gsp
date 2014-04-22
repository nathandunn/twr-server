<%@ page import="edu.uoregon.secondlook.AudioFile" %>


<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'fileName', 'error')} required">
    <label for="fileName">
        <g:message code="audioFile.fileName.label" default="File Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fileName" required="" value="${audioFileInstance?.fileName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'audioData', 'error')} required">
    <label for="audioData">
        <g:message code="audioFile.audioData.label" default="Audio Data"/>
        <span class="required-indicator">*</span>
    </label>
    <input type="file" id="audioData" name="audioData" />

</div>


<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'passage', 'error')} required">
    <label for="passage">
        <g:message code="audioFile.passage.label" default="Passage"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="passage" name="passage.id" from="${edu.uoregon.secondlook.Passage.list()}"
              optionValue="display" optionKey="id" required="" value="${audioFileInstance?.passage?.id}"
              class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'externalStudentId', 'error')} ">
    <label for="externalStudentId">
        <g:message code="audioFile.externalStudentId.label" default="External Student Id"/>

    </label>
    <g:textField name="externalStudentId" value="${audioFileInstance?.externalStudentId}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'callbackUrl', 'error')} ">
    <label for="callbackUrl">
        <g:message code="audioFile.callbackUrl.label" default="Callback Url"/>

    </label>
    <g:textField name="callbackUrl" value="${audioFileInstance?.callbackUrl}" size="80"/>

</div>

<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="audioFile.note.label" default="Note"/>

    </label>
    <g:textArea name="note" value="${audioFileInstance?.note}"/>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'computerTranscripts', 'error')} ">--}%
%{--<label for="computerTranscripts">--}%
%{--<g:message code="audioFile.computerTranscripts.label" default="Computer Transcripts" />--}%
%{----}%
%{--</label>--}%
%{--<g:select name="computerTranscripts" from="${edu.uoregon.secondlook.ComputerTranscript.list()}" multiple="multiple" optionKey="id" size="5" value="${audioFileInstance?.computerTranscripts*.id}" class="many-to-many"/>--}%

%{--</div>--}%


%{--<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'humanTranscripts', 'error')} ">--}%
%{--<label for="humanTranscripts">--}%
%{--<g:message code="audioFile.humanTranscripts.label" default="Human Transcripts" />--}%
%{----}%
%{--</label>--}%
%{--<g:select name="humanTranscripts" from="${edu.uoregon.secondlook.HumanTranscript.list()}" multiple="multiple" optionKey="id" size="5" value="${audioFileInstance?.humanTranscripts*.id}" class="many-to-many"/>--}%

%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: audioFileInstance, field: 'processingQueues', 'error')} ">--}%
%{--<label for="processingQueues">--}%
%{--<g:message code="audioFile.processingQueues.label" default="Processing Queues" />--}%
%{----}%
%{--</label>--}%
%{--<g:select name="processingQueues" from="${edu.uoregon.secondlook.ProcessingQueue.list()}" multiple="multiple" optionKey="id" size="5" value="${audioFileInstance?.processingQueues*.id}" class="many-to-many"/>--}%

%{--</div>--}%

