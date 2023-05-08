<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misNotificaciones">
    
    <c:if test="${mensajeError != null}">
        <div class="alert alert-danger alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeError}"/> de <c:out value="${jugadorSolicitud}"/>
        </div>
    </c:if>

    <c:if test="${mensajeExitoso != null}">
        <div class="alert alert-success alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeExitoso}"/> de <c:out value="${jugadorSolicitud}"/>
          </div>
    </c:if>

    <div class="row" style="font-size: large;">
        <h1>Mis notificaciones</h1>
            <c:if test="${solicitudesRecibidas.size() == 0}">
                No hay notificaciones todavía.
            </c:if>
            <c:if test="${solicitudesRecibidas.size() != 0}">
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
                                    <c:out value="${solicitud.jugador.user.username}"/>
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
            </c:if>
    </div>
    <div class="row" style="font-size: large;">
        <h1 style="margin-top: 5%;">Solicitudes pendientes</h1>
        <c:if test="${solicitudesPendientes.size() == 0}">
            No hay solicitudes pendientes.
        </c:if>
        <c:if test="${solicitudesPendientes.size() != 0}">
            <table id="solicitudesPendientesTable" class="table table-striped" summary="solicitudesPendientes">
                <thead>
                    <tr>
                        <th style="width: 120px; text-align: center;">Nombre del partido</th>
                        <th style="width: 120px; text-align: center;">Fecha</th>
                        <th style="width: 120px; text-align: center;">Centro</th>
                        <th style="width: 120px; text-align: center;">Creador</th>
                    </tr>
                    <tr style="height: 15px;"></tr>
                </thead>
                <tbody>
                    <c:forEach items="${solicitudesPendientes}" var="solicitud">
                        <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                            <td style="text-align: center;">
                                <c:out value="${solicitud.partido.nombre}"/>
                            </td>
                            <td style="text-align: center;">
                                <c:out value="${solicitud.partido.getFechaParseada()}"/>
                            </td>
                            <td style="text-align: center;">
                                <c:out value="${solicitud.partido.centro.nombre}"/>
                            </td>
                            <td style="text-align: center;">
                                <a href="/jugadores/${solicitud.partido.creador.id}">
                                    <c:out value="${solicitud.partido.creador.user.username}"/>
                                </a>
                            </td>   
                        </tr>
                        <tr style="height: 15px;"></tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</petclinic:layout>