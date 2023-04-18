<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="detalleCentro">
    <c:if test="${mensajeError != null}">
        <div style="background-color: red;">
            <c:out value="${mensajeError}"></c:out>
        </div>
    </c:if>
    <c:if test="${mensajeExitoso != null}">
        <div style="background-color: green;">
            <c:out value="${mensajeExitoso}"></c:out>
        </div>
    </c:if>
    <h1>Detalles del centro:</h1>
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
        <div class="container"> 
            <div class="row form-row">
                <div class="col-md-8">
                    <div class="col-md-5" style="margin: 25px;">
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <input type="text" style="border-radius: 20px;" class="form-control" value="${centro.nombre}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="ciudad">Ciudad:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${centro.ciudad}" readonly>
                        </div>
                    </div>
                    <div class="col-md-5" style="margin: 25px;">
                        <div class="form-group">
                            <label for="direccion">Dirección:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${centro.direccion}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="mapa">Mapa:</label>
                            <input type="text" class="form-control" style="border-radius: 20px;" value="${centro.maps}" readonly>
                        </div>
                    </div>
                </div>
            </div>
        </div>   
    

        <div style="text-align: center; padding-top: 3%;">
            <sec:authorize access="hasAuthority('admin')">
            <spring:url value="/centros/edit/{centroId}" var="editUrl">
                <spring:param name="centroId" value="${centro.id}" />
            </spring:url>
            <a href="${editUrl}" class="btn btn-primary">Editar centro</a>

            <spring:url value="/centros/delete/{centroId}" var="eliminarURL">
                <spring:param value="${centro.id}" name="centroId"/>
            </spring:url>
            <a href="${eliminarURL}" class="btn btn-danger">Eliminar centro [ADMIN]</a>
            </sec:authorize>
        </div>
    </div>
</petclinic:layout>