<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
    

<petclinic:layout pageName="listaJugadores">
    
<c:if test="${mensajeError != null}">
        <div class="alert alert-danger alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeError}"/>
          </div>
    </c:if>
    <c:if test="${mensajeExitoso != null}">
        <div class="alert alert-success alert-dismissible" style="padding-top: 2%;" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <c:out value="${mensajeExitoso}"/>
          </div>
    </c:if>

    <div class="container text-center">
        <form id="mi-formulario" class="form-inline" th:action="@{/}">
            <div class="form-group mb-2">
                <label>Filtrar : </label>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type="text" class="form-control" style="width: 100px; border-radius: 20px;" id="palabraClave" name="palabraClave" placeholder="Usuario"/>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type="number" style="width: 140px; border-radius: 20px;" class="form-control" id="valoracionMedia" min="0" max="5" name="valoracionMedia" placeholder="Valoracion (1-5)"/>
            </div>
            <input type="submit" class="btn btn-default mb-2" value="Buscar">
            <input type="submit" class="btn btn-secondary mb-2" value="Limpiar" style="border-radius: 20px; border-width: 2px; border-color: #0099; font-size: 1.5rem;" onclick="limpiarInput()">
        </form>
    </div>

   
    <h2>Jugadores</h2>
    <div class="row listaJugadores numJugadores">
        <c:if test="${numJugadores == 0}">
            No se encuentra ningún jugador.
        </c:if>
        <c:if test="${numJugadores != 0}">
            <c:forEach items="${listaJugadores}" var="jugador">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body"> 
                            <img src="${jugador.image}" width="25" height="25" style="border-radius:5px">                 
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
                            <div class="text-center">
                                <a href="/jugadores/${jugador.id}" class="btn btn-default">Ver</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

        
</petclinic:layout>

<script>

function limpiarInput() {
    document.getElementById("palabraClave").value = "";
    var palabraClaveInput = document.getElementById("palabraClave").value;
    if (palabraClaveInput === "") {
        document.getElementById("mi-formulario").submit();
    }
    document.getElementById("valoracionMedia").value = 0;
    var valoracionMediaInput = document.getElementById("valoracionMedia").value;
    if (valoracionMedia === "0") {
        document.getElementById("mi-formulario").submit();
    }
}

var url_string = window.location.href;
var url = new URL(url_string);
var palabraClave = url.searchParams.get("palabraClave");
var valoracionMedia = url.searchParams.get("valoracionMedia");

document.getElementById("palabraClave").value = palabraClave;
document.getElementById("valoracionMedia").value = valoracionMedia;

$(document).ready(function(){
        $('#palabraClave').on('input', function() {
            $('#mi-formulario').submit();
        });
        $('#valoracionMedia').on('input', function() {
            $('#mi-formulario').submit();
        });
        $('#palabraClave').focus();

});
    
</script>

<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */
        border-radius: 10px;
        padding-top: 4%;
        padding-bottom: 4%;
        padding-left: 4%;
        padding-right: 4%;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }
    .card:hover {
        transform: scale(1.1);
        cursor: pointer;
        background-color: #e6f4f2;
        animation: pulse 1.5s infinite;
    }

    @keyframes pulse {
        0% {
            transform: scale(1);
        }
        50% {
            transform: scale(1.05);
        }
        100% {
            transform: scale(1);
        }
    }
</style>
