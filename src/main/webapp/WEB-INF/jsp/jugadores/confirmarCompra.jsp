<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="confirmarCompra">
    <div class="container" style="background-color: #0099bb3e; padding: 20px; width: 60%; margin-top: 5%; border-radius: 20px;">
        <div class="row">
            <div class="col-md-12 col-xl-6 text-center mx-auto" >
                <h2>Confirmar compra</h2>
                <p style="padding-bottom: 2%;">¿Está seguro de que desea comprar ${paquete} por ${precio}€?</p>
                <div class="row">
                    <div class="col-md-6">
                        <a href="redirect:/" class="btn btn-default" style="margin: 5%; background-color: green; outline-color:greenyellow;">Comprar</a>
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
