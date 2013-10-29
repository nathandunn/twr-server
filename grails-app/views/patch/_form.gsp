<%@ page import="edu.uoregon.secondlook.Patch" %>



<div class="fieldcontain ${hasErrors(bean: patchInstance, field: 'appliedDate', 'error')} required">
	<label for="appliedDate">
		<g:message code="patch.appliedDate.label" default="Applied Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="appliedDate" precision="day"  value="${patchInstance?.appliedDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: patchInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="patch.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${patchInstance?.name}"/>
</div>

