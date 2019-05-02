<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<section id="main">

	<section id="displaySponsorship">
	
		<h3><spring:message code="sponsorship.display.info"/></h3>
		<hr>	
		
		
		<div>
		
	 	<strong><spring:message code="sponsorship.display.targetPageURL"/></strong> <a target="_blank" href="${sponsorship.targetPageURL}"> <jstl:out value="${sponsorship.targetPageURL}"></jstl:out></a>
		<p><strong><spring:message code="sponsorship.display.flatRateApplied"/></strong> <jstl:out value="${sponsorship.flatRateApplied}"></jstl:out> | <strong><spring:message code="sponsorship.display.flatRateAppliedWithVAT"/></strong> <jstl:out value="${flatRateAppliedWithVAT}"></jstl:out> </p>
		<p><strong><spring:message code="sponsorship.display.banner"/></strong></p>
		<img style="width: 100%;"  src="${sponsorship.bannerURL}"/>
		
		</div>

		
		<hr>
		
	
 
	</section>
	
	<section id="displayPositions">
	
	
		<h3><spring:message code="sponsorship.display.creditCard"/></h3>
		<hr>	
		
		
		<div>
			<p><strong><spring:message code="sponsorship.display.holder"/></strong>: <jstl:out value="${sponsorship.creditCard.holder}"></jstl:out></p>
			<p><strong><spring:message code="sponsorship.display.make"/></strong>: <jstl:out value="${sponsorship.creditCard.make}"></jstl:out></p>
			<p><strong><spring:message code="sponsorship.display.number"/></strong>: <jstl:out value="${sponsorship.creditCard.number}"></jstl:out></p>
			<p><strong><spring:message code="sponsorship.display.expirationMonth"/></strong>: <jstl:out value="${sponsorship.creditCard.expirationMonth}"></jstl:out></p>
			<p><strong><spring:message code="sponsorship.display.expirationYear"/></strong>: <jstl:out value="${sponsorship.creditCard.expirationYear}"></jstl:out></p>	
			<p><strong><spring:message code="sponsorship.display.cvv"/></strong>: <jstl:out value="${sponsorship.creditCard.cvv}"></jstl:out></p>
		</div>
			
		<hr>
		<br>	
		
	
		<h3><spring:message code="sponsorship.display.positions"/></h3>
		<hr>
		
		<display:table pagesize="5" name="sponsorship.positions" id="position" requestURI="${requestURI}">
				<display:column titleKey="sponsorship.display.positionTitle"><jstl:out value="${position.title}"/></display:column>
	   		 	 <display:column titleKey="sponsorship.display.positionDisplay"><acme:button url="position/display.do?idPosition=${position.id}" type="button" code="position.list.seeMore"/>
	   			 </display:column>
		</display:table>
		
		<hr>
		
		<div class="botones" ><acme:cancel url="sponsorship/provider/list.do" code="sponsorship.display.back" /></div>	
	
	</section>
	
	
		



<hr>
</section>



<style>
 #main {
	float: left;
	width: 100%
}
 #main > hr{
	float: left;
	margin-top: 50px;
	width: 100%;
}

#displaySponsorship {
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}

#displayPositions {
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}


 .botones{
  	margin-left: 70px;
  	float: right;
}

.botones > button{
	margin-left: 10px;
}


/*
#displaySponsorship>p {
	margin: 15px 20px 5px 20px;
	float: left;
}

#displaySponsorship>p.right {
	margin: 15px 20px 5px 20px;
	float: right;
}

#displaySponsorship>hr {
	float: left;
	width: 100%;
}

#displaySponsorship {
	float: left;
	padding: 20px 50px;
	margin: 20px;
	width: 45%;
	border: 1px solid black;
}



#main>hr{
width: 100%;
float: left;
margin-top: 50px;
} */
</style>

