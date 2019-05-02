<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" name="providers" id="provider" requestURI="${requestURI}">
			<display:column titleKey="provider.list.name"><jstl:out value="${provider.name}"/></display:column>
			<display:column titleKey="provider.list.surnames">
				<jstl:forEach var="surname" items="${provider.surnames}">
					<jstl:out value="${surname} "/>
				</jstl:forEach>
			</display:column>
			<display:column titleKey="provider.list.make"><jstl:out value="${provider.providerMake}"/></display:column>
		
				<display:column titleKey="provider.list.seeMore">
					<acme:button url="item/provider/display.do?idItem=${item.id}" type="button" code="provider.list.seeMore"/>
				</display:column>

				<display:column titleKey="provider.list.items">
						<acme:button url="item/list.do?idProvider=${provider.id}" type="button" code="provider.list.items"/>
				</display:column>
</display:table>