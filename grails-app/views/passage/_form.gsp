<%@ page import="edu.uoregon.secondlook.Passage" %>



<div class="fieldcontain ${hasErrors(bean: passageInstance, field: 'externalId', 'error')} ">
	<label for="externalId">
		<g:message code="passage.externalId.label" default="External Id" />
		
	</label>
	<g:textField name="externalId" value="${passageInstance?.externalId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: passageInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="passage.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${passageInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: passageInstance, field: 'text', 'error')} ">
	<label for="text">
		<g:message code="passage.text.label" default="Text" />
		
	</label>
	<g:textArea name="text" value="${passageInstance?.text}"/>
</div>

