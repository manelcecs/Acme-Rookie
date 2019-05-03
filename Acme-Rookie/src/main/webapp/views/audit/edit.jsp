<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:button code="audit.edit.cancel" type="button" url="/audit/auditor/list.do"/>

<form:form modelAttribute="auditForm" action="audit/auditor/save.do">
   	 <acme:hidden path="id"/>
   	 <acme:hidden path="version"/>
   	 
   	 <acme:select items="${positions}" itemLabel="title" code="audit.edit.position" path="position"/>
  
   	 <p>
   		 <acme:textarea code="audit.edit.text" path="text"/>
   	 </p>
   	 <p>
   		 <acme:inputNumber code="audit.edit.score" path="score"/>
   	 </p>
   	 <p>
   	 	<acme:checkbox code="audit.edit.draft" path="draft"/>
   	 </p>
   	  	 
   	 
   	 <acme:submit name="save" code="audit.edit.save"/>
    </form:form>
