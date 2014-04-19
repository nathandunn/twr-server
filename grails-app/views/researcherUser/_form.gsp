<%@ page import="edu.uoregon.secondlook.ResearcherUser" %>


<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="researcher.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${researcherUserInstance?.username}" size="30"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="researcher.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${researcherUserInstance?.name}" size="30"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'password1', 'error')} ">
    <label for="lastName">
        <g:message code="researcher.password.label" default="Password" />

    </label>
    <g:passwordField name="password1"/>
    %{--<g:textField name="lastName" value="${researcherUserInstance?.password1}"/>--}%
</div>

<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'password2', 'error')} ">
    <label for="lastName">
        <g:message code="researcher.password-repeat.label" default="Repeat Password"/>

    </label>
    <g:passwordField name="password2"/>
    %{--<g:textField name="lastName" value="${researcherUserInstance?.password1}"/>--}%
</div>


<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'roles', 'error')} ">
    <label for="roles">
        <g:message code="researcher.roles.label" default="Roles"/>
    </label>

    <g:if test="${researcherUserInstance.username == org.apache.shiro.SecurityUtils.subject.principal}">
        ${researcherUserInstance.roles*.name}
    </g:if>
    <g:else>
        <select name="roles" id="roles">
            <g:each in="${edu.uoregon.secondlook.ResearcherRole.list()}" var="role">
            %{--<option value="2" selected="selected">ROLE_USER</option>--}%
            %{--<option value="1" selected="selected">ROLE_ADMINISTRATOR</option>--}%

                <option value="${role.id}"
                    <g:if test="${role.id in researcherUserInstance?.roles*.id}">
                        selected="selected"
                    </g:if>>${role.name}</option>
            </g:each>
        </select>
    </g:else>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'username', 'error')} required">--}%
	%{--<label for="username">--}%
		%{--<g:message code="researcherUser.username.label" default="Username" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:field type="email" name="username" required="" value="${researcherUserInstance?.username}"/>--}%

%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'name', 'error')} required">--}%
	%{--<label for="name">--}%
		%{--<g:message code="researcherUser.name.label" default="Name" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:textField name="name" required="" value="${researcherUserInstance?.name}"/>--}%

%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'passwordHash', 'error')} required">--}%
	%{--<label for="passwordHash">--}%
		%{--<g:message code="researcherUser.passwordHash.label" default="Password Hash" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:textField name="passwordHash" required="" value="${researcherUserInstance?.passwordHash}"/>--}%

%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'permissions', 'error')} ">--}%
	%{--<label for="permissions">--}%
		%{--<g:message code="researcherUser.permissions.label" default="Permissions" />--}%
		%{----}%
	%{--</label>--}%
	%{----}%

%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: researcherUserInstance, field: 'roles', 'error')} ">--}%
	%{--<label for="roles">--}%
		%{--<g:message code="researcherUser.roles.label" default="Roles" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:select name="roles" from="${edu.uoregon.secondlook.ResearcherRole.list()}" multiple="multiple" optionKey="id" size="5" value="${researcherUserInstance?.roles*.id}" class="many-to-many"/>--}%

%{--</div>--}%

