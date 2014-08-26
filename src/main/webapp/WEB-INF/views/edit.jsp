
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<!--<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-tooltip/1.3/jquery.tooltip.js"></script>-->
<!--<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/jquery-tooltip/1.3/jquery.tooltip.css" rel="stylesheet" />-->

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/tooltip.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/popover.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />



<form class="form-horizontal">
    <fieldset>
        
        <!-- Form Name -->
        <legend>Application settings</legend>
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="serveurUri">Server URL</label>
            <div class="col-md-6">
                <input id="serveurUri" name="serveurUri" type="text" placeholder="http://localhost:8080/ag-push" class="form-control input-md" required="">
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="URL to connect to the Unified Push Serveur.">info</button>
        </div>
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="applicationId">Application ID</label>
            <div class="col-md-6">
                <input id="applicationId" name="applicationId" type="text" placeholder="c7fc6525-5506-4ca9-9cf1-55cc261ddb9c" class="form-control input-md" required="">
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="Each application registered in Unified Push Server has an identifier called Application ID. Not to be confused with Variant ID.">info</button>

        </div>
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="masterSecret">Master Secret</label>
            <div class="col-md-6">
                <input id="masterSecret" name="masterSecret" type="text" placeholder="8b2f43a9-23c8-44fe-bee9-d6b0af9e316b" class="form-control input-md" required="">
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="Each application registered in Unified Push Server has an second identifier called Master Secret. Not to be confused with Secret.">info</button>
        </div>
        
        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="submitButton"></label>
            <div class="col-md-4">
                <button id="submitButton" name="submitButton" class="btn btn-primary"><span class="glyphicon glyphicon-save"></span> Save</button>
            </div>
        </div>
        
        
    </fieldset>
</form>


<script type="text/javascript">
    $('button').popover();

</script>