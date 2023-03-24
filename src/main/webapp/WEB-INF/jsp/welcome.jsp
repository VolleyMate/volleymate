<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <style>
        body {
            background-image: url('/resources/images/Inicio4.png');
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
            color: #FFFFFF;
        }.contenedor{
            display: flex;
        }.texto{
            width: 300px;
            height: 300px;
            padding: 10px; 
            margin: 10px;  
            float: right; 
        }
    </style>
    <div class="contenedor">
        <spring:url value="/resources/images/VolleyMateInicio.png" htmlEscape="true" var="VolleyMateInicio"/>
        <img class="img-responsive" src="${fn:escapeXml(VolleyMateInicio)}" alt="VolleyMateInicio"/>
        <spring:url value="/resources/images/pivotaltxt.png" htmlEscape="true" var="pivotaltxt"/>
        <img class="texto" src="${fn:escapeXml(pivotaltxt)}" alt="pivotaltxt"/>
    </div>
</petclinic:layout>
