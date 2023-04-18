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
        .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */
        border-radius: 10px;
        padding-top: 4%;
        padding-bottom: 4%;
        padding-left: 4%;
        padding-right: 4%;
        background-color: #e6f4f2;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
        }
        .card:hover {
        transform: scale(1.1);
        cursor: pointer;
        animation: pulse 1.5s infinite;
        }

        @keyframes pulse {
        0% {
            transform: scale(1);
        }
        50% {
            transform: scale(1.05);
        }
        100% {
            transform: scale(1);
        }
        }
    </style>
    <div class="contenedor">
        <div class="col-md-4 text-center">
            <div class="card">
                <div class="card-body">
                    <img class="img-responsive" src="/resources/images/qr.png" style="width: 30%; margin-left: 33.3%; margin-bottom: 2%;" alt="QR Landign Page"/> 
                    <h3>¡Disfruta aquí de nuestra
                        <a href="https://volleymate.vercel.app">LANDING PAGE!</a>
                    </h3>
                </div>
            </div>
        </div>
        <spring:url value="/resources/images/VolleyMateInicio.png" htmlEscape="true" var="VolleyMateInicio"/>
        <img class="img-responsive" src="${fn:escapeXml(VolleyMateInicio)}" alt="VolleyMateInicio"/>
        <spring:url value="/resources/images/pivotaltxt.png" htmlEscape="true" var="pivotaltxt"/>
        <img class="texto" src="${fn:escapeXml(pivotaltxt)}" alt="pivotaltxt"/>
        <spring:url value="/terminos" htmlEscape="true" var="terminos"/>
        <a href="${fn:escapeXml(terminos)}">Terminos y condiciones</a>
    </div>
</petclinic:layout>
