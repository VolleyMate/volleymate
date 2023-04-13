<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="confirmarCompra">
    <div class="container" style="background-color: #0099bb3e; padding: 20px; width: 60%; margin-top: 5%; border-radius: 20px;">
        <div class="row">
            <div class="col-md-12 col-xl-6 text-center mx-auto" >
                <h2>Confirmar compra</h2>
                <p style="padding-bottom: 2%;">¿Está seguro de que desea comprar ${paquete} por ${precio}€?</p>
                <div class="row">
                    <div class="col-md-6">
                        <form:form method="POST" action="/pagos/autorizar_pago" modelAttribute="orderDetail">
                            <input type="text" name="descripcion" value="${paquete}" />
                            <input type="text" name="numVolleys" value="${numVolleys}" />
                            <input type="text" name="total" value="${precio}" />
                            <input type="submit" value="Continuar al pago" class="btn btn-default" style="margin: 5%; background-color: green; outline-color:greenyellow;">
                        </form:form>
                    </div>
                    <div class="col-md-6">
                        <c:if test="${idCompra == 1}">
                            <a href="/tienda" class="btn btn-default" style="margin: 5%;">Cancelar</a>
                        </c:if>
                        <c:if test="${idCompra != 1}">
                            <a href="/tienda/volleys" class="btn btn-default" style="margin: 5%;">Cancelar</a>
                        </c:if>
                        
                    </div>
                </div>                   
            </div> 
        </div> 
    </div>
</petclinic:layout>
