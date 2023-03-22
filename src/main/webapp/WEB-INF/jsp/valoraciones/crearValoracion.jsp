<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearValoracion">
  <div class="container">
    <h2 style="text-align: center; font-size: 1.5em;">
      <strong>Crear valoración</strong>
    </h2>
    <div style="background-color: #0099BB; padding: 20px; width: 80%; margin: 0 auto;border-radius: 20px;">
    <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card" style="border-radius: 20px;">
          <div class="card-body">
            <form:form modelAttribute="valoracion" class="form-horizontal" id="crear_valoracion">
              <form:hidden path="id"/>
              <form:hidden path="ratedPlayer"/>
              <form:hidden path="ratingPlayer"/>
              <div class="form-group" >
                <form:label path="puntuacion" >Valoracion</form:label>
                <form:input path="puntuacion" type="number" min="1" max="5" class="form-control" style="border-radius: 20px;" />
              </div>
              <div class="form-group">
                <form:label path="comentario">Comentario</form:label>
                <form:textarea path="comentario" class="form-control" style="border-radius: 20px;" rows="4"></form:textarea>
              </div>
              <div class="form-group text-center">
                <button type="submit" class="btn btn-primary" style="background-color: #838789;">Crear valoracion</button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
        <div style="text-align: center; color: #FF0000;">
          <c:forEach var="error" items="${errors}">
            <c:out value="${error} "/>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</petclinic:layout>