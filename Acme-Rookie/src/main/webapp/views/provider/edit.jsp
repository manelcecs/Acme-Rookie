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
			if (patt.test(str) == false) { return confirm("<spring:message code="provider.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="provider/provider/save.do"
		modelAttribute="provider">
		<acme:textbox code="provider.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="provider.edit.surnames" /></form:label>
    	<div id="surnames">
    		<jstl:forEach items="${provider.surnames }" var="surname">
    			<form:input class="textbox" path="surnames" type="text" value="${ surname}"/>  
    		</jstl:forEach>  
   		 </div>					
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="provider.edit.providerMake" path="providerMake" />
		<acme:textbox code="provider.edit.photoURL" path="photo" />
		<acme:textbox code="provider.edit.address" path="address" />
		<acme:textbox code="provider.edit.vat" path="vatNumber" />
		<acme:textbox code="provider.edit.email" path="email" />
		<acme:textbox code="provider.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="provider.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="provider.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="provider.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="provider.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="provider.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="provider.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<spring:message code="provider.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}" />
		<acme:cancel url="/actor/display.do" code="provider.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="provider.edit.surnames.add" /></button>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="provider/save.do"
		modelAttribute="providerForm">
		<acme:textbox code="provider.edit.username"
			path="userAccount.username" />
		<acme:password code="provider.edit.password"
			path="userAccount.password" />
		<acme:password code="provider.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="provider.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="provider.edit.surnames" /></form:label>
    	<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="provider.edit.providerMake" path="providerMake" />
		<acme:textbox code="provider.edit.photoURL" path="photo" />
		<acme:textbox code="provider.edit.address" path="address" />
		<acme:textbox code="provider.edit.vat" path="vatNumber" />
		<acme:textbox code="provider.edit.email" path="email" />
		<acme:textbox code="provider.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="provider.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="provider.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="provider.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="provider.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="provider.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="provider.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="provider.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<spring:message code="provider.edit.submit" var="submit"/>
		<input type="submit" name="submit"
			onclick="return checkPhone(this.form.phone.value)" value="${ submit}" />
		<acme:cancel url="/" code="provider.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="provider.edit.surnames.add" /></button>
</jstl:if>