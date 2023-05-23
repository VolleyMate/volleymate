<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="crearCentros">
    
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
            <strong>Solicitar nuevo centro</strong>
        </p>
    </h2>     
    <div style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;">
      <div class="container"> 
        <form:form modelAttribute="centro" class="form-horizontal" id="crear_centro">
          <div class="row form-row">
            <div class="col-md-8">
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                  <form:label path="nombre" >Nombre:</form:label>
                  <form:input path="nombre" style="border-radius: 20px;" class="form-control" />
                </div>  
                <div class="form-group">
                    <form:label path="ciudad" >Ciudad:</form:label>
                    <form:input path="ciudad" style="border-radius: 20px;" class="form-control" />
                  </div>              
              </div>
              <div class="col-md-5" style="margin: 25px;">
                <div class="form-group">
                    <form:label path="direccion" >Direccion:</form:label>
                    <form:input path="direccion" style="border-radius: 20px;" class="form-control" />
                </div>
                <div class="form-group">
                    <form:label path="maps" >Ubicación en maps:</form:label>
                    <form:input path="maps" style="border-radius: 20px;" class="form-control" />
                </div>
              </div>
              <div class="col-md-12">
                  <div style="text-align: center;">
                    <button class="btn btn-lg btn-default" style="background-color: #838789; margin: 0 5px;"
                            type="submit">Solicitar
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
      
</petclinic:layout>