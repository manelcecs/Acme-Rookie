<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<section>

	<jstl:if test="${sponsorshipForm.id == 0}">
		<p><spring:message code="sponsorship.edit.fare" /> <jstl:out value="${flatRate}"/> | <jstl:out value="${flatRateWithVAT}"/> (<spring:message code="sponsorship.edit.withVAT" />) </p>
	</jstl:if>

	<form:form action="sponsorship/provider/edit.do" modelAttribute="sponsorshipForm">
		
		<acme:hidden path="id"/>
		
		<acme:textbox code="sponsorship.edit.targetPageURL" path="targetPageURL"/>
		<acme:textbox code="sponsorship.edit.bannerURL" path="bannerURL"/>
		
	
		<acme:select items="${posiblePositions}"  itemLabel="title" code="sponsorship.edit.positions" path="positions"/>
		
		
		<acme:textbox code="sponsorship.edit.holder" path="creditCard.holder"/>
		<acme:textbox code="sponsorship.edit.make" path="creditCard.make"/>
		<acme:inputNumber code="sponsorship.edit.number" path="creditCard.number"/>
		<acme:inputNumber code="sponsorship.edit.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:inputNumber code="sponsorship.edit.expirationYear" path="creditCard.expirationYear"/>
		<acme:inputNumber code="sponsorship.edit.cvv" path="creditCard.cvv"/>

		
		<acme:submit name="save" code="sponsorship.edit.save"/>
		<acme:cancel url="sponsorship/provider/list.do" code="sponsorship.edit.cancel"/>
		
	</form:form>

</section>