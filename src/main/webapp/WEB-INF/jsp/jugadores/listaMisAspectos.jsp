<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="listaMisAspectos">

<h2>Mis aspectos</h2>
<div class="row">
    <c:if test="${aspectos.size() == 0}">
        No tienes aún ningún aspecto.
    </c:if>
    <c:if test="${aspectos.size()  != 0}">
        <c:forEach items="${aspectos}" var="aspecto">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body square">
                        <spring:url value="/jugadores/setAspecto/{aspectoId}" var="setURL">
                            <spring:param name="aspectoId" value="${aspecto.id}" />
                        </spring:url>
                        <a href="${setURL}" class="btn btn-default">
                            <img src="${aspecto.imagen}" alt="Aspecto">
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>
</petclinic:layout>
<style>
    .square {
        padding-bottom: 100%; /* Establece la altura igual al ancho */
        position: relative;
    }
    .square img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%; /* Establece el ancho al 100% para llenar el contenedor */
        height: auto;
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
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }
    .card:hover {
        transform: scale(1.1);
        cursor: pointer;
        background-color: #e6f4f2;
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

