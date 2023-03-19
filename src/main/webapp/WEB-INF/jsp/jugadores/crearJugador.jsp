<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="jugadores">
    <h2>
        <c:if test="${jugador['new']}">
            <p class="align-left">Nuevo Jugador</p>
        </c:if>
    </h2>
    <div style="background-color: #0099BB; padding: 20px; width: 80%; margin: 0 auto;border-radius: 20px;">
        <div class="container">         
            <form:form modelAttribute="jugador" class="form-horizontal " id="add-jugador-form">
                <div class="row form-row">
                    <div class="col-md-8" style="margin-right: 40px; margin-left: 20px;">
                        <div class="form-group">
                            <form:label path="firstName">Nombre:</form:label>
                            <form:input path="firstName" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="lastName">Apellidos:</form:label>
                            <form:input path="lastName" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="image">Imagen URL:</form:label>
                            <form:input path="image" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="user.password">Contraseña:</form:label>
                            <form:input path="user.password" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="user.username">Nombre de usuario:</form:label>
                            <form:input path="user.username" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="user.correo">Correo:</form:label>
                            <form:input path="user.correo" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="ciudad">Teléfono:</form:label>
                            <form:input path="ciudad" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="telephone">Ciudad:</form:label>
                            <form:input path="telephone" style="border-radius: 20px;" class="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="sexo">Sexo:</form:label>
                            <form:select path="sexo" style="border-radius: 20px;" class="form-control">
                              <form:options items="${sexos}" value="${sexos}"/>
                            </form:select>
                        </div>

                        <div style="text-align: center">
                            <button style="background-color: #838789" class="btn btn-primary" type="submit">Registrarse</button>
                        </div>
                    </div>
                
    
            
                    <!-- <div class="form-group has-feedback">
                    <petclinic:inputField label="Nombre" name="firstName"/>

                    <div class="form-group">
                        <form:label path="firstName">Nombre:</form:label>
                        <form:input path="firstName" style="border-radius: 20px;" class="form-control" />
                    </div>
                    <petclinic:inputField label="Apellido" name="lastName"/>
                    <petclinic:inputField label="URL Image" name="image"/>
                    <petclinic:inputField label="Password" name="user.password"/>
                    <petclinic:inputField label="Nombre de usuario" name="user.username"/>
                    <petclinic:inputField label="Ciudad" name="ciudad"/>
                    <petclinic:inputField label="Teléfono" name="telephone"/>
                    <petclinic:inputField label="Sexo" name="sexo"/>   
                    <petclinic:inputField label="Correo" name="user.correo"/>
                    </div> -->
            
                    
                </div>
                <form:errors></form:errors>
            </form:form>
        </div>
    </div>        
</petclinic:layout>