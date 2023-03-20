<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="detallePartido">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <c:if test="${mensajeError != null}">
        <div class="alert alert-danger">
            <c:out value="${mensajeError}"></c:out>
        </div>
    </c:if>
    <c:if test="${mensajeExitoso != null}">
        <div class="alert alert-success">
            <c:out value="${mensajeExitoso}"></c:out>
        </div>
    </c:if>

    <h1>Detalles del partido:</h1>
    <div class="container" style="background-color: #0099bc; border-radius: 50px; text-align: center; ">
        <div class="row">
            <div class="col-md-6 col-sm-12">
                <h3 style="color: #FFFFFF;">Nombre: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body" >
                        <c:out value="${partido.nombre}"></c:out>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;">Fecha: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.getFechaParseada()}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;">Sexo: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.sexo}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;"> Precio:</h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">150 volleys
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-12">
                <h3 style="color: #FFFFFF;">Descripción:</h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.descripcion}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;"> Tipo: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.tipo}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;">Localización: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.lugar}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;">Creador: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.creador.user.username}"/>
                    </div>
                </div>
                <h3 style="color: #FFFFFF;">Número jugadores: </h3>
                <div class="card" style="background-color: #FFFFFF; border-radius: 5px;">
                    <div class="card-body">
                        <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                    </div>
                    </div>
                    
                    
                    </div>
                    <div class="button-container" style="position: center; margin: 1rem;">
                        <c:if test="${!partido.creador.equals(jugadorLogueado)}">
                            <c:if test="${!estaDentro}">
                                <spring:url value="/jugadores/solicitudes/${partido.id}" var="enviarSolicitudUrl"></spring:url>
                                <a href="${fn:escapeXml(enviarSolicitudUrl)}">
                                    <p class="btn btn-success">Enviar solicitud</p>
                                </a>
                            </c:if>
                        </c:if>
                        <a href="/partidos/${partidoId}/jugadores">
                            <p class="btn btn-success">Ver jugadores</p>
                        </a>
                    </div>
                </div>
            
            
                
              
            </div> 
        </div>
    </petclinic:layout>