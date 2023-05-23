<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="tiendaAspectos">  
    <div class="container" style="background-color: #0099bb3e; padding: 20px; width: 100%; margin-top: 5%; border-radius: 20px;">
        <div class="row">
            <div class="col-md-12 col-xl-6 text-center mx-auto" >
                <h2>Tienda de aspectos</h2>
                <h3>
                    <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">
                    <c:out value="${jugador.volleys}"/>
                </h3>
                <p style="padding-bottom: 2%;">¡Personaliza tu aspecto!</p>
            </div>
            <c:if test="${numAspectos == 0}">
            No hay ningún aspecto disponible.
            </c:if>
            <c:if test="${numAspectos != 0}">
                <c:forEach items="${aspectos}" var="aspecto">
                <div class="col-md-4 text-center mx-auto">
                    <div class="card">
                        <div class="card-body row">
                            <div class="col-md-4">
                                <div style="margin: 2,5%;">
                                    <p class="card-text">
                                        <img src="${aspecto.imagen}" style="height: 100px; width: 100px;">
                                    </p>
                                </div> 
                            </div>
                            <div class="col-md-8">
                                <p class="card-text">
                                    <div style="padding-top: 5%">
                                        <c:choose>
                                            <c:when test="${jugador.premium}">
                                                <strong><c:out value="¡ Incluido con premium !"/> </strong>
                                            </c:when>
                                            <c:otherwise>
                                                <div style="font-size: medium;">
                                                    <strong>
                                                        <c:out value="${aspecto.precio}"/>
                                                        <c:out value=" "/>
                                                        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">
                                                    </strong>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </p>
                                <div style="padding-top: 8%">
                                    <c:choose>
                                    <c:when test="${jugador.aspectos.contains(aspecto)}">
                                        <strong><c:out value="¡ Ya tienes este aspecto !"/> </strong>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:url value="/tienda/aspectos/comprar/{aspectoId}" var="comprarAspectoUrl">
                                            <spring:param name="aspectoId" value="${aspecto.id}"/>
                                        </spring:url>
                                        <a href="${fn:escapeXml(comprarAspectoUrl)}">
                                            <p class="btn btn-default" style="background-color: goldenrod;">Comprar</p>
                                        </a>   
                                        <div style="padding-top: 3%">
                                            <sec:authorize access="hasAuthority('admin')">
                                                <spring:url value="/tienda/aspectos/edit/{aspectoId}" var="editUrl">
                                                     <spring:param name="aspectoId" value="${aspecto.id}" />
                                                </spring:url>
                                                <a href="${fn:escapeXml(editUrl)}">
                                                    <p class="btn btn-default">Editar aspecto</p>
                                                </a>
                                            </sec:authorize> 
                                        </div>   
                                        <div style="padding-top: 3%">
                                            <sec:authorize access="hasAuthority('admin')">
                                                <spring:url value="/tienda/aspectos/delete/{aspectoId}" var="deleteUrl">
                                                     <spring:param name="aspectoId" value="${aspecto.id}" />
                                                </spring:url>
                                                <a href="${fn:escapeXml(deleteUrl)}">
                                                    <p class="btn btn-red" >Eliminar aspecto</p>
                                                </a>
                                            </sec:authorize> 
                                        </div> 
                                    </c:otherwise>
                                </c:choose>
                                </div>
                            </div>                                         
                        </div>
                    </div>
                    
                </div>
                </c:forEach>
            </c:if> 
            <div class="row" style="width: 100%;">
                <div class="col-md-12 col-xl-6 text-center mx-auto">
                    <sec:authorize access="hasAuthority('admin')">
                        <a href="/tienda/aspectos/nuevo" class="btn btn-default"> <span  aria-hidden="true"></span> Agregar nuevo aspecto</a>
                    </sec:authorize>
            </div>
            </div>
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
