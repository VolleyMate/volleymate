<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="jugadores">
    <h2><c:out value="${jugador.firstName} ${jugador.lastName}"/></h2>
    <h3><c:out value="${jugador.user.username}"/></h3>


    <table class="table table-striped">
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${jugador.telephone}"/></td>
        </tr>
        <tr>
            <th>Correo electrónico</th>
            <td><c:out value="${jugador.user.correo}"/></td>
        </tr>
        <tr>
            <th>Ciudad</th>
            <td><c:out value="${jugador.ciudad}"/></td>
        </tr>
    </table>
    <a href="" class="btn btn-default">Partidos pasados</a>
    
    <h2>Partidos activos</h2>
    <table class="table table-striped">
        <c:forEach var="partido" items="${jugador.partidos}">
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
</petclinic:layout>