<%@ page session="false" trimDirectiveWhitespaces="true" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
          <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
              <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
              <%@ page contentType="text/html; charset=UTF-8" %>

                <petclinic:layout pageName="logro">
                <c:if test="${jugador.equals(jugadorAutenticado)}">
                  <h2>Logros</h2>
                  <div>
                    <c:forEach items="${logros}" var="logro">
                      <div class="col-md-3">
                        <c:if test="${conseguido.contains(logro)}">
                          <div class="card">
                        </c:if>
                        <c:if test="${!conseguido.contains(logro)}">
                          <div class="card-no-logro">
                        </c:if>
                              <div>
                                <img style="width: 50px; height: 50px;" src="${logro.imagen}"/>
                              </div>
                              <div class="card-body">                  
                                  <p class="card-text">
                                      <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                                    </p>                              
                                  <p class="card-text">
                                      <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                                  </p>
                                  
                                  <p class="card-text">
                                      <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}"/>
                                  </p>

                                  <c:if test="${esAdmin}">
                                    <br>
                                    
                                      <spring:url value="/logro/edit/{achievementId}" var="editUrl">
                                        <spring:param name="achievementId" value="${logro.id}" />
                                      </spring:url>
                                      <a href="${fn:escapeXml(editUrl)}" class="glyphicon glyphicon-pencil"></a>
                                    
                                      <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                        <spring:param name="achievementId" value="${logro.id}" />
                                      </spring:url>
                                      <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash"></a>
                                  
                                  </c:if>
                              </div>
                          </div>
                      </div>
                    </c:forEach>
                  </c:if>
                  <c:if test="${!jugador.equals(jugadorAutenticado)}">
                    <h2>Logros de <c:out value="${jugador.user.username}"/></h2>
                  <div>
                    <c:forEach items="${jugador.logros}" var="logro">
                      <div class="col-md-3">
                          <div class="card">
                              <div>
                                <img style="width: 50px; height: 50px;" src="${logro.imagen}"/>
                              </div>
                              <div class="card-body">                  
                                  <p class="card-text">
                                      <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                                    </p>                              
                                  <p class="card-text">
                                      <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                                  </p>
                                  
                                  <c:if test="${esAdmin}">
                                  <p class="card-text">
                                      <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}"/>
                                  </p>

                                  
                                    <br>
                                    
                                      <spring:url value="/logro/edit/{achievementId}" var="editUrl">
                                        <spring:param name="achievementId" value="${logro.id}" />
                                      </spring:url>
                                      <a href="${fn:escapeXml(editUrl)}" class="glyphicon glyphicon-pencil"></a>
                                    
                                      <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                        <spring:param name="achievementId" value="${logro.id}" />
                                      </spring:url>
                                      <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash"></a>
                                  
                                  </c:if>
                              </div>
                          </div>
                      </div>
                    </c:forEach>
                  </c:if>
                    
                  </div>
                  <c:if test="${esAdmin}">
                    <div class="text-right">
                      <a href="/logro/new" class="btn btn-default"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Nuevo logro</a>
                  </div>
                </c:if>
                </petclinic:layout>
  
<style>
  .card {
      box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
      border-width: 2px;
      border-style: solid;
      border-color: #0099BB;
      background-color: #a9eac0;
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
      background-color: #a9eac0;
      animation: pulse 1.5s infinite;
  }

  .card-no-logro {
      box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
      border-width: 2px;
      border-style: solid;
      border-color: #0099BB;
      background-color: #aab2b1;
      margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */
      border-radius: 10px;
      padding-top: 4%;
      padding-bottom: 4%;
      padding-left: 4%;
      padding-right: 4%;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      transition: transform 0.2s ease-in-out;
  }
  .card-no-logro:hover {
      transform: scale(1.1);
      cursor: pointer;
      background-color: #aab2b1;
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