<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <div class="row">
        <spring:url value="/resources/images/VolleyMate.jpeg" htmlEscape="true" var="logoVolleyMate"/>
        <img class="img-responsive" src="${logoVolleyMate}" style="display: block; margin: auto;" alt="logoInicio"/>
    </div>
</petclinic:layout>
