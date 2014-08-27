<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>


<portlet:actionURL name="notificationSubmit" var="formSubmitAction"/>


<form:form method="post" action="${formSubmitAction}" class="form-horizontal" >
    <fieldset>
        <!-- Form Name -->
        <legend>Esup Push Notification Portlet</legend>
        
        <!-- Text input-->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="recipient" for="recipient">Recipient</form:label>  
            <div class="col-md-4">
                <form:input path="recipient" name="recipient" placeholder="mbelmokh" class="form-control input-md" required="requis"/>
            </div>
        </div>
        
        <!-- Textarea -->
        
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="message" for="messageInput">Message</form:label>
            <div class="col-md-4">
                <form:textarea path="message" name="message" placeholder="Hello world !" class="form-control" required="requis"/></div>
        </div>
        
        <!-- Button (Double) -->
        <div class="form-group">
            <div class="col-md-4">
                <button id="button1id" name="button1id" class="btn btn-primary">Send <span class="glyphicon glyphicon-send"></span></button>
                <button id="button2id" name="button2id" type="reset" class="btn btn-primary">Reset <span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
    </fieldset>
</form:form>
    
    
<c:if test="${not empty submit}">
    
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        Notification has been send successfully !
    </div>
</c:if>