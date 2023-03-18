<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<petclinic:layout pageName="chat">
    <h2>Chat</h2>

    <table id="partidosTable" class="table table-striped" summary="listadoPartidos">
        <thead>
        </thead>
        <tbody>
            <c:forEach items="${mensajes}" var="mensajeEach">
                <tr style="border: 1px solid black; padding: 5px; border-radius: 50px;">
                    <td style="text-align: center;">
                        <c:out value="${mensajeEach.emisor.user.username}"/>
                        <c:out value=" | "/>
                        <c:out value="${mensajeEach.contenidoMensaje}"/>
                        <c:out value=" | "/>
                        <c:out value="${mensajeEach.getFechaParseada()}"/>
                    </td>
                </tr>
                <tr style="height: 15px;"></tr>
            </c:forEach>
        </tbody>
    </table>   


    <c:url value="/chat/enviar/${partidoId}" var="enviarMensaje"/>
    <form:form action="${enviarMensaje}" method="post">

        <div class="form-group">
            <label for="contenidoMensaje">Mensaje</label>
            <input type="text" class="form-control" id="contenidoMensaje" name="contenidoMensaje" placeholder="Mensaje">
        </div>
        <button class="btn btn-default" type="submit">Enviar</button>
    
    </form:form>


    <div style="text-align: center; color: #FF0000">
        <c:forEach var="error" items="${errors}">
          <c:out value="${error} "/>
      </c:forEach>
    </div>
      
</petclinic:layout>