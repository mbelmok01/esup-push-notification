<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>


<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>

<p>The time on the server is ${serverTime}.</p>

<form class="form-horizontal">
    <fieldset>
        <!-- Form Name -->
        <legend>Esup Push Notification Portlet</legend>
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="recipientInput">Recipient</label>  
            <div class="col-md-4">
                <input id="recipientInput" name="recipientInput" type="text" placeholder="mbelmokh" class="form-control input-md" required="">
            </div>
        </div>
        
        <!-- Textarea -->
        
        <div class="form-group">
            <label class="col-md-4 control-label" for="messageInput">Message</label>
            <div class="col-md-4">
                <textarea class="form-control" id="messageInput" name="messageInput">Hello world !</textarea>
            </div>
        </div>
        
        <!-- Button -->
        
        <div class="form-group">
            <label class="col-md-4 control-label" for="submitButton"></label>
            <div class="col-md-4">
                <button id="submitButton" name="submitButton" class="btn btn-primary">Send</button>
            </div>
        </div>
    </fieldset>
</form>




<br>
<portlet:renderURL var="editLink" portletMode="edit"/>
<a href="${editLink}">Aller vers la page edit</a>