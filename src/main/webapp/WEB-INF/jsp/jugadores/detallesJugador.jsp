<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<petclinic:layout pageName="detalleJugador">
    <c:if test="${not empty jugadorConPartidos}">
        <div id="mensaje" class="mensaje">
            <span class="cerrar" onclick="cerrarMensaje()">&times;</span>
            <c:out value="${jugadorConPartidos}"/>
          </div>
    </c:if>
    <c:if test="${mensajeError!=null}">
            <div style="background-color: red;">
                <c:out value="${mensajeError}" ></c:out>
            </div>
    </c:if>

        <div class="m-0 row justify-content-center col-auto text-center"> 
            <c:if test="${jugadorVista.premium == true}"><img class="rounded d-block" style="padding-top: 2%;" src="/resources/images/corona.png" width="100" height="100" alt="jugador"></c:if>

    <div class="col-auto p-5 text-center">
        
        <img class="rounded d-block" src="${jugadorVista.image}" width="250" height="250" alt="jugador">
       
</br>
</br>
        <h1><c:out value="${jugadorVista.firstName}"/><c:out value="  "/><c:out value="${jugadorVista.lastName}"/></h1>
        <h3><b>@<c:out value="${jugadorVista.user.username}"/></b></h3>
        <c:if test="${jugadorVista.premium == true}"><h4>Premium</h4></c:if>
        <c:if test="${jugadorVista.premium == false}"><h4>No premium</h4></c:if>
        <h4>Ciudad: <b><c:out value="${jugadorVista.ciudad}"/></b></h4>
        <h4>Sexo: <b><c:out value="${jugadorVista.sexo}"/></b></h4>
        <h4>Valoración Media: <b><c:out value= "${jugadorVista.getValoracionMedia()}"/></b></h4>
        <c:if test="${jugadorVista.equals(jugadorAutenticado)}">
            <h4>Teléfono: <b><c:out value="${jugadorVista.telephone}"/></b></h4>
            <h4><b><c:out value="${jugadorVista.volleys}"/></b><c:out value=" "/><img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;"></h4>
        </c:if>
    </br>
    </br>
    
        <tr>
            <c:if test="${!jugadorVista.equals(jugadorAutenticado)}">
        
                <c:if test="${!yaValorado}">
        
                    <spring:url value="/valoraciones/new/{id}" var="valorarURL">
                        <spring:param name="id" value="${valorarId}" />
                    </spring:url>
                    <a href="${valorarURL}" class="btn btn-default">Valorar jugador</a>
        
                </c:if>
        
                <spring:url value="/valoraciones/{id}" var="valURL">
                    <spring:param name="id" value="${valorarId}" />
                </spring:url>
                <a href="${valURL}" class="btn btn-default">Valoraciones de
                    <c:out value="${jugadorVista.user.username}" />
                </a>
                <spring:url value="/logro/player/${jugadorVista.id}" var="LogURL">
                </spring:url>
                <a href="${LogURL}" class="btn btn-default"><span class="glyphicon glyphicon-certificate"></span> Ver logros</a>

                
                 <sec:authorize access="hasAuthority('admin')">
                    <spring:url value="/jugadores/volleys/añadir/{id}" var="añadirURL">
                    <spring:param name="id" value="${valorarId}" />
                </spring:url>
                <a href="${añadirURL}" class="btn btn-default">Añadir volleys [ADMIN]</a>
                </sec:authorize>


            </c:if>

            
        
        </tr>
        
        <tr>
            <c:if test="${jugadorVista.equals(jugadorAutenticado)}">
        
            <div>
                <spring:url value="/jugadores/edit/{id}" var="editUrl">
                    <spring:param name="id" value="${id}" />
                </spring:url>
                <a href="${editUrl}" class="btn btn-green"><span class="glyphicon glyphicon-pencil" style="border-radius: 20px;"></span>  Editar perfil</a>
            </div>
            <br>
                <spring:url value="/jugadores/mispartidos" var="partidasURL"></spring:url>
                <a href="${partidasURL}" class="btn btn-default"><span class="glyphicon glyphicon-th-list"></span>  Mis partidos</a> 
        
                <spring:url value="/valoraciones/{id}" var="valURL">
                    <spring:param name="id" value="${id}" />
                </spring:url>
                <a href="${valURL}" class="btn btn-default"><span class="glyphicon glyphicon-star"></span>  Mis valoraciones</a>

                <spring:url value="/logro" var="LogURL">
                </spring:url>
                <a href="${LogURL}" class="btn btn-default"><span class="glyphicon glyphicon-certificate"></span> Ver logros</a>
            
                <spring:url value="/misAspectos" var="aspURL"> <!--Cambiamos la URL cuando esté-->
                </spring:url>
                <a href="${aspURL}" class="btn btn-default"><span class="glyphicon glyphicon-eye-open"></span> Mis aspectos</a>

                <sec:authorize access="hasAuthority('admin')">
                    <spring:url value="/jugadores/volleys/añadir/{id}" var="añadirURL">
                    <spring:param name="id" value="${id}" />
                </spring:url>
                <a href="${añadirURL}" class="btn btn-default">Añadir volleys [ADMIN]</a>
                </sec:authorize>
                
            </c:if>

            <c:if test="${admin || jugadorVista.id == jugadorAutenticado.id}">
                <spring:url value="/jugadores/delete/{id}" var="eliminarURL">
                    <spring:param name="id" value="${jugadorVista.id}" />
                </spring:url>
                <a href="${eliminarURL}" class="btn btn-danger"><span class="glyphicon glyphicon-star"></span>  Eliminar cuenta</a>
            </c:if>
            </tr>
    </table>

</petclinic:layout>


<style>
    .mensaje {
  position: relative;
  padding: 0.5em 1em;
  margin-bottom: 1em;
  background-color: #eee;
  border: 1px solid #ccc;
}

.cerrar {
  position: absolute;
  top: 0;
  right: 0;
  padding: 0.5em;
  cursor: pointer;
}

</style>

<script>
    function cerrarMensaje() {
  document.getElementById("mensaje").style.display = "none";
}

</script>