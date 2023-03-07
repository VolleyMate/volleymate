<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="partidos">
    <h2>Partidos</h2>

    <table id="partidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Creador</th>
            <th>numJugadoresNecesarios</th>
            <th>Lugar</th>
            <th style="width: 120px">Fecha actividad</th>
            <th style="width: 120px">Fecha creación</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidos}" var="partido">
            <tr>
                <td>
                    <c:out value="${partido.creador.user.username}"/></a>
                </td>
                <td>
                    <c:out value="${partido.numJugadoresNecesarios}"/>
                </td>
                <td>
                    <c:out value="${partido.lugar}"/>
                </td>
                <td>
                    <c:out value="${partido.getFechaParseada()}"/>
                </td>
                <td>
                    <c:out value="${partido.getFechaCreacionParseada()}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
