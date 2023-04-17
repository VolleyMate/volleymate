<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<petclinic:layout pageName="detalleJugador">


        <div class="m-0 row justify-content-center col-auto text-center"> 
            <c:if test="${jugadorVista.premium == true}"><img class="rounded d-block" style="padding-top: 2%;" src="/resources/images/corona.png" width="100" height="100" alt="jugador"></c:if>

    <div class="col-auto p-5 text-center">
        
        <c:if test="${jugadorVista.image == ''}"><img class="rounded d-block" src="/resources/images/perfilPorDefecto.png" width="250" height="250" alt="jugador"></c:if>
        <c:if test="${jugadorVista.image != ''}"><img class="rounded d-block" src="${jugadorVista.image}" width="250" height="250" alt="jugador"></c:if>
       
</br>
</br>
        <h1><c:out value="${jugadorVista.firstName}"/><c:out value="  "/><c:out value="${jugadorVista.lastName}"/></h1>
        <h3><b>@<c:out value="${jugadorVista.user.username}"/></b></h3>
        <h4>Ciudad: <b><c:out value="${jugadorVista.ciudad}"/></b></h4>
        <h4>Sexo: <b><c:out value="${jugadorVista.sexo}"/></b></h4>
        <h4>Premium: <b><c:out value="${jugadorVista.premium}"/></b></h4>
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

                <spring:url value="/misAspectos" var="aspURL"> <!--Cambiamos la URL cuando esté-->
                </spring:url>
                <a href="${aspURL}" class="btn btn-default"><span class="glyphicon glyphicon-eye-open"></span>  Mis aspectos</a>

                <sec:authorize access="hasAuthority('admin')">
                    <spring:url value="/jugadores/volleys/añadir/{id}" var="añadirURL">
                    <spring:param name="id" value="${id}" />
                </spring:url>
                <a href="${añadirURL}" class="btn btn-default">Añadir volleys [ADMIN]</a>
                </sec:authorize>
                
            </c:if>
        
        </tr>
    </table>

</petclinic:layout>