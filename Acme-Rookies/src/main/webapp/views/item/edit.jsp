<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="item" action="item/provider/edit.do">
		<acme:hidden path="id"/>
				
		<p>
			<acme:textbox code="item.edit.name" path="name"/>
		</p>
		<p>
			<acme:textarea code="item.edit.description" path="description"/>
		</p>
	
		<form:label class="textboxLabel" path="links"><spring:message code="item.edit.links" /></form:label>
   		<div id="links">
    	<jstl:if test="${empty item.links}">
        	<form:input class="textbox" path="links" type="text"/>        	
    	</jstl:if>
   
    	<jstl:forEach items="${item.links}" var="link">
        	<form:input class="textbox" path="links" type="text" value="${link}"/> 
    	</jstl:forEach> 
    	</div>
   		<form:errors path="links" cssClass="error" />
   		
   		<form:label class="textboxLabel" path="pictures"><spring:message code="item.edit.pictures" /></form:label>
   		<div id="pictures">
    	<jstl:if test="${empty item.pictures}">
        	<form:input class="textbox" path="pictures" type="text"/>        	
    	</jstl:if>
   
    	<jstl:forEach items="${item.pictures}" var="picture">
        	<form:input class="textbox" path="pictures" type="text" value="${picture}"/> 
    	</jstl:forEach> 
    	</div>
   		<form:errors path="pictures" cssClass="error" />
    	    			
		<acme:submit name="save" code="item.edit.save"/>
		<acme:cancel url="item/provider/list.do" code="item.edit.back"/>
	</form:form>
	
	<button class="addTag" onclick="addComment('links','links', 'textbox')"><spring:message code="item.edit.addLinks" /></button>
	<button class="addTag" onclick="addComment('pictures','pictures', 'textbox')"><spring:message code="item.edit.addPictures" /></button>
	
