<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %--> 
<%@ page contentType="text/html; charset=UTF-8" %> 

<petclinic:layout pageName="home">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background-image: url('/resources/images/Inicio4.png');
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
            color: #FFFFFF; 
        }/*.contenedor{
            display: flex;
        }.texto{
            width: 450px;
            height: 300px;
            padding: 10px; 
            margin: 10px;  
            float: right; 
        } */

        .contenedor {
            display: flex;
            flex-wrap: wrap; /* Permite que los elementos se ajusten en varias filas */
            justify-content: center; /* Distribuye los elementos de manera uniforme en el contenedor */
            align-items: center; /* Centra los elementos verticalmente */
        }

        .texto {
            flex: 0 0 100%; /* Establece el ancho del elemento al 100% del contenedor */
            max-width: 400px; /* Limita el ancho máximo del elemento */
            height: auto; /* Permite que la altura se ajuste automáticamente */
            margin: 10px 0; /* Agrega un margen superior e inferior */
        }
        
    </style>
    <div class="contenedor">
        <spring:url value="/resources/images/VolleyMateInicio.png" htmlEscape="true" var="VolleyMateInicio"/>
        <img class="img-responsive" src="${fn:escapeXml(VolleyMateInicio)}" alt="VolleyMateInicio"/>
        <spring:url value="/resources/images/pivotaltxt.png" htmlEscape="true" var="pivotaltxt"/>
        <img class="texto" src="${fn:escapeXml(pivotaltxt)}" alt="pivotaltxt"/>
    </div>
</petclinic:layout>