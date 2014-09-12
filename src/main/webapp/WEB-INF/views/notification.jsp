<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<portlet:actionURL name="notificationSubmit" var="formSubmitAction"/>


<form:form method="post" action="${formSubmitAction}" class="form-horizontal" >
    <fieldset>
        <!-- Form Name -->
        <legend><spring:message code="notification.title"/></legend>
        
        <!-- Select Basic -->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="" for="selectbasic"><spring:message code="notification.recipientType"/></form:label>
            <div class="col-md-6">
                <form:select path="recipientType" class="col-md-4 form-control" id="recipientType">
                    <option value="token">token</option>
                    <option value="logins"><spring:message code="notification.login"/></option>
                    <option value="groups"><spring:message code="notification.group"/></option>
                    <option value="broadcast"><spring:message code="notification.broadcast"/></option>
                </form:select>
            </div>
        </div>
        
        
        <!-- Text input-->
        <div class="form-group" id="recipient">
            <form:label class="col-md-4 control-label" path="recipient" for="recipient"><spring:message code="notification.recipient"/></form:label>  
            <div class="col-md-6">
                <form:input id="recipientinput" path="recipient" name="recipient" placeholder="mbelmokh" class="form-control input-md" required="requis"/>
            </div>
        </div>
        
        <!-- Textarea -->
        
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="message" for="messageInput"><spring:message code="notification.message"/></form:label>
            <div class="col-md-6">
                <form:textarea path="message" name="message" placeholder="Hello world !" class="form-control" required="requis"/></div>
        </div>
        
        <!-- Select Basic -->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="" for="courrielSelect"><spring:message code="notification.mail"/></form:label>
            <div class="col-md-6">
                <form:select path="mail" name="courrielSelect" class="form-control" id="mail">
                    <option value="false"><spring:message code="notification.nomail"/></option>
                    <option value="true"><spring:message code="notification.withmail"/></option>
                </form:select>
            </div>
        </div>
        
        
        <div class="form-group hidden" id="object">
            <form:label class="col-md-4 control-label" path="" for="object"><spring:message code="notification.object"/></form:label>  
            <div class="col-md-6" id="object">
                <form:input path="object" name="object" placeholder="Object" class="form-control input-md" />
            </div>
        </div>
        
        
        <!-- Button (Double) -->
        <div class="form-group">
            <div class="col-md-12" style="text-align: center;">
                <button id="button1id" name="button1id" class="btn btn-primary"><spring:message code="notification.submit"/> <span class="glyphicon glyphicon-send"></span></button>
                <button id="button2id" name="button2id" type="reset" class="btn btn-danger"><spring:message code="notification.reset"/> <span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
    </fieldset>
</form:form>

<c:if test="${not empty submit}">
    
    <div class="alert alert-success alert-dismissible" role="alert" style="text-align: center;">
        <button type="button" class="close" data-dismiss="alert">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        Notification has been send successfully !
    </div>
</c:if>

<script>
    
    
    $("#recipientType").change(function() {
        
        if($("#recipientType " ).val() == "broadcast"){
            $('#recipient').addClass('hidden');
            $('#recipientinput').removeAttr('required');
        } else {
            $('#recipient').removeClass('hidden');
            $('#recipientinput').attr('required', 'requis');   
        }
    });
    
    $("#mail").change(function() {
        
        if($("#mail").val() == "false"){
            $('#object').addClass('hidden');
        } else {
            $('#object').removeClass('hidden');
        }
    });
    
    $(function() {
        var availableTags = [
            "ActionScript",
            "AppleScript",
            "C",
            "C++",
            "Groovy",
            "Java",
            "JavaScript",
            "Perl",
            "PHP",
            "Python"
        ];
        $( "#recipientinput" ).autocomplete({
            source: availableTags
        });
    });
</script>

