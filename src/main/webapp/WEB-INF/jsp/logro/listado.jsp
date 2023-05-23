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

  <c:if test="${mensajeExitoso != null}">
    <div class="alert alert-success alert-dismissible" style="padding-top: 2%;" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
          aria-hidden="true">&times;</span></button>
      <c:out value="${mensajeExitoso}" />
    </div>
  </c:if>

  <h1>Logros de <c:out value="${jugador.user.username}"/></h1>

  <div class="row">
    <c:if test="${jugador.equals(jugadorAutenticado)}">
      <c:if test="${logros.size() == 0}">
          Aún no se ha publicado ningún logro.
      </c:if>
      <c:if test="${logros.size() != 0}">
          <c:forEach items="${logros}" var="logro">
              <div class="col-md-4">
                <c:if test="${esAdmin}">
                  <spring:url value="/logro/edit/{achievementId}" var="editUrl">
                    <spring:param name="achievementId" value="${logro.id}" />
                  </spring:url>
                  <a href="${fn:escapeXml(editUrl)}" class="card-link">
                </c:if>
                  <c:if test="${progreso.get(logro.metrica) >= logro.threshold}">
                    <div class="card">
                      <div class="card-body">
                          <div class="content" style="margin-left: 35%; margin-bottom: 5%;">
                            <img style="width: 100px; height: 100px;" src="${logro.imagen}" />
                          </div>
                          <p class="card-text">
                              <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                            </p>                              
                          <p class="card-text">
                              <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                          </p>
                          <p class="card-text">
                              <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}" />
                          </p>
                          <br>
                          <div class="text-center">
                            <c:if test="${esAdmin}">
                              <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash glyphicon-4x" style="font-size: 2rem;"></a>
                            </c:if>
                          </div>
                      </div>
                  </div>
                  </c:if>

                  <c:if test="${progreso.get(logro.metrica) < logro.threshold}">
                    <div class="card-no-logro">
                      <div class="card-body">
                          <div class="content" style="margin-left: 35%; margin-bottom: 5%;">
                            <img style="width: 100px; height: 100px;" src="${logro.imagen}" />
                          </div>
                          <p class="card-text">
                              <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                            </p>                              
                          <p class="card-text">
                              <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                          </p>
                          <p class="card-text">
                              <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}" />
                          </p>
                          <br>
                          <div class="text-center">
                            <c:if test="${esAdmin}">
                              <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash glyphicon-4x" style="font-size: 2rem;"></a>
                            </c:if>
                          </div>
                      </div>
                  </div>
                  </c:if>
                  
              </div>
          </c:forEach>
      </c:if>
    </c:if>


    <c:if test="${!jugador.equals(jugadorAutenticado)}">
      <c:if test="${logros.size() == 0}">
          Aún no se ha publicado ningún logro.
      </c:if>
      <c:if test="${logros.size() != 0}">
          <c:forEach items="${logros}" var="logro">
              <div class="col-md-4">
                <c:if test="${esAdmin}">
                  <spring:url value="/logro/edit/{achievementId}" var="editUrl">
                    <spring:param name="achievementId" value="${logro.id}" />
                  </spring:url>
                  <a href="${fn:escapeXml(editUrl)}" class="card-link">
                </c:if>
                  <c:if test="${progreso.get(logro.metrica) >= logro.threshold}">
                    <div class="card">
                      <div class="card-body">
                        <div class="content" style="margin-left: 35%; margin-bottom: 5%;">
                          <img style="width: 100px; height: 100px;" src="${logro.imagen}" />
                        </div>
                          <p class="card-text">
                              <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                            </p>                              
                          <p class="card-text">
                              <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                          </p>
                          <p class="card-text">
                              <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}" />
                          </p>
                          <br>
                          <div class="text-center">
                            <c:if test="${esAdmin}">
                              <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash glyphicon-4x" style="font-size: 2rem;"></a>
                            </c:if>
                          </div>
                      </div>
                  </div>
                  </c:if>
                  
                  <c:if test="${progreso.get(logro.metrica) < logro.threshold}">
                    <div class="card-no-logro">
                      <div class="card-body">
                        <div class="content" style="margin-left: 35%; margin-bottom: 5%;">
                          <img style="width: 100px; height: 100px;" src="${logro.imagen}" />
                        </div>
                          <p class="card-text">
                              <strong>Nombre:</strong> <c:out value="${logro.nombre}"/>
                            </p>                              
                          <p class="card-text">
                              <strong>Descripción:</strong> <c:out value="${logro.descripcion}"/> 
                          </p>
                          <p class="card-text">
                              <strong>Progreso:</strong> <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}" />
                          </p>
                          <br>
                          <div class="text-center">
                            <c:if test="${esAdmin}">
                              <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash glyphicon-4x" style="font-size: 2rem;"></a>
                            </c:if>
                            
                          </div>
                      </div>
                  </div>
                </c:if>
              </div>
          </c:forEach>
      </c:if>
    </c:if>

</div>

  <c:if test="${esAdmin}">
    <div class="text-center">
      <a href="/logro/new" class="btn btn-default"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Nuevo logro</a>
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
      animation: pulse 1.5s infinite;
  }

  .card-no-logro {
      box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
      border-width: 2px;
      border-style: solid;
      border-color: #0099BB;
      background-color: #fb8686;
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