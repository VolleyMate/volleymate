<%@ page session="false" trimDirectiveWhitespaces="true" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

          <petclinic:layout pageName="logro">

            <jsp:body>

              <h2>
                <c:if test="${logro['new']}">Nuevo </c:if> Logro
              </h2>

              <form:form modelAttribute="logro">

                <input type="hidden" name="id" value="${logro.id}" />

                <div class="form-group has-feedback">

                  <p>Nombre</p>
                  <petclinic:inputField name="nombre" label=""></petclinic:inputField>

                  <p>Descripcion</p>
                  <sieteislas:inputField name="descripcion" label="">
                    </petclinic:inputField>

                    <p>Imagen</p>
                    <petclinic:inputField name="imagen" label=""></petclinic:inputField>

                    <p>Meta</p>
                    <petclinic:inputField name="threshold" label=""></petclinic:inputField>

                </div>

                <div class="form-group">

                  <div class="col-sm-offset-2 col-sm-10">

                    <c:choose>

                      <c:when test="${logro['new']}">
                        <button class="btn btn-default" type="submit">
                          Crear Logro</button>
                      </c:when>

                      <c:otherwise>
                        <button class="btn btn-default" type="submit">
                          Actualizar Logro</button>
                      </c:otherwise>

                    </c:choose>

                  </div>

                </div>

              </form:form>

            </jsp:body>

          </petclinic:layout>