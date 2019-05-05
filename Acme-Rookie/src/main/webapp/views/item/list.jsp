<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${provider}">
	<acme:button code="item.list.create" type="button"  url="item/provider/create.do"/>
</jstl:if>
<display:table pagesize="5" name="items" id="item" requestURI="${requestURI}">
			<display:column titleKey="item.list.name"><jstl:out value="${item.name}"/></display:column>
			<display:column titleKey="item.list.description"><jstl:out value="${item.description}"/></display:column>
		
			<jstl:if test="${provider}">
				<display:column titleKey="item.list.seeMore">
					<acme:button url="item/provider/display.do?idItem=${item.id}" type="button" code="item.list.seeMore"/>
				</display:column>

				<display:column titleKey="item.list.edit">
						<acme:button url="item/provider/edit.do?idItem=${item.id}" type="button" code="item.list.edit"/>
				</display:column>

				<display:column titleKey="item.list.delete">
						<acme:button url="item/provider/delete.do?idItem=${item.id}" type="button" code="item.list.delete"/>
				</display:column>
				
			</jstl:if>
			<jstl:if test="${viewAll}">
				<display:column titleKey="item.list.seeMore">
					<acme:button url="item/display.do?idItem=${item.id}" type="button" code="item.list.seeMore"/>
				</display:column>
				<jstl:if test="${idProvider == null}">
					<display:column titleKey="item.list.providerMake">
							<jstl:out value="${item.provider.providerMake}"/>
					</display:column>
¡				<display:column titleKey="item.list.provider">
   					 <acme:button url="actor/displayProvider.do?providerId=${item.provider.id}" type="button" code="item.list.provider"/>
				</display:column>
				</jstl:if>				
			</jstl:if>
</display:table>