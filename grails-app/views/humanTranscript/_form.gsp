<%@ page import="edu.uoregon.secondlook.HumanTranscript" %>



<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'processDate', 'error')} ">
	<label for="processDate">
		<g:message code="humanTranscript.processDate.label" default="Process Date" />
		
	</label>
	<g:datePicker name="processDate" precision="day"  value="${humanTranscriptInstance?.processDate}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'transcript', 'error')} ">
	<label for="transcript">
		<g:message code="humanTranscript.transcript.label" default="Transcript" />
		
	</label>
	<g:textField name="transcript" value="${humanTranscriptInstance?.transcript}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="humanTranscript.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${edu.uoregon.secondlook.TranscriptionStatus?.values()}" keys="${edu.uoregon.secondlook.TranscriptionStatus.values()*.name()}" required="" value="${humanTranscriptInstance?.status?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'twr', 'error')} ">
	<label for="twr">
		<g:message code="humanTranscript.twr.label" default="Twr" />
		
	</label>
	<g:field name="twr" type="number" value="${humanTranscriptInstance.twr}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'transcriptErrors', 'error')} ">
	<label for="transcriptErrors">
		<g:message code="humanTranscript.transcriptErrors.label" default="Transcript Errors" />
		
	</label>
	<g:textField name="transcriptErrors" value="${humanTranscriptInstance?.transcriptErrors}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'passageErrors', 'error')} ">
	<label for="passageErrors">
		<g:message code="humanTranscript.passageErrors.label" default="Passage Errors" />
		
	</label>
	<g:textField name="passageErrors" value="${humanTranscriptInstance?.passageErrors}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="humanTranscript.note.label" default="Note" />
		
	</label>
	<g:textField name="note" value="${humanTranscriptInstance?.note}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'researcher', 'error')} ">
	<label for="researcher">
		<g:message code="humanTranscript.researcher.label" default="Researcher" />
		
	</label>
	<g:textField name="researcher" value="${humanTranscriptInstance?.researcher}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'audioFile', 'error')} required">
	<label for="audioFile">
		<g:message code="humanTranscript.audioFile.label" default="Audio File" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="audioFile" name="audioFile.id" from="${edu.uoregon.secondlook.AudioFile.list()}" optionKey="id" required="" value="${humanTranscriptInstance?.audioFile?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: humanTranscriptInstance, field: 'processedTranscript', 'error')} ">
	<label for="processedTranscript">
		<g:message code="humanTranscript.processedTranscript.label" default="Processed Transcript" />
		
	</label>
	<g:textField name="processedTranscript" value="${humanTranscriptInstance?.processedTranscript}"/>

</div>

