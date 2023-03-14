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
        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">150 volleys
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
<h3 style="color: #FFFFFF;">Localización: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.lugar}"/></h3>
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
    <div style=" margin: 0 auto;">

    <c:if test="${!partido.creador.equals(jugadorLogueado)}">
        <c:if test="${!estaDentro}">
            <spring:url value="/jugadores/solicitudes/${partido.id}" var="enviarSolicitudUrl"></spring:url>
            <a href="${fn:escapeXml(enviarSolicitudUrl)}">
                <p class="btn btn-success">Enviar solicitud</p>
            </a>
        </c:if>
    </c:if>

    


</div>

</div>
<div style="width: 70%; margin: 0 auto; border-radius: 50px;  text-align: center; display: flex; flex-wrap: wrap;">
    <table id="partidosTable" class="table" summary="listadoPartidos">
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
                        
                        <c:if test="${jugador.image == ''}"><img class="rounded d-block" src="/resources/images/perfilPorDefecto.jpg" width="50" height="50"></c:if>
                        <c:if test="${jugador.image != ''}"><img class="rounded d-block" src="${jugador.image}" width="50" height="50"></c:if>
                        
                        <spring:url value="/jugadores/${jugador.id}" var="verJugadorURL"></spring:url>
                        <a href="${verJugadorURL}" class="btn" style="color: black;">[<c:out value="${jugador.user.username}"/>]</a>
                        <c:out value="${jugador.firstName}"/><c:out value=" "/><c:out value="${jugador.lastName}"/>
                    </td>
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</petclinic:layout>
