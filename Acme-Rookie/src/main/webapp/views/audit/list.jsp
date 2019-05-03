<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:button url="audit/auditor/create.do" type="button" code="audit.list.create"/>

<display:table pagesize="5" name="audits" id="audit" requestURI="${requestURI}">
   		 <display:column titleKey="audit.list.position.title"><jstl:out value="${audit.position.title}"/></display:column>
   		 <display:column titleKey="audit.list.score"><jstl:out value="${audit.score}"/>/10</display:column>
   		 <display:column titleKey="audit.list.moment"><jstl:out value="${audit.moment}"/></display:column>

   			 <!-- Columna de see more -->
   			 <display:column titleKey="audit.list.display">
   				 <acme:button url="audit/auditor/display.do?idAudit=${audit.id}" type="button" code="audit.list.display"/>
   			 </display:column>


   			 <!-- Columna de edit -->
   			 <display:column titleKey="audit.list.edit">
   				 <jstl:if test="${audit.draft}">
   					 <acme:button url="audit/auditor/edit.do?idAudit=${audit.id}" type="button" code="audit.list.edit"/>
   				 </jstl:if>
   			 </display:column>

   			 <!-- Columna de delete-->
   			 <display:column titleKey="audit.list.delete">
   				 <jstl:if test="${audit.draft}">
   					 <acme:button url="audit/auditor/delete.do?idAudit=${audit.id}" type="button" code="audit.list.delete"/>
   				 </jstl:if>
   			 </display:column>
</display:table>
