<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<petclinic:layout pageName="chat">

<script>
    var refreshIntervalId = setInterval(function() {
        if (!document.getElementById('contenidoMensaje').value) {
            location.reload();
        }
    }, 3000);

    function scrollDown() {
        var chatMensajes = document.getElementById('chat-mensajes');
        chatMensajes.scrollTop = chatMensajes.scrollHeight;
    }

    window.onload = scrollDown;
</script>

    <h2>Chat</h2>

    <div style="text-align: center; color: #FF0000">
        <c:out value="${error}"/>
    </div>
    <div id="chat" style="display: grid; grid-template-rows: 1fr, 1fr;">
        <div id="chat-mensajes" style="display: grid; grid-template-columns: repeat(2,1fr); max-height: 400px; overflow: scroll;">
            <c:forEach items="${mensajes}" var="mensajeEach">
                <c:if test="${mensajeEach.emisor.user.username.equalsIgnoreCase(username)}">
                    <br>
                    <div style="grid-column: 2; background-color: #0099BB; border-radius: 15px; margin: 10px; text-align: center; border:1px dotted #0099; color:white">
                        <br>
                        <c:out value="${mensajeEach.contenidoMensaje}"/>
                        <c:out value=" | "/>
                        <c:out value="${mensajeEach.getFechaParseada()}"/>
                    </div>
                </c:if>
                <c:if test="${!mensajeEach.emisor.user.username.equalsIgnoreCase(username)}">
                    <div style="grid-column: 1; border-color: #0099BB; border-width: 10px; background-color: #0099; border-radius: 15px; margin: 10px; text-align: center; border:1px dotted #000; color:white"">
                        <div style="border-radius: 15px; text-align: left; margin-left: 5%;">
                            <c:out value="${mensajeEach.emisor.user.username}"/>
                            <br>
                        </div>
                        <c:out value="${mensajeEach.contenidoMensaje}"/>
                        <c:out value=" | "/>
                        <c:out value="${mensajeEach.getFechaParseada()}"/> 
                    </div>
                </c:if>
            </c:forEach>
        </div> 
        <div style="grid-row: 2;">
            <c:url value="/chat/enviar/${partidoId}" var="enviarMensaje"/>
            <form:form action="${enviarMensaje}" method="post">

                <div class="form-group">
                    <label for="contenidoMensaje">Mensaje</label>
                    <input type="text" class="form-control" id="contenidoMensaje" name="contenidoMensaje" placeholder="Mensaje">
                </div>
                <button class="btn btn-default" type="submit">Enviar</button>
            
            </form:form>
        </div>
    </div>

    <div style="text-align: center; color: #FF0000">
        <c:forEach var="error" items="${errors}">
          <c:out value="${error} "/>
      </c:forEach>
    </div>
      
</petclinic:layout>