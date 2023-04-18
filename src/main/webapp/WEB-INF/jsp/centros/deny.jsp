<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="deny">
       
    
        <div class="container" style="background-color: #0099bb3e; padding: 20px; width: 70%; margin: 0 auto;border-radius: 20px;"> 
            <div class="row" style="justify-content: center; align-items: center; text-align: center;">
                <p >Has denegado la solicitud de centros</p>
            </div>
            <div class="row" style="justify-content: center; align-items: center; text-align: center;">
                <br>
                <a href="/centros" class="btn btn-default">Volver</a>
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

<style>
    .container p {
        font-size: 24px;
    }
</style>