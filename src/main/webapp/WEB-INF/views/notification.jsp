<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>


<portlet:actionURL name="notificationSubmit" var="formSubmitAction"/>


<c:if test="${not empty submit}">
    <h1>ON A BIEN ENVOYER LE FORMULAIRE
</c:if>

<form:form action="${formSubmitAction}" method="post" class="form-horizontal" >
    <fieldset>
        <!-- Form Name -->
        <legend>Esup Push Notification Portlet</legend>
        
        <!-- Text input-->
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="recipient" for="recipient">Recipient</form:label>  
            <div class="col-md-4">
                <form:input path="recipient" name="recipient" placeholder="mbelmokh" class="form-control input-md" required=""/>
            </div>
        </div>
        
        <!-- Textarea -->
        
        <div class="form-group">
            <form:label class="col-md-4 control-label" path="message" for="messageInput">Message</form:label>
            <div class="col-md-4">
                <form:textarea path="message" name="message" placeholder="Hello world !" class="form-control"/></div>
        </div>
        
        <!-- Button -->
        
        <div class="form-group">
            <div class="col-md-4">
                <input type="submit" value="Register" class="btn btn-primary" value="Send">
            </div>
        </div>
    </fieldset>
</form:form>