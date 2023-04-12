<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misPartidos">

    <h2>Mis partidos</h2>
    <div class="row">
        <c:if test="${numPartidos == 0}">
            Aún no se ha publicado ningún partido.
        </c:if>
        <c:if test="${numPartidos != 0}">
            <c:forEach items="${partidos.content}" var="partido">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">                  
                            <p class="card-text">
                                <strong>Jugadores:</strong> <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                              </p>                              
                            <p class="card-text">
                                <strong>Centro:</strong> <c:out value="${partido.centro.nombre}"/> 
                            </p>
                            <p class="card-text">
                                <strong>Tipo:</strong> <c:out value="${partido.tipo}"/>
                            </p>
                            <p class="card-text">
                                <strong>Fecha de la actividad:</strong> <c:out value="${partido.getFechaParseada()}"/>
                            </p>
                            <br>
                            <div class="text-center">
                            <a href="/partidos/${partido.id}" class="btn btn-default">Ver</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <div class="row" style="width: 100%;">
        <div class="col-md-6 text-left">
            <c:if test="${numPartidos != 0}">
            <c:if test="${partidos.hasPrevious()}">
                <c:url var="previousPageUrl" value="/partidos">
                    <c:param name="sexo" value="${param.sexo}" />
                    <c:param name="tipo" value="${param.tipo}" />
                    <c:param name="page" value="${partidos.number - 1}" />
                </c:url>
                <a href="${previousPageUrl}" class="previous">
                    <button class="btn btn-default">
                        Anterior
                    </button>
                </a>
    
            </c:if>
            <c:if test="${!partidos.isLast()}">
                <c:url var="nextPageUrl" value="/partidos">
                    <c:param name="sexo" value="${param.sexo}" />
                    <c:param name="tipo" value="${param.tipo}" />
                    <c:param name="page" value="${partidos.number + 1}" />
                </c:url>
                <a href="${nextPageUrl}" class="next">
                    <button class="btn btn-default">
                        Siguiente
                    </button>
                </a>
            </c:if>
            <p>Página ${partidos.number + 1} de ${partidos.totalPages}</p>
        </c:if>
        </div>
        <div class="col-md-6 text-right">
            <a href="/partidos/new" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Nuevo partido
            </a>
        </div>
    </div>  
</petclinic:layout>

<style>
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