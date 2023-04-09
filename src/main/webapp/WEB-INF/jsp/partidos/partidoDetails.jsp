<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<h1>Detalles del partido:</h1>
<div style="width: 70%; background-color: #0099bc; margin: 0 auto; border-radius: 50px;  text-align: center; display: flex; flex-wrap: wrap;">
   
    <div style="width: 45%;">
        <h3 style="color: #FFFFFF;">Nombre: </h3>
<h3  style="background-color: white;border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto; ">  <c:out value="${partido.nombre}"></c:out>   </h3>
<h3></h3>
<h3 style="color: #FFFFFF;">Fecha: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.getFechaParseada()}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;">Sexo: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.sexo}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;"> Precio:</h3>
    <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;"><c:out value="${partido.precioPersona}"/> volleys
        <h3></h3>
    </div>
    <div style="width: 45%;">
<h3 style="color: #FFFFFF;">Descripción:</h3>
<h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
    <c:out value="${partido.descripcion}"/>
</h3>
<h3></h3>
<h3 style="color: #FFFFFF;"> Tipo: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
     <c:out value="${partido.tipo}"/></h3>
     <h3></h3>
    <h3 style="color: #FFFFFF;">Ciudad: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;"> 
    <a href="${partido.centro.maps}"><c:out value="${partido.centro.nombre}"/> [<c:out value="${partido.centro.direccion}"/>, <c:out value="${partido.centro.ciudad}"/>]</h3></a>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Creador: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.creador.user.username}"/></h3>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Número jugadores: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/></h3>
    <h3></h3>
    </div>
    
    <div style=" margin: 0 auto; padding-top: 2%; padding-bottom: 5%;">
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
                        <spring:url value="/jugadores/solicitudes/${partido.id}" var="enviarSolicitudUrl"></spring:url>
                        <a href="${fn:escapeXml(enviarSolicitudUrl)}"><p class="btn btn-success">Enviar solicitud</p></a>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${estaDentro}">
                <spring:url value="/partidos/salir/${partidoId}" var="salirURL">
                    <spring:param value="${partido.id}" name="partidoId"/>
                </spring:url>
                <a href="${fn:escapeXml(salirURL)}" class="btn btn-warning">Salir de partido (no se devuelven los volleys)</a>
            </c:if>
        </c:if>
    </div>

</div>


<div style="text-align: center; padding-top: 3%;">
    
    <spring:url value="/jugadores/partido/${partidoId}" var="jugURL">
        <spring:param value="${partido.id}" name="partidoId"/>
    </spring:url>
    <a href="${fn:escapeXml(jugURL)}" class="btn btn-default">Ver jugadores</a>
    
    <c:if test="${estaDentro}">
        <spring:url value="/chat/{partidoId}" var="chat">
            <spring:param value="${partido.id}" name="partidoId"/>
        </spring:url>
        <a href="${fn:escapeXml(chat)}" class="btn btn-default">Chat</a>
    </c:if>

    <sec:authorize access="hasAuthority('admin')">
        <spring:url value="/partidos/eliminar/{partidoId}" var="eliminarURL">
            <spring:param value="${partido.id}" name="partidoId"/>
        </spring:url>
        <a href="${eliminarURL}" class="btn btn-danger">Eliminar partido [ADMIN]</a>
    </sec:authorize>


    
</div>

</petclinic:layout>
