<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="partidos">
    
    <h2>Partidos disponibles</h2>
    <form action="/partidos" method="get">
        <div style="display: grid; grid-template-columns: repeat(4,1fr);">
            <div style="grid-column: 1;">
                <div style="text-align: left;">
                    <label for="sexo">Sexo:</label>
                </div>
                <select class="btn btn-default" id="sexo" name="sexo">
                    <option value="">Cualquiera</option>
                    <option value="MIXTO">Mixto</option>
                    <option value="MASCULINO">Masculino</option>
                    <option value="FEMENINO">Femenino</option>
                </select>
            </div>
            <br>
            <div style="grid-column: 2; grid-row: 1; align-items: center;">
                <div style="text-align: left;">
                    <label for="tipo">Tipo de partido:</label>
                </div>
                <select class="btn btn-default" id="tipo" name="tipo">
                    <option value="">Cualquiera</option>
                    <option value="VOLEIBOL">Voleibol</option>
                    <option value="FUTVOLEI">Futbolei</option>
                    <option value="BOSABALL">Bosaball</option>
                    <option value="SUBMARINO">Submarino</option>
                    <option value="SENTADO">Sentado</option>
                    <option value="PLAYA">Playa</option>
                    <option value="WATERVOLEY">Watervoley</option>
                </select>
            </div>
            <br>
            <div style="grid-column: 3; grid-row: 1;">
                <div style="text-align: left;">
                    <label for="ciudad">Ciudad:</label>
                </div>
                <input class="btn btn-default" type="text" id="ciudad" name="ciudad" />
            </div>
            <br>
            <div style="grid-column: 4; grid-row: 1;">
                <div style="text-align: left;">
                    <label for=""></label>
                </div>
                <button class="btn btn-default" type="submit">Filtrar</button>
            </div> 
        </div>
    </form>
   

    <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
        <thead>
            <tr>
                <th style="width: 150px; text-align: center;">Creador</th>
                <th style="width: 120px; text-align: center;">Jugadores</th>
                <th style="width: 120px; text-align: center;">Ciudad</th>
                <th style="width: 120px; text-align: center;">Dirección</th>
                <th style="width: 120px; text-align: center;">Fecha de la actividad</th>
                <th style="width: 120px; text-align: center;">Fecha de creación</th>
                <th style="width: 60px;"></th>
            </tr>
            <tr style="height: 15px;"></tr>
        </thead>
        <tbody>
            <c:forEach items="${partidos}" var="partido">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <c:out value="${partido.creador.user.username}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.jugadores.size()}"/>/<c:out value="${partido.numJugadoresNecesarios}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.centro.nombre}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.getFechaParseada()}"/>
                    </td>
                    <td style="text-align: center;">
                        <c:out value="${partido.getFechaCreacionParseada()}"/>
                    </td>
                    <td style="text-align: center;">
                        <spring:url value="/partidos/${partido.id}" var="verURL"></spring:url>
                        <a href="${verURL}" class="btn btn-default">Ver</a>
                    </td> 
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>

    <table class="center" border="0">
        <tr>
        	<td>
            <c:if test="${hasPrevious}">
                <td><a
                    style="margin-right:5px"  
                    href="/partidos?page=${pageNumber - 1}"
                    class="btn btn-default">Anterior</a>
            	</td>
            </c:if>

            <c:forEach begin="1" end="${totalPages+1}" var="i">
                <td><a style="margin-left:5px; margin-right:5px;" href="/partidos?page=${i-1}">${i}</a></td>
            </c:forEach>

            <c:if test="${pageNumber != totalPages}">
                <td><a
                	style="margin-left:5px;" 
                    href="/partidos?page=${pageNumber + 1}"
                    class="btn btn-default">Siguiente</a></td>
            </c:if>
            <td>
            <a style="margin-left:900px;" href="/partidos/new" class="btn btn-default">Crear nuevo partido</a>

        </tr>
     </table>
    
      
</petclinic:layout>
