<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<security:authorize access="hasRole('AUDITOR')">
		<acme:button url="audit/auditor/edit.do?idAudit=${audit.id}" type="button" code="audit.display.edit"/>
</security:authorize>
		<acme:button code="audit.display.back" type="button" url="/audit/auditor/list.do"/>

<acme:text label="audit.display.position.title" value="${audit.position.title}"/>
<acme:text label="audit.display.moment" value="${audit.moment}"/>
<acme:text label="audit.display.score" value="${audit.score}/10"/>
<acme:text label="audit.display.text" value="${audit.text}"/>

<jstl:if test="${audit.draft}">
<p><spring:message code="audit.display.draft"/></p>
</jstl:if>
<jstl:if test="${!audit.draft}">
<p><spring:message code="audit.display.final"/></p>

</jstl:if>











