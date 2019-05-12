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
			if (patt.test(str) == false) { return confirm("<spring:message code="auditor.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="auditor/auditor/save.do"
		modelAttribute="auditor">
		<acme:textbox code="auditor.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="auditor.edit.surnames" /></form:label>
    	<div id="surnames">
    		<jstl:forEach items="${auditor.surnames }" var="surname">
    			<form:input class="textbox" path="surnames" type="text" value="${ surname}"/>  
    		</jstl:forEach>  
   		 </div>					
    	<form:errors path="surnames" cssClass="error" />  
		
		<acme:textbox code="auditor.edit.photoURL" path="photo" />
		<acme:textbox code="auditor.edit.address" path="address" />
		<acme:textbox code="auditor.edit.vat" path="vatNumber" />
		<acme:textbox code="auditor.edit.email" path="email" />
		<acme:textbox code="auditor.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="auditor.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="auditor.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="auditor.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="auditor.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="auditor.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="auditor.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<spring:message code="auditor.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}"/>
		<acme:cancel url="/actor/display.do" code="auditor.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="auditor.edit.surnames.add" /></button>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="auditor/administrator/save.do"
		modelAttribute="auditorForm">
		<acme:textbox code="auditor.edit.username"
			path="userAccount.username" />
		<acme:password code="auditor.edit.password"
			path="userAccount.password" />
		<acme:password code="auditor.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="auditor.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="auditor.edit.surnames" /></form:label>
		<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		
		<acme:textbox code="auditor.edit.photoURL" path="photo" />
		<acme:textbox code="auditor.edit.address" path="address" />
		<acme:textbox code="auditor.edit.vat" path="vatNumber" />
		<acme:textbox code="auditor.edit.email" path="email" />
		<acme:textbox code="auditor.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="auditor.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="auditor.edit.creditcard.make" path="creditCard.make"/>
		<acme:inputNumber code="auditor.edit.creditcard.number" path="creditCard.number"/>
		<acme:inputNumber code="auditor.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="auditor.edit.creditcard.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="auditor.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="auditor.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		
		<spring:message code="auditor.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}"/>
		<acme:cancel url="/" code="auditor.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="auditor.edit.surnames.add" /></button>
</jstl:if>