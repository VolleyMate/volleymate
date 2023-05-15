<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearAspecto">
    <h2>
        <div style="font-size:1.5em; text-align: center;">
          <p><strong>Agregar nuevo aspecto</strong></p>
        </div>
    </h2>     
    
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
      <div class="container"> 
        <form:form modelAttribute="aspecto" class="form-horizontal" id="crear_aspecto">
          <div class="row form-row">
            <div class="col-md-8">

                <div class="form-group">
                  <form:label path="imagen" >Imagen:</form:label>
                  <form:input path="imagen" style="border-radius: 20px;" class="form-control" />
                </div>  
                <div class="form-group">
                  <form:label path="precio">Precio:</form:label>
                  <form:input path="precio" style="border-radius: 20px;" type="number" min="0" class="form-control" />
                </div>              
              </div>
              <div class="col-md-8 text-center">
                  <div style="text-align: center;">
                    <button class="btn btn-lg btn-default" style="background-color: #838789; margin: 0 5px;"
                            type="submit">Agregar
                    </button>
                  </div>
              </div>
              <br>
              <br>
            </div>   
          </div>
        </form:form>
      </div>   
    </div>
          
    <div style="text-align: center; color: #FF0000; padding-top: 2%;">
          <c:forEach var="error" items="${errors}">
              <ul>    
               <c:out value="${error} "/>
              </ul>
          </c:forEach>
  </div> 
      
</petclinic:layout>