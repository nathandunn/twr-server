<%@ page import="edu.uoregon.secondlook.ComputerTranscript" %>

<head>
    <ckeditor:resources/>
    <ckeditor:config var="toolbar_Mytoolbar">
        [
            [ 'Source', '-', 'Bold','Underline','-','RemoveFormat','TextColor','Styles','Format','Font','FontSize'  ]
        ]
    </ckeditor:config>
</head>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcriptionEngine', 'error')} ">
    <label for="transcriptionEngine">
        <g:message code="computerTranscript.transcriptionEngine.label" default="Transcription Engine"/>

    </label>
    <g:select id="transcriptionEngine" name="transcriptionEngine.id"
              from="${edu.uoregon.secondlook.TranscriptionEngine.list()}" optionKey="id" optionValue="name"
              value="${computerTranscriptInstance?.transcriptionEngine?.id}" class="many-to-one"
              noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'audioFile', 'error')} required">
    <label for="audioFile">
        <g:message code="computerTranscript.audioFile.label" default="Audio File"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="audioFile" name="audioFile.id" from="${edu.uoregon.secondlook.AudioFile.list()}" optionKey="id"
              optionValue="fileName" required="" value="${computerTranscriptInstance?.audioFile?.id}"
              class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'requestDate', 'error')} required">
    <label for="requestDate">
        <g:message code="computerTranscript.requestDate.label" default="Request Date"/>
        <span class="required-indicator">*</span>
    </label>
    %{--<g:datePicker name="requestDate" precision="day"  value="${computerTranscriptInstance?.requestDate}"  />--}%
    <g:datePicker name="requestDate" precision="day" relativeYears="[-20..0]"
                  value="${computerTranscriptInstance?.requestDate}" default="${new Date()}"
                  noSelection="['': '']"/>

</div>

<g:if test="${computerTranscriptInstance.id}">
    <div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'returnDate', 'error')} ">
        <label for="returnDate">
            <g:message code="computerTranscript.returnDate.label" default="Return Date"/>

        </label>
        <g:datePicker name="returnDate" precision="day" relativeYears="[-20..0]"
                      value="${computerTranscriptInstance?.returnDate}" default="${new Date()}" noSelection="['': '']"/>
    </div>

    %{--<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcript', 'error')} ">--}%
        %{--<label for="transcript">--}%
            %{--<g:message code="computerTranscript.transcript.label" default="Computer Transcription"/>--}%

        %{--</label>--}%

        %{--${computerTranscriptInstance.transcript}--}%
        %{--<g:textArea name="transcript" value="${computerTranscriptInstance?.transcript}"/>--}%

    %{--</div>--}%
    <div class="fieldcontain">
        <span id="transcript-label" class="property-label"><g:message code="computerTranscript.transcript.label"
                                                                      default="Computer Transcript"/></span>

        <span class="property-value" aria-labelledby="transcript-label">
            %{--<g:fieldValue bean="${computerTranscriptInstance}" field="transcript"/>--}%
            <g:if test="${computerTranscriptInstance?.transcript}">
                Charaters: ${computerTranscriptInstance?.transcript.size()}
                <g:link action="downloadTimingsFile" id="${computerTranscriptInstance.id}">
                    Timings File
                </g:link>
                &nbsp;
                <g:link action="downloadTranscripts" id="${computerTranscriptInstance.id}">
                    Transcript
                </g:link>
            %{--${computerTranscriptInstance?.transcript}--}%
            </g:if>
            <g:else>
                ------
            </g:else>
        </span>

    </div>

    <div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcript', 'error')} ">
        <label for="transcript">
            <g:message code="computerTranscript.transcript.label" default="Human Annotation"/>

        </label>
        %{--<g:textArea name="transcript" value="${computerTranscriptInstance?.transcript}"/>--}%
        <div class="annotationTable">
            <ckeditor:editor name="humanAnnotation" height="100px" width="90%" toolbar="Mytoolbar">
                ${computerTranscriptInstance.humanAnnotation}
            </ckeditor:editor>
        </div>

    </div>

    <div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'twr', 'error')} ">
        <label for="twr">
            <g:message code="computerTranscript.twr.label" default="Twr"/>

        </label>
        <g:field name="twr" type="number" value="${computerTranscriptInstance.twr}"/>
    </div>


    <div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'status', 'error')} required">
        <label for="status">
            <g:message code="computerTranscript.status.label" default="Status"/>
            <span class="required-indicator">*</span>
        </label>
        <g:select name="status" from="${edu.uoregon.secondlook.TranscriptionStatus?.values()}"
                  keys="${edu.uoregon.secondlook.TranscriptionStatus.values()*.name()}" required=""
                  value="${computerTranscriptInstance?.status?.name()}"/>

    </div>




    %{--<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'transcriptErrors', 'error')} ">--}%
        %{--<label for="transcriptErrors">--}%
            %{--<g:message code="computerTranscript.transcriptErrors.label" default="Transcript Errors"/>--}%

        %{--</label>--}%
        %{--<g:textArea name="transcriptErrors" value="${computerTranscriptInstance?.transcriptErrors}"/>--}%

    %{--</div>--}%

    %{--<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'passageErrors', 'error')} ">--}%
        %{--<label for="passageErrors">--}%
            %{--<g:message code="computerTranscript.passageErrors.label" default="Passage Errors"/>--}%

        %{--</label>--}%
        %{--<g:textArea name="passageErrors" value="${computerTranscriptInstance?.passageErrors}"/>--}%

    %{--</div>--}%

</g:if>
<g:else>
    <g:hiddenField name="status" value="${edu.uoregon.secondlook.TranscriptionStatus.RECEIVED}"/>
    %{--<g:hiddenField name="requestDate" value="${new Date().toLocaleString()}"/>--}%
</g:else>

<div class="fieldcontain ${hasErrors(bean: computerTranscriptInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="computerTranscript.note.label" default="Note"/>

    </label>
    <g:textArea name="note" value="${computerTranscriptInstance?.note}"/>

</div>


