<g:render template="/shared/readonlyHeader" />

<!-- BEGIN MAIN CONTENT -->

            <g:layoutBody />

<!-- END CONTENT -->
  </div>
  <!-- END WRAP (from header) -->


  <!-- BEGIN FOOTER -->
<div id="footer">
    <div class="container">
        <div class="row">
            <div class="col-sm-4 nav-left" >
                <sec:ifLoggedIn>
                    <a class="text-muted feedback" id="feedback" >Feedback</a>
                </sec:ifLoggedIn>
            </div>
            <div class="col-sm-4"><p class="text-muted">Model Catalogue &copy; 2014&nbsp&nbspv<g:meta name="app.version"/> </p> </div>
            <div class="col-sm-4"></div>
        </div>
    </div>
</div>
<!-- END FOOTER -->
<asset:javascript src="jiraIssueTracker.js"></asset:javascript>

<r:layoutResources />
</body>
</html>
