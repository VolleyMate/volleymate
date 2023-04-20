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

<c:if test="${errors.size() != 0}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${error}"/>
        </div>    
    </c:forEach>
</c:if>


<c:if test="${mensajeError != null}">
        <div class="alert alert-danger alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeError}"/>
          </div>
    </c:if>
    <c:if test="${mensajeExitoso != null}">
        <div class="alert alert-success alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeExitoso}"/>
          </div>
    </c:if>
    <h2>
        <p style="font-size:1.5em; text-align: center;">
            <strong>Editar mi perfil</strong>
        </p>
    </h2>
 
    <div style="background-color: #0099bb3e; padding: 20px; width: 80%; margin: 0 auto;border-radius: 20px;">
        <div class="container">
            <form:form modelAttribute="jugador" class="form-horizontal " id="add-jugador-form">
                <div class="col-md-8" style="margin: 50px;">
                    <div class="form-group has-feedback" >

                        <div class="form-group">
                            <form:label path="firstName" >Nombre:</form:label>
                            <form:input path="firstName" style="border-radius: 20px;" class="form-control" />    
                        </div>
                       
                        <div class="form-group">
                            <form:label path="lastName" >Apellido:</form:label>
                            <form:input path="lastName" style="border-radius: 20px;" class="form-control" />    
                        </div>

                        <div class="form-group">
                            <form:label path="ciudad">Ciudad:</form:label>
                            <form:select path="ciudad" style="border-radius: 20px;" class="form-control">
                                <form:options items="${ciudades}" value="${ciudad}" itemLabel="nombre"/>
                            </form:select>  
                        </div>

                    </div>

                    <div class="form-group" style="text-align: center;">
                        <button class="btn btn-md btn-default"
                            type="submit">Actualizar datos</button>
                        <a href="/jugadores" class="btn btn-md btn-default"
                            >Volver atrás</a>
                    </div>
                </div>

                <form:errors></form:errors>

            </form:form>
        </div>
    </div>

</petclinic:layout>