<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="petsImage"/>
    <img src="${petsImage}" style="max-width:25%;"/>

    <h2>Vaya... parece que ha habido un problema</h2>

    <p>${exception.message}</p>

</petclinic:layout>
