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
        <p class="align-left" style="font-size:1.5em;">
            <strong><u>PERFIL</u></strong>
        </p>
    </h2>
    
    <form:form modelAttribute="jugador" class="form-horizontal " id="add-jugador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellido" name="lastName"/>
            <petclinic:inputField label="Ciudad" name="ciudad"/>
            <petclinic:inputField label="Image" name="image"/>

            Si quieres la foto por defecto, deja este campo vacío
            
        </div>
    
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">   
                        <button class="btn btn-default" type="submit">Actualizar datos</button>
                        <a href="/jugadores" class="btn btn-default">Volver</a>
            </div>
        </div>
        <form:errors></form:errors>
    </form:form>

    <div style="text-align: center; color: #FF0000">
            <c:forEach var="error" items="${errors}">
                <ul>    
                 <c:out value="${error} "/>
                </ul>
            </c:forEach>
    </div> 

</petclinic:layout>