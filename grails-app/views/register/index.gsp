<head>
    <meta name="layout" content="main">
    <title><g:message code='spring.security.ui.register.title'/></title>

    <asset:javascript src="jquery/dist/jquery.js"/>
    <asset:javascript src="jquery-ui/ui/jquery-ui.js"/>


</head>

<body>

<g:form action='register' name='registerForm' style="width:100%">

    <g:if test='${emailSent}'>

        <br/>
        <div class='alert alert-success'>
           <g:message code='spring.security.ui.register.sent'/>
           <br/>
            <a href="${createLink(uri: '/')}">Return to the homepage</a>
        </div>

    </g:if>
    <g:else>

        <br/>

        <table id="s2ui_registration">
            <tbody>

            <s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                               labelCodeDefault='Username' value="${command.username}"/>

            <s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
                               labelCode='user.email.label' labelCodeDefault='E-mail'/>

            <s2ui:textFieldRow name='firstName' labelCode='user.firstName.label' bean="${command}"
                               labelCodeDefault='First name' value="${command?.firstName}"/>
            <s2ui:textFieldRow name='lastName' labelCode='user.lastName.label' bean="${command}"
                               labelCodeDefault='Last name' value="${command?.lastName}"/>

            <s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                                   labelCodeDefault='Password' value="${command.password}"/>

            <s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                                   labelCodeDefault='Password (again)' value="${command.password2}"/>

            </tbody>
        </table>
        <g:submitButton class="btn btn-large btn-primary" name="Create your account" elementId='create'
                        form='registerForm' messageCode='spring.security.ui.register.submit'/>

    </g:else>

</g:form>



<script>
    $(document).ready(function () {
        $('#username').focus();
    });
</script>

</body>