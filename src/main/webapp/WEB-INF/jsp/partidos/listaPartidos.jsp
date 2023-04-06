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
        <div style="display: grid; grid-template-columns: repeat(4,1fr);">
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
            <br>
            <br>
            <div style="grid-column: 4; grid-row: 1;">
                <div style="text-align: left;">
                    <label for=""></label>
                </div>
                <button class="btn btn-default" type="submit">Filtrar</button>
            </div> 
        </div>
    </form>
   

    <!-- <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
        <thead>
            <tr>
                <th style="width: 150px; text-align: center;">Creador</th>
                <th style="width: 120px; text-align: center;">Jugadores</th>
                <th style="width: 120px; text-align: center;">Dirección</th>
                <th style="width: 120px; text-align: center;">Tipo</th>
                <th style="width: 120px; text-align: center;">Fecha de la actividad</th>
                <th style="width: 120px; text-align: center;">Fecha de creación</th>
                <th style="width: 60px;"></th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach items="${partidos.content}" var="partido">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
            
                    <td style="text-align: center;">
                        <a style = "color: black;" href="/jugadores/${partido.creador.id}" class="btn" >${partido.creador.user.username}</a>
                    </td>
                    
                    <td style="text-align: center;">
                        <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.centro.nombre}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.tipo}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.getFechaCreacionParseada()}"/>
                    </td>
                    <td style="text-align: center;">
                        <spring:url value="/partidos/${partido.id}" var="verURL"></spring:url>
                        <a href="${verURL}" class="btn btn-default">Ver</a>
                    </td> 
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>  -->

    <div class="row">
        <c:forEach items="${partidos.content}" var="partido">
          <div class="col-md-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">${partido.creador.user.username}</h5>
                <p class="card-text">Jugadores: <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/></p>
                <p class="card-text">Dirección: <c:out value="${partido.centro.nombre}"/></p>
                <p class="card-text">Tipo: <c:out value="${partido.tipo}"/></p>
                <p class="card-text">Fecha de la actividad: <c:out value="${partido.getFechaParseada()}"/></p>
                <a href="/partidos/${partido.id}" class="btn btn-primary">Ver</a>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

    <div class="pagination">
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
      </div>
      
      <p>Página ${partidos.number + 1} de ${partidos.totalPages}</p>
    
      
</petclinic:layout>

<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */

}
</style>
