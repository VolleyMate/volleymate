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
<h3  style="background-color: white;border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto; ">  <c:out value="${partido.nombre}"></c:out>   </h3>
<h3></h3>
<h3 style="color: #FFFFFF;">Fecha: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.fecha}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;">Sexo: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.sexo}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;"> Precio:</h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.precioPersona}"/></h3>
        <h3></h3>
    </div>
    <div style="width: 45%;">
<h3 style="color: #FFFFFF;">Descripción:</h3>
<h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
    <c:out value="${partido.descripcion}"/>
</h3>
<h3></h3>
<h3 style="color: #FFFFFF;"> Tipo: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
     <c:out value="${partido.tipo}"/></h3>
     <h3></h3>
<h3 style="color: #FFFFFF;">Localización: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.lugar}"/></h3>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Creador: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.creador.user.username}"/></h3>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Número jugadores: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/></h3>
    <h3></h3>
    </div>
    <div style=" margin: 0 auto;">

    <c:if test="${!partido.creador.equals(jugadorLogueado)}">
        <c:if test="${!estaDentro}">
            <spring:url value="/jugadores/solicitudes/${partido.id}" var="enviarSolicitudUrl"></spring:url>
            <a href="${fn:escapeXml(enviarSolicitudUrl)}">
                <p class="btn btn-success">Enviar solicitud</p>
            </a>
        </c:if>
    </c:if>

    <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
        <thead>
            <tr>
                <th style="width: 150px; text-align: center; background-color:#FFFFFF; color: black;">Jugadores:</th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach var="jugador" items="${partido.jugadores}">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <spring:url value="/jugadores/${jugador.id}" var="verJugadorURL"></spring:url>
                        <a href="${verJugadorURL}" class="btn" style="color: black;"><c:out value="${jugador.user.username}"/></a>
                    </td>
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>


</div>

</div>
</petclinic:layout>
