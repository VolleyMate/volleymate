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
    <div style="width: 70%; background-color: #0099bc; margin: 0 auto; border-radius: 50px;  text-align: center; display: flex; flex-wrap: wrap;">

        <div style="width: 45%;">
            <h3></h3>
            <h3 style="color: #FFFFFF;">Nombre: </h3>
            <h3 style="background-color: white;border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto; ">
                <c:out value="${centro.nombre}"></c:out>
            </h3>
            <h3></h3>
            <h3 style="color: #FFFFFF;">Ciudad: </h3>
            <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
                <c:out value="${centro.ciudad}" />
            </h3>
        </div>
        <div style="width: 45%;">
            <h3></h3>
            <h3 style="color: #FFFFFF;">Dirección: </h3>
            <h3 style="background-color: white; border-radius: 5px; width: 20em; text-align: center;  margin: 0 auto;">
                <c:out value="${centro.direccion}" />
            </h3>
            <h3></h3>
            <h3 style="color: #FFFFFF;">Mapa:</h3>
            <h3 style="background-color: white; border-radius: 5px; width: 25em; text-align: center;  margin: 0 auto;">
                <c:out value="${centro.maps}" />
            </h3>
            <br>
        <h3></h3>
        
    
        </div>

    </div>


    <div style="text-align: center; padding-top: 3%;">
        <sec:authorize access="hasAuthority('admin')">
            <spring:url value="/centros/edit/{centroId}" var="editUrl">
                <spring:param name="centroId" value="${centro.id}" />
            </spring:url>
            <a href="${editUrl}" class="btn btn-default">Editar centro</a>
            

            <spring:url value="/centros/delete/{centroId}" var="eliminarURL">
                <spring:param value="${centro.id}" name="centroId"/>
            </spring:url>
            <a href="${eliminarURL}" class="btn btn-danger">Eliminar centro [ADMIN]</a>
        </sec:authorize>
    
    </div>

</petclinic:layout>
