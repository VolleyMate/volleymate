<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="editarAspecto">

    <h2>
        <div style="font-size:1.5em; text-align: center;">
            <strong>Editar aspecto</strong>
        </div>
    </h2>
 
    <div style="background-color: #0099BB; padding: 20px; width: 80%; margin: 0 auto;border-radius: 20px;">
        <div class="container">
            
        <form:form modelAttribute="aspecto" class="form-horizontal" id="crear_aspecto">
          <div class="row form-row">
          	<form:hidden path="id"/>
          </div>
          <div class="row form-row">
            <div class="col-md-8">
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                    <form:label path="imagen" >Imagen:</form:label>
                    <form:input path="imagen" style="border-radius: 20px;" class="form-control" />
                  </div>  
                  <div class="form-group">
                    <form:label path="precio">Precio:</form:label>
                    <form:input path="precio" style="border-radius: 20px;" type="number" min="0" class="form-control" />
                  </div>                
                </div>
              <div class="col-md-12">
                <button class="btn btn-md btn-default" style="background-color: #838789; margin: 0 5px;" type="submit">Actualizar</button>
              </div>
              <br>
              <br>
            </div>
          </div>
        </form:form>

                <form:errors></form:errors>
        </div>
    </div>


    <div style="text-align: center; color: #FF0000; padding-top: 2%;">
        <c:forEach var="error" items="${errors}">
            <ul>
                <c:out value="${error} " />
            </ul>
        </c:forEach>
    </div>

</petclinic:layout>