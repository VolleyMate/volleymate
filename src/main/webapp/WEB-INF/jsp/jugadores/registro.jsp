<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="jugadores">
    <h2>
        Nuevo jugador
    </h2>
    <form:form modelAttribute="jugador" class="form-horizontal" id="añadir-jugador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="first_name">
            <petclinic:inputField label="Apellido" name="last_name"/>
            <petclinic:inputField label="Teléfono" name="telephone"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">            
                    
            	<button class="btn btn-default" type="submit">Registrarse</button>                  
                
            </div>
        </div>
    </form:form>
</petclinic:layout>
