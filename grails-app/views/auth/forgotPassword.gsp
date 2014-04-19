<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
  <title>Reset Password</title>
</head>
<body>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <g:form action="resetPassword">
    %{--<input type="hidden" name="targetUri" value="${targetUri}" />--}%
    <table>
      <tbody>
        <tr>
          <td>Username:</td>
          <td><input type="text" name="username" value="${username}" size="30"/></td>
        </tr>
        <tr>
          <td />
          <td><input type="submit" value="Reset Password" /> &nbsp;
          </td>
        </tr>
      </tbody>
    </table>
  </g:form>
</body>
</html>
