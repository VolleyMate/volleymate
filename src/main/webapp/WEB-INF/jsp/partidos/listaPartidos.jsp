<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="partidos">
    <h2>Partidos creados</h2>

    <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
        <thead>
            <tr>
                <th style="width: 150px; text-align: center;">Creador</th>
                <th style="width: 120px; text-align: center;">Jugadores</th>
                <th style="width: 120px; text-align: center;">Lugar</th>
                <th style="width: 120px; text-align: center;">Fecha de la actividad</th>
                <th style="width: 120px; text-align: center;">Fecha de creación</th>
                <th style="width: 60px;"></th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach items="${partidos}" var="partido">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <c:out value="${partido.creador.user.username}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.lugar}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.getFechaParseada()}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.getFechaCreacionParseada()}"/>
                    </td>
                    <td style="text-align: center;">
                        <spring:url value="/partidos/${partido.id}" var="verURL"></spring:url>
                        <a href="${verURL}" class="btn btn-default">Ver</a>
                    </td> 
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>
    
      
</petclinic:layout>
