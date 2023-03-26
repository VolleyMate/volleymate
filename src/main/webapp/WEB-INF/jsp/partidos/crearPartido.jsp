<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearPartido">
    <h2>
        <p style="font-size:1.5em; text-align: center;">
            <strong>Crear partido</strong>
        </p>
    </h2>     
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
      <div class="container"> 
        <form:form modelAttribute="partido" class="form-horizontal" id="crear_partido">
          <div class="row form-row">
          	<form:hidden path="id"/>
          	<form:hidden path="creador"/>
          	<form:hidden path="fechaCreacion"/>
          </div>
          <div class="row form-row">
            <div class="col-md-8">
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                  <form:label path="nombre" >Nombre actividad:</form:label>
                  <form:input path="nombre" style="border-radius: 20px;" class="form-control" />
                </div>
                <div class="form-group">
                  <form:label path="descripcion">Descripcion:</form:label>
                  <form:textarea path="descripcion" style="border-radius: 20px;" rows="5" cols="30" class="form-control"/>
                </div>
                <div class="form-group">
                  <form:label path="numJugadoresNecesarios">Número de jugadores necesarios:</form:label>
                  <form:input path="numJugadoresNecesarios" style="border-radius: 20px;" type="number" min="1" class="form-control" />
                </div>
                
              </div>
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                  <form:label path="sexo">Sexo:</form:label>
                  <form:select path="sexo" style="border-radius: 20px;" class="form-control">
                    <form:options items="${sexos}" />
                  </form:select>
                </div>
                <div class="form-group">
                  <form:label path="tipo">Tipo:</form:label>
                  <form:select path="tipo" style="border-radius: 20px;" class="form-control">
                    <form:options items="${tipos}" value="${tipo}"/>
                  </form:select>
                </div>
                <div class="form-group">
                  <form:label path="fecha">Fecha y hora:</form:label>
                  <form:input path="fecha" style="border-radius: 20px;" type="datetime-local" class="form-control" />
                </div> 
                <div class="form-group">
                  <form:label path="centro">Centro:</form:label>
                  <form:select path="centro" style="border-radius: 20px;" class="form-control">
                    <form:options items="${centros}" value="${centro}" itemLabel="nombre"/>
                  </form:select>
                </div>
              </div>
              <div class="col-md-12">
                <c:if test="${puedeCrear}">
                  <div style="text-align: center;">
                    <button class="btn btn-md btn-default" style="background-color: #838789; margin: 0 5px;"
                            type="submit">150 volleys
                            <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys"
                        style="width: 20px; height: 20px; margin-right: 10px;">
                    </button>
                  </div>
                </c:if>
                <c:if test="${!puedeCrear}">
                  <div style="text-align: center">
                    <a class="btn btn-danger">No tienes volleys suficientes</a>
                  </div>
                </c:if>
              </div>
             
            
              <br>
              <br>
            </div>
          </div>
        </form:form>
      </div>   
    </div>
          
    <div style="text-align: center; color: #FF0000">
          <c:forEach var="error" items="${errors}">
              <ul>    
               <c:out value="${error} "/>
              </ul>
          </c:forEach>
  </div> 
      
</petclinic:layout>