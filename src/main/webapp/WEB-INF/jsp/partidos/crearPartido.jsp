<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearPartido">
    
    <h2>Crear partido</h2>
        
    <div style="background-color: #0099BB; padding: 20px; width: 80%; margin: 0 auto;border-radius: 20px;">
      <div class="container">
        <form:form modelAttribute="partido" class="form-horizontal" id="crear_partido">
          <div class="row form-row">
            <div class="col-md-4" style="margin-right: 40px; margin-left: 20px;">
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
              <!-- <div class="form-group">
                <form:label path="precioPersona">Precio por persona:</form:label>
                <form:input path="precioPersona" style="border-radius: 20px;" type="number" step="0.01" min="0" class="form-control" />
              </div> -->
              <div class="form-group">
                <label for="precioPersona">Precio por persona:</label>
                <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">150 volleys
                <!-- <select id="precioPersona" name="precioPersona" class="form-control" style="border-radius: 20px;">
                  <option value="Gratis">Gratis</option>
                  <option value="150 volleys">150 volleys</option>
                </select> -->
              </div>
              

            </div>
            <div class="col-md-4">
              <div class="form-group">
                <form:label path="sexo">Sexo:</form:label>
                <form:select path="sexo" style="border-radius: 20px;" class="form-control">
                  <form:options items="${sexos}" />
                </form:select>
              </div>
              <div class="form-group">
                <form:label path="tipo">Tipo:</form:label>
                <form:select path="tipo" style="border-radius: 20px;" class="form-control">
                  <form:options items="${tipos}" />
                </form:select>
              </div>
              <div class="form-group">
                <form:label path="fecha">Fecha y hora:</form:label>
                <form:input path="fecha" style="border-radius: 20px;" type="datetime-local" class="form-control" />
              </div>
              <div class="form-group">
                <form:label path="lugar">Lugar:</form:label>
                <form:input path="lugar" style="border-radius: 20px;" class="form-control" />
              </div>
              <br>
              <br>
              <div style="text-align: center">
                <button style="background-color: #838789" type="submit" class="btn btn-primary">Crear partido</button>                               
              </div>
            </div>
          </div>
        </form:form>
      </div>
    </div>
  

</petclinic:layout>