<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="solicitudesList">
    <form action="/centros/solitud/list" method="get">
        <h2>Solicitudes de centros</h2>

    <div class="row">
        <c:if test="${numCentros == 0}">
            Aún no hay solicitudes de centros.
        </c:if>
        <c:if test="${numCentros != 0}">
            <c:forEach var="centro" items="${centros}">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                             
                            <p class="card-text">
                                <strong>Centro:</strong> <c:out value="${centro.nombre}"/>
                              </p>                              
                            <p class="card-text">
                                <strong>Dirección:</strong> <c:out value="${centro.direccion}"/> 
                            </p>
                            <p class="card-text">
                                <strong>Ciudad:</strong> <c:out value="${centro.ciudad}"/>
                            </p>
                            <p class="card-text">
                                <strong>Dirección en el mapa:</strong> <c:out value="${centro.maps}"/>
                            </p>
                            <div> 
                                <a href="/centros/solitud/accept/{centroId}" class="btn btn-default" style="background-color: green;">
                                    <span class="glyphicon" aria-hidden="true"></span> Aceptar solicitud
                                </a>
                            
                            
                            
                                
                                <a href="/centros/solitud/deny/{centroId}" class="btn btn-default" style="background-color: lightcoral;">
                                    <span  aria-hidden="true"></span> Denegar solicitud
                                </a>
                            
                            </div>
                        </div>
                    
                    </div>
                </div>
           </c:forEach>
        </c:if> 
    </div>
    </form>
</petclinic:layout>
<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */

}
    
</style>
    
