<%@ page session="false" trimDirectiveWhitespaces="true" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
          <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
              <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

                <petclinic:layout pageName="logro">
                  <h2>Logros de ${jugador.user.username}</h2>
                  <table id="logrosTabla" class="table table-striped" summary="Tabla de logros">
                    <thead>
                      <tr>
                        <th>Nombre</th>
                        <th>Descripcion</th>
                        <th>Imagen</th>
                        <th></th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${jugador.logros}" var="logro">
                        <tr>
                          <td>
                            <c:out value="${logro.nombre}" />
                          </td>
                          <td>
                            <c:out value="${logro.descripcion}" />
                          </td>
                          <td>
                            <img src="${logro.imagen}" style="width: 80px; height:auto" alt="Imagen" />
                          </td>

                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>

                </petclinic:layout>