<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>


<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/jquery-2.1.1/jquery-2.1.1.min.js"></script>
<link type="text/css" href=" ${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/rs/bootstrap-3.2.0/js/bootstrap.min.js"></script>


<h1>Home page</h1>

<br>
<!--<p>The time on the server is ${serverTime}.</p>-->
<br>
<portlet:renderURL var="editLink" portletMode="edit"/>
<portlet:renderURL var="notifFormLink">
        <portlet:param name="action" value="notificationForm"/>
</portlet:renderURL>





<a class="btn btn-default" href="${editLink}">
    <i class="glyphicon glyphicon-cog"></i> Set parameters
</a>
<a class="btn btn-default" href="${notifFormLink}">
    <i class="glyphicon glyphicon-edit"></i> Write notification
</a>
