<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="listaCentros">

<h1>Centros disponibles</h1>

    <div class="row">
        <c:if test="${numCentros == 0}">
            Aún no se ha aceptado ningún centro.
        </c:if>
        <c:if test="${numCentros != 0}">
            
            <c:forEach items="${centros.content}" var="centro">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <p class="card-text">
                                <strong>Nombre:</strong> <c:out value="${centro.nombre}"/>
                              </p>                              
                            <p class="card-text">
                                <strong>Dirección:</strong> <c:out value="${centro.direccion}"/> 
                            </p>
                            <p class="card-text">
                                <strong>Ciudad:</strong> <c:out value="${centro.ciudad}"/>
                            </p>
                            <div class="text-center">
                                <a href="/centros/${centro.id}" class="btn btn-default">Ver</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if> 
    </div>
 
    <div class="row" style="width: 100%;">
        <div class="col-md-6 text-left">
            <c:if test="${numCentros != 0}">
            <c:if test="${centros.hasPrevious()}">
                <c:url var="previousPageUrl" value="/centros">
                    <c:param name="page" value="${centros.number - 1}" />
                </c:url>
                <a href="${previousPageUrl}" class="previous">
                    <button class="btn btn-default">
                        Anterior
                    </button>
                </a>
    
            </c:if>
            <c:if test="${!centros.isLast()}">
                <c:url var="nextPageUrl" value="/centros">
                    <c:param name="page" value="${centros.number + 1}" />
                </c:url>
                <a href="${nextPageUrl}" class="next">
                    <button class="btn btn-default">
                        Siguiente
                    </button>
                </a>
            </c:if>
            <p style="font-size: large;">Página ${centros.number + 1} de ${centros.totalPages}</p>
        </c:if>
        </div>

    </div>
    <div class="row" style="width: 100%; padding-top: 5%;">
        <div class="col-md-6 text-left">
            <a href="/centros/solicitud/new" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Solicitar centro</a>
            <sec:authorize access="hasAuthority('admin')">
            <a href="/centros/solicitud/list" class="btn btn-default"> <span  aria-hidden="true"></span> Ver solicitudes</a>
        </sec:authorize>
        </div>
    </div>


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
</style>