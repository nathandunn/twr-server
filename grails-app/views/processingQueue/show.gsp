<%@ page import="edu.uoregon.secondlook.ProcessingQueue" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'processingQueue.label', default: 'ProcessingQueue')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-processingQueue" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                      default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-processingQueue" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list processingQueue">

        <g:if test="${processingQueueInstance?.entryDate}">
            <li class="fieldcontain">
                <span id="entryDate-label" class="property-label"><g:message code="processingQueue.entryDate.label"
                                                                             default="Entry Date"/></span>

                <span class="property-value" aria-labelledby="entryDate-label"><g:formatDate
                        date="${processingQueueInstance?.entryDate}"/></span>

            </li>
        </g:if>

        <g:if test="${processingQueueInstance?.status}">
            <li class="fieldcontain">
                <span id="status-label" class="property-label"><g:message code="processingQueue.status.label"
                                                                          default="Status"/></span>

                <span class="property-value" aria-labelledby="status-label"><g:fieldValue
                        bean="${processingQueueInstance}" field="status"/></span>

            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="transcription-label" class="property-label"><g:message code="processingQueue.transcription.label"
                                                                             default="Transcription"/></span>

            <span class="property-value" aria-labelledby="transcription-label"><g:link controller="computerTranscript"
                                                                                       action="show"
                                                                                       id="${processingQueueInstance?.computerTranscript?.id}">${processingQueueInstance?.computerTranscript?.audioFile.fileName}</g:link></span>

        </li>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${processingQueueInstance?.id}"/>
            <g:link class="edit" action="edit" id="${processingQueueInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
