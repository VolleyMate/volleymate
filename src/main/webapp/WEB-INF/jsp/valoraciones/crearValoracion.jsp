<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearPartido">
    
    <h2>Crear valoracion</h2>
    <form:form modelAttribute="valoracion" class="form-horizontal" id="crear_partido">
        <div class="row form-row">
            <form:hidden path="id"/>
            <form:hidden path="ratedPlayer"/>
            <form:hidden path="ratingPlayer"/>
        </div>
        <div class="row form-row">
          <div class="col-md-4" style="margin-right: 40px; margin-left: 20px;">
            
            <div class="form-group">
              <form:label path="puntuacion">Valoracion</form:label>
              <form:input path="puntuacion" style="border-radius: 20px;" type="number" min="1" max="5" class="form-control" />
            </div>
            <div class="form-control">
              <form:label path="comentario">Comentario</form:label>
              <form:textarea path="comentario" style="border-radius: 20px;" class="form-control" />
            </div>
            <br>
            <br>
            <div style="text-align: center">
                <button style="background-color: #838789" type="submit" class="btn btn-primary">Crear valoracion</button>                               
            </div>
         
        </div>
      </form:form>
    
          
      <div style="text-align: center; color: #FF0000">
      	<c:forEach var="error" items="${errors}">
            <c:out value="${error} "/>
        </c:forEach>
      </div>
      
</petclinic:layout>