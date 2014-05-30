<!DOCTYPE html>
<html lang="en">
<head><meta name="layout" content="main"/>
    <title>Model Catalogue - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>


		<!-- Jumbotron -->
		<div class="jumbotron">
			<h1>Model Catalogue Browser</h1>
			<p class="lead">
				<b><em>Model</em></b> existing business processes and context. <br><b><em>Browse</em></b>
				conceptual domains, models, data elements and data types. <b>
			</p>

            <sec:ifNotLoggedIn>
			    <g:link controller="login" action="auth" class="btn btn-large btn-primary">Login</g:link>
			    <span class="lead">&nbsp;&nbsp;or&nbsp;&nbsp;</span>
			    <g:link controller="register" action="index" class="btn btn-large btn-primary">Sign Up</g:link>
            </sec:ifNotLoggedIn>
				
		</div>

		<!-- Example row of columns -->
		<div id="info" class="row">
			<div class="col-sm-4">
				<h2>Metadata Elements</h2>
				<p>Browse Conceptual Domains, Models, Data Elements and Data Types.
                View their details and specifications</p>
				<p>
					<a href="#">More info&hellip;</a>
				</p>
			</div>
            <div class="col-sm-4">
				<h2>Search</h2>
				<p>Search for Conceptual Domains, Models, Data Elements and Data Types.</p>
				<p>
					<a href="#">More info&hellip;</a>
				</p>
			</div>
            <div class="col-sm-4">
				<h2>Export</h2>
				<p>Export latest version of metadata in Excel and XML format.</p>
				<p>
					<a href="#">More info&hellip;</a>
				</p>
			</div>
		</div>

</body>