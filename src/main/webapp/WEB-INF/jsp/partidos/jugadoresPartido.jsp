<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="jugadoresPartido">






<div style="width: 70%; margin: 0 auto; border-radius: 50px;  text-align: center; display: flex; flex-wrap: wrap;">
    <table id="partidosTable" class="table" summary="listadoPartidos">
        <thead>
            <tr>
                <th style="width: 150px; text-align: center; background-color:#FFFFFF; color: black;">Jugadores:</th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach var="jugador" items="${jugadores}">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        
                        <c:if test="${jugador.image == ''}"><img class="rounded d-block" src="/resources/images/perfilPorDefecto.jpg" width="50" height="50"></c:if>
                        <c:if test="${jugador.image != ''}"><img class="rounded d-block" src="${jugador.image}" width="50" height="50"></c:if>
                        
                        <a href="/jugadores/${jugador.id}" class="btn" style="color: black;">[<c:out value="${jugador.user.username}"/>]</a>
                        <c:out value="${jugador.firstName}"/><c:out value=" "/><c:out value="${jugador.lastName}"/>
                    </td>
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</petclinic:layout>