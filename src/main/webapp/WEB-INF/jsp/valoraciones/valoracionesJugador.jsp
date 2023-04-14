<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="valoracionesJugador">
<head>
    <title>Valoraciones del perfil</title>
</head>
<body>
    <h1>Valoraciones del perfil</h1>
    <div class="row listaJugadores numJugadores">
        <c:if test="${valoraciones.size() == 0}">
            No hay valoraciones para este jugador.
        </c:if>
        <c:if test="${valoraciones.size()!= 0}">
            <c:forEach items="${valoraciones}" var="valoracion">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">                  
                            <p class="card-text">
                                <strong>Puntuación:</strong> <c:out value="${valoracion.puntuacion}"/>
                              </p>                              
                            <p class="card-text">
                                <strong>Jugador:</strong> 
                                <spring:url value="/jugadores/${id}" var="verURL">
                                    <spring:param name="id" value="${jugador.id}" />
                                </spring:url>
                                <a href="${fn:escapeXml(verURL)}" class="btn">
                                    <c:out value="${valoracion.ratingPlayer.user.username}"/> 
                                </a>
                            </p>
                            <p class="card-text">
                                <c:if test="${valoracion.comentario == ''}">
                                    <strong>Comentario: -</strong>
                                </c:if>
                                <c:if test="${valoracion.comentario != ''}">
                                    <strong>Comentario:</strong> <c:out value="${valoracion.comentario}"/>
                                </c:if>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</body>
 
</petclinic:layout>

<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */
        border-radius: 10px;
        padding-top: 4%;
        padding-bottom: 4%;
        padding-left: 4%;
        padding-right: 4%;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }
    .card:hover {
        transform: scale(1.1);
        cursor: pointer;
        background-color: #e6f4f2;
        animation: pulse 1.5s infinite;
    }

    @keyframes pulse {
        0% {
            transform: scale(1);
        }
        50% {
            transform: scale(1.05);
        }
        100% {
            transform: scale(1);
        }
    }

    a{
        color: black;
    }
    a:hover{
    }
</style>