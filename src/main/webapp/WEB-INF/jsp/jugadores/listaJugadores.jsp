<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="listaJugadores">
    
    <div class="container text-center">
        <form id = "mi-formulario" class="form-inline" th:action="@{/}">
            <div class="form-group mb-2">
                <label>Filtrar : </label>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type="text" class="form-control" id="input-1" name="palabraClave" th:value="${palabraClave}" placeholder="Escriba el valor a buscar" required/>
            </div>
            <input type="submit" class="btn btn-info mb-2" value="Buscar">
            <input type="submit" class="btn btn-secondary mb-2" value="Limpiar">
        </form>
    </div>

   
    <h2>Jugadores</h2>
    <div class="row">
        <c:if test="${numJugadores == 0}">
            No se encuentra ningún jugador.
        </c:if>
        <c:if test="${numJugadores != 0}">
            <c:forEach items="${listaJugadores}" var="jugador">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">                  
                            <p class="card-text">
                                <strong>Nombre:</strong> <c:out value="${jugador.user.username}"/>
                              </p>                              
                            <p class="card-text">
                                <strong>Ciudad:</strong> <c:out value="${jugador.ciudad}"/> 
                            </p>
                            <p class="card-text">
                                <strong>Teléfono:</strong> <c:out value="${jugador.telephone}"/>
                            </p>
                            <p class="card-text">
                                <strong>Sexo:</strong> <c:out value="${jugador.sexo}"/>
                            </p>
                            <a href="/jugadores/{jugadorId}" class="btn btn-primary">Ver</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

        
</petclinic:layout>

<script src="https://code.jquery.com/jquery-3.6.0.min.js%22%3E"></script>
    <script>
      const formulario = document.querySelector('#mi-formulario');
      const input = document.querySelector('#input-1');
    
      input.addEventListener('change', enviarDatos);
    
      function enviarDatos() {
    
        const valor = input.value;
    
        $.post('/listaJugadores', {input: valor});
      }
</script>

<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */

}
</style>
