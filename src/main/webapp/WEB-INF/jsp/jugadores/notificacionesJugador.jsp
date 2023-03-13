<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misNotificaciones">
    <h2>Mis Notificaciones</h2>
    <table id="solicitudesRecibidasTable" class="table table-striped" summary="solicitudesRecibidas">
        <thead>
            <tr>
                <th style="width: 120px; text-align: center;">Nombre del partido</th>
                <th style="width: 120px; text-align: center;">Jugador que solicita unirse</th>
                <th style="width: 120px; text-align: center;"></th>
                <th style="width: 120px; text-align: center;"></th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach items="${solicitudesRecibidas}" var="solicitud">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <c:out value="${solicitud.partido.nombre}"/>
                    </td>
                    <td style="text-align: center;">
                        <a href="/jugadores/${solicitud.jugador.id}">
                            <c:out value="${solicitud.jugador.firstName}"/>
                        </a>
                    </td>
                    <td>
                        <spring:url value="/jugadores/solicitudes/aceptar/${solicitud.id}" var="aceptarSolicitudUrl"></spring:url>
                        <a href="${fn:escapeXml(aceptarSolicitudUrl)}">
                            <p class="btn btn-success">Aceptar solicitud</p>
                        </a>
                    </td>
                    <td>
                        <spring:url value="/jugadores/solicitudes/denegar/${solicitud.id}" var="denegarSolicitudUrl"></spring:url>
                        <a href="${fn:escapeXml(denegarSolicitudUrl)}">
                            <p class="btn btn-danger">Denegar solicitud</p>
                        </a>
                    </td>
                    
                   

                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>
    <table id="solicitudesPendientesTable" class="table table-striped" summary="solicitudesPendientes">
        <thead>
            <tr>
                <th style="width: 120px; text-align: center;">Nombre del partido</th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach items="${solicitudesPendientes}" var="solicitudP">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <a href="/partidos/${solicitudP.partido.id}">
                            <c:out value="${solicitudP.partido.nombre}"/>
                        </a>
                    </td>
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>
</petclinic:layout>