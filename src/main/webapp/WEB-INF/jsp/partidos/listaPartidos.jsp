<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="partidos">
    <h2>Partidos disponibles</h2>
    <form action="/partidos" method="get">
        <div class="col-md-12 text-center" style="justify-content: center;">
            <a href="/jugadores/mispartidos" class="btn btn-default">
                <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Mis partidos
            </a>
        </div>
        <br>
        <br>
        <br>
        <div style="display: grid; grid-template-columns: repeat(4,1fr); padding-bottom: 3%;">
            <div style="grid-column: 1;">
                <div style="text-align: left;">
                    <label for="sexo">Sexo:</label>
                </div>
                <select class="btn btn-default" id="sexo" name="sexo">
                    <option value="">Cualquiera</option>
                    <option value="MIXTO">Mixto</option>
                    <option value="MASCULINO">Masculino</option>
                    <option value="FEMENINO">Femenino</option>
                </select>
            </div>
            <br>
            <div style="grid-column: 2; grid-row: 1; align-items: center;">
                <div style="text-align: left;">
                    <label for="tipo">Tipo de partido:</label>
                </div>
                <select class="btn btn-default" id="tipo" name="tipo">
                    <option value="">Cualquiera</option>
                    <option value="VOLEIBOL">Voleibol</option>
                    <option value="FUTVOLEI">Futbolei</option>
                    <option value="BOSABALL">Bosaball</option>
                    <option value="SUBMARINO">Submarino</option>
                    <option value="SENTADO">Sentado</option>
                    <option value="PLAYA">Playa</option>
                    <option value="WATERVOLEY">Watervoley</option>
                </select>
            </div>
            <div style="grid-column: 4; grid-row: 1;">
                <div style="text-align: left;">
                    <label for=""></label>
                </div>
                <button class="btn btn-default" type="submit">Filtrar</button>
            </div> 
        </div>
    </form>

    <div class="row">
        <c:if test="${numPartidos == 0}">
            Aún no se ha publicado ningún partido.
        </c:if>
        <c:if test="${numPartidos != 0}">
            <c:forEach items="${partidos.content}" var="partido">
                <div class="col-md-4">
                    <a href="/partidos/${partido.id}" class="card-link">
                    <div class="card">
                        <div class="card-body">
                            <a href="/jugadores/${partido.creador.id}"><h5 class="card-title" style="font-weight: bold;">${partido.creador.user.username}</h5></a> 

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
