<%@ page import="edu.uoregon.secondlook.Transcription" %>



<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'audioData', 'error')} required">
    <label for="audioData">
        <g:message code="transcription.audioData.label" default="Audio Data"/>
        <span class="required-indicator">*</span>
    </label>
    <input type="file" id="audioData" name="audioData"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'passage', 'error')} ">
    <label for="passage">
        <g:message code="transcription.passage.label" default="Passage Name"/>

    </label>
    %{--<g:textField name="passage" value="${transcriptionInstance?.passage}"/>--}%
    <g:select name="passage.id"
              from="${edu.uoregon.secondlook.Passage.listOrderByName()}"
              value="${transcriptionInstance.passage}"
              optionValue="name" optionKey="id"/>

</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionInstance, field: 'externalStudentId', 'error')} ">
<label for="externalStudentId">
<g:message code="transcription.externalStudentId.label" default="External Student ID" />

</label>
<g:textField name="externalStudentId" value="${transcriptionInstance?.externalStudentId}"/>
</div>

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

