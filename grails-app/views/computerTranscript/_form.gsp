<%@ page import="edu.uoregon.secondlook.ComputerTranscript" %>



<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'returnDate', 'error')} ">
	<label for="returnDate">
		<g:message code="computerTranscript.returnDate.label" default="Return Date" />
		
	</label>
	<g:datePicker name="returnDate" precision="day"  value="${computerTranscriptInstance?.returnDate}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcript', 'error')} ">
	<label for="transcript">
		<g:message code="computerTranscript.transcript.label" default="Transcript" />
		
	</label>
	<g:textField name="transcript" value="${computerTranscriptInstance?.transcript}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="computerTranscript.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${edu.uoregon.secondlook.TranscriptionStatus?.values()}" keys="${edu.uoregon.secondlook.TranscriptionStatus.values()*.name()}" required="" value="${computerTranscriptInstance?.status?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'twr', 'error')} ">
	<label for="twr">
		<g:message code="computerTranscript.twr.label" default="Twr" />
		
	</label>
	<g:field name="twr" type="number" value="${computerTranscriptInstance.twr}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcriptErrors', 'error')} ">
	<label for="transcriptErrors">
		<g:message code="computerTranscript.transcriptErrors.label" default="Transcript Errors" />
		
	</label>
	<g:textField name="transcriptErrors" value="${computerTranscriptInstance?.transcriptErrors}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'passageErrors', 'error')} ">
	<label for="passageErrors">
		<g:message code="computerTranscript.passageErrors.label" default="Passage Errors" />
		
	</label>
	<g:textField name="passageErrors" value="${computerTranscriptInstance?.passageErrors}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="computerTranscript.note.label" default="Note" />
		
	</label>
	<g:textField name="note" value="${computerTranscriptInstance?.note}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcriptionEngine', 'error')} ">
	<label for="transcriptionEngine">
		<g:message code="computerTranscript.transcriptionEngine.label" default="Transcription Engine" />
		
	</label>
	<g:select id="transcriptionEngine" name="transcriptionEngine.id" from="${edu.uoregon.secondlook.TranscriptionEngine.list()}" optionKey="id" value="${computerTranscriptInstance?.transcriptionEngine?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'audioFile', 'error')} required">
	<label for="audioFile">
		<g:message code="computerTranscript.audioFile.label" default="Audio File" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="audioFile" name="audioFile.id" from="${edu.uoregon.secondlook.AudioFile.list()}" optionKey="id" required="" value="${computerTranscriptInstance?.audioFile?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'requestDate', 'error')} required">
	<label for="requestDate">
		<g:message code="computerTranscript.requestDate.label" default="Request Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="requestDate" precision="day"  value="${computerTranscriptInstance?.requestDate}"  />

</div>

