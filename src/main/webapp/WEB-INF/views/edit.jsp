<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/tooltip.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/popover.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />

<portlet:actionURL name="editSubmit" var="formSubmitAction"/>

<form:form id="editForm" method="post" action="${formSubmitAction}" class="form-horizontal">
    <fieldset>
        
         <!--Form Name--> 
        <legend>Application settings</legend>
        
         <!--Text input-->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="serverURL" for="serverURL">Server URL</form:label>  
            <div class="col-md-6">
                <form:input path="serverURL" name="serverURL" placeholder="http://localhost:8080/ag-push" class="form-control input-md" required="requis" type="url"/>
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="URL to connect to the Unified Push Serveur."><i class="glyphicon glyphicon-info-sign"></i></button>
        </div>
        
         <!--Text input-->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="applicationID" for="applicationId">Application ID</form:label>
            <div class="col-md-6">
                <form:input path="applicationID" name="applicationID" placeholder="c7fc6525-5506-4ca9-9cf1-55cc261ddb9c" class="form-control input-md" required="requis" onChange="scanApplicationID()"/>
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="Each application registered in Unified Push Server has an identifier called Application ID. Not to be confused with Variant ID."><i class="glyphicon glyphicon-info-sign"></i></button>

        </div>
        
         <!--Text input-->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="masterSecret" for="masterSecret">Master Secret</form:label>
            <div class="col-md-6">
                <form:input path="masterSecret" name="masterSecret" placeholder="8b2f43a9-23c8-44fe-bee9-d6b0af9e316b" class="form-control input-md" required="requis" onChange="scanMasterSecret()"/>
            </div>
            <button type="button" class="btn btn-default btn-xs" data-container="body" data-toggle="popover" data-placement="right" data-content="Each application registered in Unified Push Server has an second identifier called Master Secret. Not to be confused with Secret."><i class="glyphicon glyphicon-info-sign"></i></button>
        </div>
        
         <!--Button--> 
        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton"></label>
            <div class="col-md-4">
                <button id="submitButton" name="submitButton" class="btn btn-primary"><span class="glyphicon glyphicon-save"></span> Save</button>
            </div>
        </div>
    </fieldset>
</form:form>


<script type="text/javascript">
    $('button').popover();
    
    function scanApplicationID(){
     console.log('Application ID changed');
    }
    
    function scanMasterSecret() {
        console.log('Master secret changed');
    }
    
</script>