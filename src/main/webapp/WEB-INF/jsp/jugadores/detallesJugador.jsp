<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<petclinic:layout pageName="detalleJugador">

        <div class="m-0 row justify-content-center"> 

    <div class="col-auto p-5 text-center"> 
        <c:if test="${jugadorVista.image == ''}"><img class="rounded d-block" src="/resources/images/perfilPorDefecto.jpg" width="250" height="250"></c:if>
        <c:if test="${jugadorVista.image != ''}"><img class="rounded d-block" src="${jugadorVista.image}" width="250" height="250"></c:if>
       
</br>
</br>
        <h1><c:out value="${jugadorVista.firstName}"/><c:out value="  "/><c:out value="${jugadorVista.lastName}"/></h1>
        <h3><b>@<c:out value="${jugadorVista.user.username}"/></b></h3>
        <h4>Ciudad: <b><c:out value="${jugadorVista.ciudad}"/></b></h4>
        <h4>Teléfono: <b><c:out value="${jugadorVista.telephone}"/></b></h4>
        <h4>Sexo: <b><c:out value="${jugadorVista.sexo}"/></b></h4>
        <h4><b><c:out value="${jugadorVista.volleys}"/></b><c:out value=" "/><img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;"></h4>
    </br>
    </br>
        <tr>
            <c:if test="${jugadorVista.equals(jugadorAutenticado)}">
                <spring:url value="/jugadores/edit/{id}" var="editUrl">
                    <spring:param name="id" value="${id}"/>
                </spring:url>
                <a href="${editUrl}" class="btn btn-default">Editar Jugador</a>
            </c:if>
        
        </tr>
   
        <tr>
            <c:if test="${jugadorVista.equals(jugadorAutenticado)}">
                <spring:url value="/jugadores/mispartidos" var="partidasURL"></spring:url>
                <a href="${partidasURL}" class="btn btn-default">Mis partidos</a>
            </c:if>
        </tr>
    </table>

</petclinic:layout>