<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" >
<head>

    <meta name="layout" content="metadata_curation">
    <title>Metadata Curation </title>

    <asset:stylesheet href="metaDataCurator.css"/>
</head>

<body>
<div id="metadataCurator" ng-app="metadataCurator" style="width: 100%">

        <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand"  href="${createLink(uri: '/')}">Model Catalogue</a>
            </div>


            <div class="navbar-collapse collapse" id="mainNavbar">

                <ul class="nav navbar-nav">

                    <li><a  href="${createLink(uri: '/metadataCurator')}">Home</a></li>

                    <li class="dropdown" ui-sref-active="active">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" id ="catalogueElementLink">Catalogue Elements<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li ui-sref-active="active"><a id="conceptualDomainLink" ui-sref="mc.resource.list({resource: 'conceptualDomain', page:'1'})">Conceptual Domains</a></li>
                            <li ui-sref-active="active"><a id="dataElementLink" ui-sref="mc.resource.list({resource: 'dataElement', page:'1'})">Data Elements</a></li>
                            <li ui-sref-active="active"><a id="dataTypeLink" ui-sref="mc.resource.list({resource: 'dataType', page:'1'})">Data Types</a></li>
                            <li ui-sref-active="active"><a id="modelLink" ui-sref="mc.resource.list({resource: 'model', page:'1'})">Models</a></li>
                            <li ui-sref-active="active"><a id="valueDomainLink" ui-sref="mc.resource.list({resource: 'valueDomain', page:'1'})">Value Domains</a></li>
                        </ul>
                    </li>
                    <sec:ifAnyGranted roles="ROLE_READONLY_ADMIN">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="administrationLink">Administration <b  class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li class="dropdown-header">Roles</li>
                                <li><g:link controller="role" action='search'>Search roles</g:link></li>
                                <li><g:link controller="role" action='create'>Create role</g:link></li>
                                <li class="dropdown-header">Users</li>
                                <li><g:link controller="user" action='search'>Search users</g:link></li>
                                <li><g:link controller="user" action='create'>Create user</g:link></li>
                                <li><g:link controller="registrationCode" action='search'><g:message code="spring.security.ui.menu.registrationCode"/></g:link></li>
                                <li><g:link controller="role" action='listPendingUsers'>Activate pending users</g:link></li>
                            </ul>
                        </li>
                    </sec:ifAnyGranted>

                    <sec:ifNotGranted roles="ROLE_READONLY_ADMIN">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="accountLink">Account <b  class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="changePasswordLink"><g:link controller="register" action='changePassword' >Change Password</g:link></li>
                            </ul>
                        </li>
                    </sec:ifNotGranted>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <form class="navbar-form navbar-right navbar-input-group" role="search" autocomplete="off" ng-submit="search()" ng-controller="metadataCurator.searchCtrl">
                            <div class="form-group">
                                <input ng-model="searchSelect" type="text" name="search-term" id="search-term" placeholder="Search" catalogue-element-picker typeahead-on-select="search()">
                            </div>
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                        </form>
                    </li>
                    <li><g:link data-placement="bottom" class="btn btn-inverse" data-original-title="Logout" rel="tooltip" controller="logout">Logout</g:link></li>
                </ul>

            </div><!--/.nav-collapse -->
        </div>
    </div>

        <div class="container">
            <div class="row">
                <messages-panel max="3"></messages-panel>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <ui-view></ui-view>
                </div>
            </div>
        </div>
</div>

<asset:javascript src="angular/metaDataCurator.js"/>
<script type="text/javascript">
    angular.module('demo.config', ['mc.core.modelCatalogueApiRoot']).value('modelCatalogueApiRoot', '${request.contextPath ?: ''}/api/modelCatalogue/core')
</script>
</body>
</html>