<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('PROVIDER')"><acme:button code="item.display.back" type="button" url="/item/provider/list.do"/></security:authorize>
<security:authorize access="not hasRole('PROVIDER')"><acme:button code="item.display.back" type="button" url="/item/list.do"/></security:authorize>
<acme:text label="item.display.name" value="${item.name}"/>
<acme:text label="item.display.description" value="${item.description}"/>

<p><strong><spring:message code="item.display.pictures" />:</strong> </p> 
<ul>
	<jstl:forEach var="picture" items="${item.pictures}">
		
		<li><a href="<jstl:out value="${picture}"/>" target="_blank"><jstl:out value="${picture}"/></a></li>
		<br/>
		<img  alt="" src="${picture}">
		<br/>
		<br/>
	</jstl:forEach>
</ul>

<p><strong><spring:message code="item.display.links" />:</strong> </p> 
<ul>
	<jstl:forEach var="link" items="${item.links}">
	<li><a href="<jstl:out value="${link}"/>" target="_blank"><jstl:out value="${link}"/></a></li>
	</jstl:forEach>
</ul>


