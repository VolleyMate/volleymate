<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="detallePartido">
    <c:if test="${mensajeError != null}">
        <div style="background-color: red;">
            <c:out value="${mensajeError}"></c:out>
        </div>
    </c:if>
    <c:if test="${mensajeExitoso != null}">
        <div style="background-color: green;">
            <c:out value="${mensajeExitoso}"></c:out>
        </div>
    </c:if>

    <h1 style="margin: 20px 0;">Detalles del partido:</h1>
    
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
        <div class="container"> 
            <div class="row form-row">
                <div class="col-md-8">
                    <div class="col-md-5" style="margin: 25px;">
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <input type="text" style="border-radius: 20px;" class="form-control" value="${partido.nombre}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="ciudad">Fecha:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.getFechaParseada()}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Sexo:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.sexo}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Descripción:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.descripcion}" readonly>
                        </div>
                    </div>
                    <div class="col-md-5" style="margin: 25px;">
                        <div class="form-group">
                            <label for="direccion">Tipo:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.tipo}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Centro:</label>
                            <a href="${partido.centro.maps}"><input type="text" class="form-control" style="border-radius: 20px;" value="${partido.centro.nombre}" readonly></a>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Creador:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.creador.user.username}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="direccion">Número jugadores:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${partido.jugadores.size()} / ${partido.numJugadoresNecesarios}" readonly>
                        </div>
                    </div>
                    <div style="text-align: center;">
                        <label for="mapa">Precio:</label>
                        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys"
                            style="width: 20px; height: 20px; margin-right: 10px;">
                        <c:choose>
                            <c:when test="${jugadorLogueado.premium}">
                                <c:out value="Gratis con tu suscripción premium!" /> 
                            </c:when>
                            <c:otherwise>
                                <c:out value="${partido.precioPersona}" /> volleys
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <div style=" margin: 0 auto; padding-top: 2%; padding-bottom: 5%; align-items: center; justify-content: center; text-align: center;">
            <c:if test="${!partido.creador.equals(jugadorLogueado)}">
                <c:if test="${!estaDentro}">
                    <c:choose>
                        <c:when test="${estaEnEspera}">
                            <p class="btn btn-warning">Pendiente</p>
                        </c:when>
                        <c:when test="${partido.precioPersona > jugadorLogueado.volleys}">
                            <p class="btn btn-danger">No tienes los volleys necesarios</p>
                        </c:when>
                        <c:otherwise>
                            <spring:url value="/jugadores/solicitudes/${partido.id}" var="enviarSolicitudUrl">
                            </spring:url>
                            <a href="${fn:escapeXml(enviarSolicitudUrl)}">
                                <p class="btn btn-green" style="font-size: large;">Enviar solicitud</p>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${estaDentro}">
                    <spring:url value="/partidos/salir/${partidoId}" var="salirURL">
                        <spring:param value="${partido.id}" name="partidoId"/>
                    </spring:url>
                    <a href="${fn:escapeXml(salirURL)}" class="btn btn-warning">Salir de partido </br> (perderás los volleys)</a>
                </c:if>
            </c:if>
        </div>

   


        <div style="text-align: center; padding-top: 3%;">

            <spring:url value="/jugadores/partido/${partidoId}" var="jugURL">
                <spring:param value="${partido.id}" name="partidoId" />
            </spring:url>

            <a href="${fn:escapeXml(jugURL)}" class="btn btn-default">Ver jugadores</a>

            <c:if test="${estaDentro}">
                <spring:url value="/chat/{partidoId}" var="chat">
                    <spring:param value="${partido.id}" name="partidoId" />
                </spring:url>
                <a href="${fn:escapeXml(chat)}" class="btn btn-default">Chat</a>

            </c:if>
            
            <c:if test="${puedeEditar}">
                <spring:url value="/partidos/edit/{partidoId}" var="edit">
                    <spring:param value="${partido.id}" name="partidoId" />
                </spring:url>
                <a href="${fn:escapeXml(edit)}" class="btn btn-default">Editar partido</a>
            </c:if>

            <sec:authorize access="hasAuthority('admin')">
                <spring:url value="/partidos/eliminar/{partidoId}" var="eliminarURL">
                    <spring:param value="${partido.id}" name="partidoId"/>
                </spring:url>
                <a href="${eliminarURL}" class="btn btn-red">Eliminar partido [ADMIN]</a>
            </sec:authorize>
        </div>
    </div>
</petclinic:layout>

