<%@ page import="edu.uoregon.secondlook.ProcessingQueue" %>



<div class="fieldcontain ${hasErrors(bean: processingQueueInstance, field: 'entryDate', 'error')} required">
	<label for="entryDate">
		<g:message code="processingQueue.entryDate.label" default="Entry Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="entryDate" precision="day"  value="${processingQueueInstance?.entryDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: processingQueueInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="processingQueue.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${edu.uoregon.secondlook.ProcessingStatus?.values()}" keys="${edu.uoregon.secondlook.ProcessingStatus.values()*.name()}" required="" value="${processingQueueInstance?.status?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: processingQueueInstance, field: 'transcription', 'error')} required">
	<label for="transcription">
		<g:message code="processingQueue.transcription.label" default="Transcription" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="transcription" name="transcription.id" from="${edu.uoregon.secondlook.Transcription.list()}" optionKey="id" required="" value="${processingQueueInstance?.transcription?.id}" class="many-to-one"/>
</div>

