<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<petclinic:layout pageName="jugadores">
    <h2>
        <c:if test="${jugador['new']}"><p class="align-left"> Nuevo Jugador</p></c:if>
        <p class="align-left" style="font-size:1.5em;">
            <strong><u>PERFIL</u></strong>
        </p>
    </h2>
    
    <form:form modelAttribute="jugador" class="form-horizontal " id="add-jugador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellido" name="lastName"/>
            <petclinic:inputField label="Ciudad" name="ciudad"/>
            <petclinic:inputField label="Teléfono" name="telephone"/>
            
        </div>
    
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${jugador['new']}">
                        <button class="btn btn-default" type="submit">Registrarse</button>
                    </c:when>
                    <c:otherwise>
                        
                        <button class="btn btn-default" type="submit">Actualizar datos</button>
                        
                        <spring:url value="/jugadores" var="editUrl"></spring:url>
                        <a href="${editUrl}" class="btn btn-default">Volver</a>
                        
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <form:errors></form:errors>
    </form:form>

    <div>
        <c:forEach var="error" items="${errors}">
            <c:out value="${error}"/>
        </c:forEach>   
    </div>

</petclinic:layout>