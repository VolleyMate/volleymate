<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misPartidos">
    <h2>Mis Partidos</h2>
    <table class="table table-striped" summary="tabla para ver los partidos del jugador">
        <c:forEach var="partido" items="${partidos}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Fecha</dt>
                        <dd><c:out value="${partido.getFechaParseada()}"/></dd>
                        <dt>Lugar</dt>
                        <dd><c:out value="${partido.lugar}"/></dd>
                        <dt>Nº jug necesarios</dt>
                        <dd><c:out value="${partido.numJugadoresNecesarios}"/></dd>
                    </dl>
                  <td> <a href="" class="btn btn-default">Ver partido</a></td> 
                </td>
        </c:forEach>
    </table>
</petclinic:layout>