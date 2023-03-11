<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="detalleJugador">
    <h2><c:out value="${jugadorVista.firstName} ${jugadorVista.lastName}"/></h2>
    <h3><c:out value="${jugadorVista.user.username}"/></h3>


    <table class="table table-striped" summary="tabla para ver los datos del jugador">
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${jugadorVista.telephone}"/></td>
        </tr>
        <tr>
            <th>Correo electrónico</th>
            <td><c:out value="${jugadorVista.user.correo}"/></td>
        </tr>
        <tr>
            <th>Ciudad</th>
            <td><c:out value="${jugadorVista.ciudad}"/></td>
        </tr>
    </table>
    
    <h2>Partidos</h2>
    <table class="table table-striped" summary="tabla para ver los partidos del jugador">
        <c:forEach var="partido" items="${jugadorVista.partidos}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Fecha</dt>
                        <dd><c:out value="${partido.fecha}"/></dd>
                        <dt>Lugar</dt>
                        <dd><c:out value="${partido.lugar}"/></dd>
                        <dt>Número de jugadores necesarios</dt>
                        <dd><c:out value="${partido.numJugadoresNecesarios}"/></dd>
                    </dl>
                </td>
        </c:forEach>
    </table>

    <a href="" class="btn btn-default">Partidos pasados</a>

    <c:if test="${!jugadorVista.equals(jugadorAutenticado)}">
        <a href="" class="btn btn-default">Reportar jugador</a>
    </c:if>
</petclinic:layout>