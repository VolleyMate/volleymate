<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="tiendaVolleys">
    <div class="container" style="background-color: #0099bb3e; padding: 20px; width: 100%; margin-top: 5%; border-radius: 20px;">
        <div class="row">
            <div class="col-md-12 col-xl-6 text-center mx-auto" >
                <h1>Tienda de Volleys</h1>
                <h3>
                    <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">
                    <c:out value="${jugador.volleys}"/>
                </h3>
                <p style="padding-bottom: 2%; font-size: large;">Aquí tienes algunos paquetes de Volleys:</p>
            </div> 
            <div class="container">
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <div class="card-body p-4">
                                <a href="/tienda/confirmaCompra/2">
                                    <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/300.png"/>
                                    <h3>4,99€</h3>
                                </a>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <div class="card-body p-4">
                                <a href="/tienda/confirmaCompra/3">
                                    <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/450.png"/>
                                    <h3>6,50€</h3>
                                </a>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <div class="card-body p-4">
                                <a href="/tienda/confirmaCompra/4" >
                                    <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/1100.png"/>
                                    <h3>14,50€</h3>
                                </a>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-6 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <div class="card-body p-4">
                                <a href="/tienda/confirmaCompra/5">
                                    <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/1550.png"/>
                                    <h3>19,99€</h3>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <div class="card-body p-4">
                                <a href="/tienda/confirmaCompra/6" >
                                    <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/4100.png"/>
                                    <h3>49,99€</h3>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div> 
    </div>
</petclinic:layout>

<style>
    .card {
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
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
        background-color: #e6f4f2;
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
