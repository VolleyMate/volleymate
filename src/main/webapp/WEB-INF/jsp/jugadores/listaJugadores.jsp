<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="listaJugadores">
    <!--
    <h2>Jugadores disponibles</h2>
    <form action="/listaJugadores" method="get">
        <div style="display: grid; grid-template-columns: repeat(4,1fr);">
            <div style="grid-column: 1;">
                <div style="text-align: left;">
                    <label for="sexo">Sexo:</label>
                </div>
                <select class="btn btn-default" id="sexo" name="sexo">
                    <option value="MASCULINO">Masculino</option>
                    <option value="FEMENINO">Femenino</option>
                </select>
            </div>
            <br>
            <div style="grid-column: 2; grid-row: 1; align-items: center;">
                <div style="text-align: left;">
                    <label for="ciudad">Ciudad:</label>
                </div>
                <input type="text" class="form-control" id="ciudad" name="ciudad" value="${param.ciudad}" />
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
-->
    <div class="row">
        <c:if test="${numJugadores == 0}">
            No se encuentra ningún jugador.
        </c:if>
        <c:if test="${numJugadores != 0}">
            <c:forEach items="${jugadores}" var="jugador">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <a href="/jugadores/${jugador.id}"><h5 class="card-title">${jugador.nombre}</h5></a>
                            <p class="card-text">
                                <strong>Usuario:</strong> <c:out value="${jugador.user.username}"/>
                            </p>
                            <p class="card-text">
                                <strong>Ciudad:</strong> <c:out value="${jugador.ciudad}"/>
                            </p>
                            <p class="card-text">
                                <strong>Sexo:</strong> <c:out value="${jugador.sexo}"/>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <!--
    <div class="pagination">
        <c:if test="${jugadores.hasPrevious()}">
            <c:url var="previousPageUrl" value="/jugadores">
                <c:param name="sexo" value="${param.sexo}" />
                <c:param name="ciudad" value="${param.ciudad}" />
                <c:param name="page" value="${currentPage - 1}" />
            </c:url>
            <a href="${previousPageUrl}" class="previous">
                <button class="btn btn-default">
                    Anterior
                </button>
            </a>
        </c:if>
        <c:if test="${!isLast}">
            <c:url var="nextPageUrl" value="/jugadores">
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
    -->
      
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
