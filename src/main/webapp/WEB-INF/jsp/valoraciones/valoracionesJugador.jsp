<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>



</style>

<petclinic:layout pageName="valoracionesJugador">
<head>
    <title>Valoraciones del perfil</title>
</head>
<body>
    <h1>Valoraciones del perfil</h1>
        <c:if test="${valoraciones.size() == 0}">
                No hay valoraciones todavía.
        </c:if>
        <c:if test="${valoraciones.size() != 0}">
            <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
                <thead>
                    <tr>
                        <th style="width: 120px; text-align: center;">Puntuación</th>
                        <th style="width: 120px; text-align: center;">Jugador</th>
                        <th style="width: 120px; text-align: center;">Comentario</th>
                    
                    </tr>
                    <tr style="height: 15px;"></tr>
                </thead>
                <tbody>
                    
                    <c:forEach items= "${valoraciones}" var="valoracion">
                        <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                            <td style="text-align: center; padding-top: 1.25%;">
                                <c:out value="${valoracion.puntuacion}"/>
                            </td>
                            <td style="text-align: center;">
                                <a style = "color: black; font-size: medium;" href="/jugadores/${valoracion.ratingPlayer.id}" class="btn" ><c:out value="${valoracion.ratingPlayer.firstName}"/></a>
                            </td>
                            <td style="text-align: center; padding-top: 1.25%;">

                                <c:out value="${valoracion.comentario}"/>
                            </td>
                        </tr>
                        <tr style="height: 15px;"></tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
</body>
</petclinic:layout>