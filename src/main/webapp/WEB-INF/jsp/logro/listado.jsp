<%@ page session="false" trimDirectiveWhitespaces="true" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
          <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
              <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

                <petclinic:layout pageName="logro">
                  <h2>Logros</h2>
                  <table id="logrosTabla" class="table table-striped" summary="Tabla de logros">
                    <thead>
                      <tr>
                        <th>Nombre</th>
                        <th>Descripcion</th>
                        <th>Imagen</th>
                        <th>Progreso</th>
                        <th>Metrica</th>
                        <th>Lo tengo</th>
                        <th></th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${logros}" var="logro">
                        <tr>
                          <td>
                            <c:out value="${logro.nombre}" />
                          </td>
                          <td>
                            <c:out value="${logro.descripcion}" />
                          </td>
                          <td>
                            <img src="${logro.imagen}" style="width: 80px; height:auto" alt="Imagen"/>
                          </td>
                          <td>
                            <c:out value="${progreso.get(logro.metrica)} / ${logro.threshold}" />
                          </td>
                          <td>
                            <c:out value="${logro.metrica}" />
                          </td>
                          <td>
                            <c:choose>
                              <c:when test="${conseguido.contains(logro)}">
                                <p class="glyphicon glyphicon-ok"></p>
                              </c:when>
                              <c:otherwise>
                                <p class="glyphicon glyphicon-remove"></p>
                              </c:otherwise>
                            </c:choose>
                          </td>

                          <c:if test="${esAdmin}">
                            <td>
                              <spring:url value="/logro/edit/{achievementId}" var="editUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(editUrl)}" class="glyphicon glyphicon-pencil"></a>
                            </td>
                            <td>
                              <spring:url value="/logro/delete/{achievementId}" var="deleteUrl">
                                <spring:param name="achievementId" value="${logro.id}" />
                              </spring:url>
                              <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash"></a>
                            </td>
                          </c:if>

                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>

                  <c:if test="${esAdmin}">
                    <a href="/logro/new" class="btn btn-default">Nuevo Logro</a>
                  </c:if>



                </petclinic:layout>