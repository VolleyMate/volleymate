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
        <c:if test="${jugador['new']}">
            <p class="align-left">Nuevo Jugador</p>
        </c:if>
    </h2>
    
    <form:form modelAttribute="jugador" class="form-horizontal " id="add-jugador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellido" name="lastName"/>
            <petclinic:inputField label="URL Image" name="image"/>
            <petclinic:inputField label="Password" name="user.password"/>
            <petclinic:inputField  label="Nombre de usuario" name="user.username"/>
            <petclinic:inputField  label="Ciudad" name="ciudad"/>
            <petclinic:inputField  label="Teléfono" name="telephone"/>
            <petclinic:inputField  label="Sexo" name="sexo"/>   
            <petclinic:inputField  label="Correo" name="user.correo"/>
        </div>
    
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Registrarse</button>
            </div>
        </div>
        <form:errors></form:errors>
    </form:form>

</petclinic:layout>