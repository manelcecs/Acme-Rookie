<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<script type='text/javascript'>
	function checkPhone(str) {
		if (str != "") {
			var patt = new RegExp("^(\[+][1-9][0-9]{0,2}[ ]{1}\[(][1-9][0-9]{0,2}\[)][ ]{1}[0-9]{4,}|\[+][1-9][0-9]{0,2}[ ]{1}[0-9]{4,}|[0-9]{4,}|[ ]{1,})$");
			if (patt.test(str) == false) { return confirm("<spring:message code="rookie.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="rookie/rookie/save.do"
		modelAttribute="rookie">
		<acme:textbox code="rookie.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="rookie.edit.surnames" /></form:label>
    	<div id="surnames">
    		<jstl:forEach items="${rookie.surnames }" var="surname">
    			<form:input class="textbox" path="surnames" type="text" value="${ surname}"/>  
    		</jstl:forEach>  
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="rookie.edit.photoURL" path="photo" />
		<acme:textbox code="rookie.edit.address" path="address" />
		<acme:textbox code="rookie.edit.vat" path="vatNumber" />
		<acme:textbox code="rookie.edit.email" path="email" />
		<acme:textbox code="rookie.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="rookie.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="rookie.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="rookie.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="rookie.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="rookie.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="rookie.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<spring:message code="rookie.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}" />
		<acme:cancel url="/actor/display.do" code="rookie.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="rookie.edit.surnames.add" /></button>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="rookie/save.do"
		modelAttribute="rookieForm">
		<acme:textbox code="rookie.edit.username"
			path="userAccount.username" />
		<acme:password code="rookie.edit.password"
			path="userAccount.password" />
		<acme:password code="rookie.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="rookie.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="rookie.edit.surnames" /></form:label>
    	<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="rookie.edit.photoURL" path="photo" />
		<acme:textbox code="rookie.edit.address" path="address" />
		<acme:textbox code="rookie.edit.vat" path="vatNumber" />
		<acme:textbox code="rookie.edit.email" path="email" />
		<acme:textbox code="rookie.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="rookie.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="rookie.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="rookie.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="rookie.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="rookie.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="rookie.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="rookie.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<spring:message code="rookie.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}"/>
		<acme:cancel url="/" code="rookie.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="rookie.edit.surnames.add" /></button>
</jstl:if>