<%@ page import="edu.uoregon.secondlook.TranscriptionEngine" %>



<div class="fieldcontain ${hasErrors(bean: transcriptionEngineInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="transcriptionEngine.note.label" default="Note" />
		
	</label>
	<g:textField name="note" value="${transcriptionEngineInstance?.note}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionEngineInstance, field: 'lookup', 'error')} required">
	<label for="lookup">
		<g:message code="transcriptionEngine.lookup.label" default="Lookup" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lookup" required="" value="${transcriptionEngineInstance?.lookup}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: transcriptionEngineInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="transcriptionEngine.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${transcriptionEngineInstance?.name}"/>

</div>

