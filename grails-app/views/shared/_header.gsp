<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <g:javascript>
    window.appContext = '${request.contextPath}';
    var root = location.protocol + '//' + location.host + window.appContext;
    </g:javascript>


    <r:require modules="application"/>




    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"/>
<![endif]-->

    <g:set var="activeNavItem" value="${pageProperty(name: 'page.name')}"/>

    <g:layoutHead/>
    <r:layoutResources/>

    <asset:stylesheet href="datatables/media/css/jquery.dataTables.css"/>

    <asset:stylesheet href="angular-xeditable/dist/css/xeditable.css"/>
    <asset:stylesheet href="application.css"/>
</head>

<body>

%{--<g:render template="/pathway/createPathwayModal"/>--}%
%{--<g:render template="/formDesign/createFormModal"/>--}%
<!-- BEGIN WRAP. We use the wrap to enable a sticky footer element-->
<div id="wrap">
    <nav class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNavbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${createLink(uri: '/')}">Model Catalogue</a>
            </div>

            <div class="navbar-collapse collapse" id="mainNavbar">
                <ul class="nav navbar-nav">
                    <sec:ifLoggedIn>

                        <li><a href="${createLink(uri: '/')}">Home</a></li>
                        <li><a href="${createLink(uri: '/metadataCurator')}">Browser</a></li>

                        <sec:ifAnyGranted roles="ROLE_READONLY_ADMIN">
                            <!-- Admin menu -->
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Administration <b
                                        class="caret"></b></a>
                                <ul class="dropdown-menu">

                                    <li class="dropdown-header">Roles</li>
                                    <li><g:link controller="role" action='search'>Search roles</g:link></li>
                                    <li><g:link controller="role" action='create'>Create role</g:link></li>
                                    <li class="dropdown-header">Users</li>
                                    <li><g:link controller="user" action='search'>Search users</g:link></li>
                                    <li><g:link controller="user" action='create'>Create user</g:link></li>
                                    <li><g:link controller="registrationCode" action='search'><g:message
                                            code="spring.security.ui.menu.registrationCode"/></g:link></li>
                                    <li><g:link controller="role"
                                                action='listPendingUsers'>Activate pending users</g:link></li>
                                </ul>
                            </li>
                            </ul>
                        </sec:ifAnyGranted>
                        <ul class="nav navbar-nav navbar-right">
                            <li><g:link data-placement="bottom" class="btn btn-inverse" data-original-title="Logout"
                                        rel="tooltip" controller="logout">Logout</g:link></li>
                        </ul>
                    </sec:ifLoggedIn>
                 </ul>
            </div>
        </div>
    </nav>