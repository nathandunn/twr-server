<%@ page import="edu.uoregon.secondlook.Transcription" %>



<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'passage', 'error')} ">
    <label for="passage">
        <g:message code="transcription.passage.label" default="Passage Name"/>
        <span class="required-indicator">*</span>
    </label>
    %{--<g:textField name="passage" value="${transcriptionInstance?.passage}"/>--}%
    <g:select name="passage.id"
              from="${edu.uoregon.secondlook.Passage.listOrderByExternalId()}"
              value="${transcriptionInstance.passage}"
              optionValue="display" optionKey="id"/>

</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'audioData', 'error')} required">
    <label for="audioData">
        <g:message code="transcription.audioData.label" default="Audio Data"/>
        <span class="required-indicator">*</span>
    </label>
    <input type="file" id="audioData" name="audioData"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'externalStudentId', 'error')} ">
    <label for="externalStudentId">
        <g:message code="transcription.externalStudentId.label" default="External Student ID"/>

    </label>
    <g:textField name="externalStudentId" value="${transcriptionInstance?.externalStudentId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'goldenTranscript', 'error')} ">
    <label for="note">
        <g:message code="transcription.goldenTranscript.label" default="Golden Transcript"/>
    </label>
    <g:textArea name="goldenTranscript" value="${transcriptionInstance?.goldenTranscript}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'callbackUrl', 'error')} required">
    <label for="callbackUrl">
        <g:message code="transcription.callbackUrl.label" default="Calblabck Url"/>
        %{--<span class="required-indicator">*</span>--}%
    </label>
    <g:textField name="callbackUrl" value="${transcriptionInstance.callbackUrl}" size="80"/>
    %{--<input type="url" id="callbackUrl" name="callbackUrl" size="80"/>--}%
</div>


<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="transcription.note.label" default="Transcript Notes"/>
    </label>
    <g:textArea name="note" value="${transcriptionInstance?.note}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'transcriptErrors', 'error')} ">--}%
%{--<label for="transcriptErrors">--}%
%{--<g:message code="transcription.transcriptErrors.label" default="Transcript Errors"/>--}%
%{--</label>--}%
%{--<g:textArea name="transcriptErrors" value="${transcriptionInstance?.transcriptErrors}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'fileName', 'error')} ">--}%
%{--<label for="fileName">--}%
%{--<g:message code="transcription.fileName.label" default="File Name" />--}%
%{----}%
%{--</label>--}%
%{--<g:textField name="fileName" value="${transcriptionInstance?.fileName}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'requestDate', 'error')} required">--}%
%{--<label for="requestDate">--}%
%{--<g:message code="transcription.requestDate.label" default="Request Date" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:datePicker name="requestDate" precision="day"  value="${transcriptionInstance?.requestDate}"  />--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'returnDate', 'error')} required">--}%
%{--<label for="returnDate">--}%
%{--<g:message code="transcription.returnDate.label" default="Return Date" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:datePicker name="returnDate" precision="day"  value="${transcriptionInstance?.returnDate}"  />--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'transcript', 'error')} ">--}%
%{--<label for="transcript">--}%
%{--<g:message code="transcription.transcript.label" default="Transcript" />--}%
%{----}%
%{--</label>--}%
%{--<g:textField name="transcript" value="${transcriptionInstance?.transcript}"/>--}%
%{--</div>--}%

