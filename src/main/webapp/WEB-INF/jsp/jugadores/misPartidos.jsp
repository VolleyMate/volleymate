<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misPartidos">

    <div class="row">
        <h2>Mis Partidos</h2>
        <c:if test="${numPartidos == 0}">
            Aún no perteneces a ningún partido.
        </c:if>
        <c:if test="${numPartidos != 0}">
            <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
                <thead>
                    <tr>
                        <th style="width: 120px; text-align: center;">Título</th>
                        <th style="width: 120px; text-align: center;">Fecha de la actividad</th>
                        <th style="width: 120px; text-align: center;">Ciudad</th>
                        <th style="width: 120px; text-align: center;">Dirección</th>
                        <th style="width: 120px; text-align: center;">Jugadores</th>
                        <th style="width: 120px; text-align: center;"></th>
                        </tr>
                    <tr style="height: 15px;"></tr>
                </thead>
                <tbody>
                    <c:forEach items="${partidos.content}" var="partido">
                        <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                            <td style="text-align: center;">
                                <c:out value="${partido.nombre}"/>
                            </td>
                            <td style="text-align: center;">
                                <c:out value="${partido.getFechaParseada()}"/>
                            </td>
                            <td style="text-align: center;">
                                <c:out value="${partido.centro.nombre}"/>
                            </td>
                            <td style="text-align: center;">
                                <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                            </td>
                            <td style="text-align: center;">
                                <spring:url value="/partidos/${partido.id}" var="verURL"></spring:url>
                                <a href="${verURL}" class="btn btn-default">Ver</a>
                            </td> 
                        </tr>
                        <tr style="height: 15px;"></tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <div style="margin: 2%;" class="pagination">
        <c:if test="${partidos.hasPrevious()}">
          
            <c:url var="previousPageUrl" value="/jugadores/mispartidos">
                <c:param name="page" value="${partidos.number - 1}" />
            </c:url>
            <a href="${previousPageUrl}" class="previous">
                <button class="btn btn-default">
                    Anterior
                </button>
            </a>
    
        </c:if>
        <c:if test="${!partidos.isLast()}">
            <c:url var="nextPageUrl" value="/jugadores/mispartidos">
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