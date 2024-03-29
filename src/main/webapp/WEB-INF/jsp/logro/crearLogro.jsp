<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearLogro">
    
<c:if test="${errors.size() != 0}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${error}"/>
        </div>    
    </c:forEach>
</c:if>

<h2>
        <p style="font-size:1.5em; text-align: center;">
            <strong>Guardar logro</strong>
        </p>
    </h2>     
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
      <div class="container"> 
        <form:form modelAttribute="logro" class="form-horizontal" id="crear_logro">
          <div class="row form-row">
          	<form:hidden path="id"/>
          </div>
          <div class="row form-row">
            <div class="col-md-8">
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                  <form:label path="nombre" >Nombre logro:</form:label>
                  <form:input path="nombre" style="border-radius: 20px;" class="form-control" />
                </div>
                <div class="form-group">
                  <form:label path="descripcion">Descripcion:</form:label>
                  <form:textarea path="descripcion" style="border-radius: 20px;" rows="5" cols="30" class="form-control"/>
                </div>
                <div class="form-group">
                  <label for="imagen">Tipo de logro:</label>
                  <select name="imagen" id="imagen" style="border-radius: 20px;" class="form-control">
                    <option value="/resources/images/bronce.png">Bronce</option>
                    <option value="/resources/images/plata.png">Plata</option>
                    <option value="/resources/images/oro.png">Oro</option>
                  </select>
                </div>
                
              </div>
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                  <form:label path="threshold">Meta:</form:label>
                  <form:input path="threshold" style="border-radius: 20px;" type="number" min="0.0" class="form-control" />
                </div>
                <div class="form-group">
                  <form:label path="metrica">Metrica:</form:label>
                  <form:select path="metrica" style="border-radius: 20px;" class="form-control">
                    <form:option value="partidos" itemLabel="Partidos"/>
                    <form:option value="valoracion" itemLabel="Valoracion"/>
                  </form:select>
                </div>
              </div>
              <div class="col-md-12">
                <button class="btn btn-md btn-default" style="background-color: #838789; margin: 0 5px;" type="submit">Guardar</button>
              </div>
             
            
              <br>
              <br>
            </div>
          </div>
        </form:form>
      </div>   
    </div>
      
</petclinic:layout>