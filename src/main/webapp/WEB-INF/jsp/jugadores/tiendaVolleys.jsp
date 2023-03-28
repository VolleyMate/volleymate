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
                <h2>Tienda de Volleys</h2>
                <h3>
                    <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">
                    <c:out value="${jugador.volleys}"/>
                </h3>
                <p style="padding-bottom: 2%;">Aquí tienes algunos paquetes de Volleys:</p>
            </div> 
            <div class="container">
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <img class="card-img-top w-100 d-block fit-cover" style="width: 100px; height: 100px;" src="/resources/images/300.png"/>
                            <div class="card-body p-4">
                                <button class="btn btn-default-tienda" style="width: 100px;" type="button">4,99€</button>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <img class="card-img-top w-100 d-block fit-cover" style="width: 100px; height: 100px;" src="/resources/images/450.png"/>
                            <div class="card-body p-4">
                                <button class="btn btn-default-tienda" style="width: 100px;" type="button">6,50€</button>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-4 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <img class="card-img-top w-100 d-block fit-cover" style="width: 100px; height: 100px;" src="/resources/images/1100.png"/>
                            <div class="card-body p-4">
                                <button class="btn btn-default-tienda" style="width: 100px;" type="button">14,50€</button>
                            </div>
                        </div>
                    </div>
                </div> 
                <div class="col-md-6 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <img class="card-img-top w-100 d-block fit-cover" style="width: 100px; height: 100px;" src="/resources/images/1550.png"/>
                            <div class="card-body p-4">
                                <button class="btn btn-default-tienda" style="width: 100px;" type="button">19,99€</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 text-center mx-auto">
                    <div class="card" style="margin: 5%;">
                        <div class="card-body p-4">
                            <img class="card-img-top w-100 d-block fit-cover" style="width: 100px; height: 100px;" src="/resources/images/4100.png"/>
                            <div class="card-body p-4">
                                <button class="btn btn-default-tienda" style="width: 100px;" type="button">49,99€</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div> 
    </div>
</petclinic:layout>
