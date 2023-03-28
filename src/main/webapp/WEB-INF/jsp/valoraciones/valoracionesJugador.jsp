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
                        <th style="width: 120px; text-align: center;">Jugador que valora</th>
                        <th style="width: 120px; text-align: center;">Comentario</th>
                    
                    </tr>
                    <tr style="height: 15px;"></tr>
                </thead>
                <tbody>
                    <c:forEach items= "${valoraciones}" var="valoracion">

                        <td style="text-align: center;">
                            <c:out value="${valoracion.puntuacion}"/>
                        </td>
                        <td style="text-align: center;">
                            <c:out value="${valoracion.ratingPlayer.firstName}"/>
                        </td>
                        <td style="text-align: center;">
                            <c:out value="${valoracion.comentario}"/>
                        </td>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
</body>
</petclinic:layout>