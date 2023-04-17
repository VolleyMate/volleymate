<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="tienda">
    <div class="container">
        <c:if test="${mensajeExito != null}">
            <div style="background-color: greenyellow;">
                <c:out value="${mensajeExito}" />
            </div>
        </c:if>
        <c:if test="${mensajeError!=null}">
            <div style="background-color: red;">
                <c:out value="${mensajeError}" ></c:out>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6" style="padding: 2%;">
                <div class="col-md-8 col-xl-6 text-center mx-auto" style="background-color: #0099bb3e; padding: 20px; width: 100%; height: 350px; margin-top: 5%; border-radius: 20px;">
                    <h2>Tienda de Volleys</h2>
                    <p style="padding-bottom: 2%;">Dispones de: </p>
                    <h3>
                        <img src="/resources/images/pelotaVolley.png" alt="imagen de volleys" style="width: 20px; height: 20px; margin-right: 10px;">
                        <c:out value="${jugador.volleys}"/>
                    </h3>
                    <div class="row">
                        <div class="col-md-6 tienda">
                            <a href="/tienda/volleys">
                                <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/pelotaVolley.png"/>
                                <h3>Comprar Volleys</h3>
                            </a>
                        </div>
                        <div class="col-md-6 tienda">
                            <a href="/tienda/aspectos">
                                <img class="card-img-top" style="width: 100px; height: 100px; margin: 5%;" src="/resources/images/perfilPorDefecto.png"/>
                                <h3>Comprar aspectos</h3>
                            </a>
                        </div>
                    </div>
                </div>      
            </div>
            <div class="col-md-6" style="padding: 2%;">
                <div class="col-md-8 col-xl-6 text-center mx-auto" style="background-color: #0099bb3e; padding: 20px; width: 100%; height: 350px; margin-top: 5%; border-radius: 20px;">
                    <h2>Plan Premium</h2>
                    <c:if test="${jugador.premium != true}">
                        <p style="padding-bottom: 2%; margin-top: 10%;">Inscríbete al plan Premium para disfrutar de VolleyMate al completo</p>
                        <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
                        <div class="card h-100">
                            <div class="card-body flex-grow-0 p-4"><span class="badge bg-primary text-uppercase mb-2">PREMIUM</span>
                                <h4 class="display-4 fw-bold card-title">7,99€<span class="fs-3 fw-normal text-muted">/mes</span></h4>
                            </div>
                            <div class="card-footer d-flex flex-column flex-grow-1 justify-content-between p-4">
                                <div>
                                    <ul class="list-unstyled">
                                        <li class="d-flex mb-2"><span class="bs-icon-xs bs-icon-rounded bs-icon-primary-light bs-icon me-2"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-check-lg">
                                                    <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425a.247.247 0 0 1 .02-.022Z"></path>
                                                </svg></span><span>Volleys infinitos</span></li>
                                        <li class="d-flex mb-2"><span class="bs-icon-xs bs-icon-rounded bs-icon-primary-light bs-icon me-2"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-check-lg">
                                                      <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425a.247.247 0 0 1 .02-.022Z"></path>
                                                </svg></span><span>Aspectos gratuitos&nbsp;</span></li>
                                        <li class="d-flex mb-2"></li>
                                    </ul>
                                </div>
                                <a href="/tienda/confirmaCompra" class="btn btn-default">Comprar</a>
                            </div>
                        </div>
                        </div>
                    </c:if>
                    <c:if test="${jugador.premium == true}">
                        <h1 style="text-align: center; padding-top: 15%;">YA ERES USUARIO PREMIUM</h1>
                    </c:if>
                </div>
            </div>
        </div>

    </div>
</petclinic:layout>

<style>
    .tienda {
        width: 40%;
        box-shadow: 0 0 10px rgba(16, 88, 139, 0.1);
        border-width: 2px;
        border-style: solid;
        border-color: #0099BB;
        margin-bottom: 20px; /* Agrega un margen inferior de 20 píxeles */
        margin-left: 5%;
        margin-right: 5%;
        border-radius: 10px;
        padding-top: 4%;
        padding-bottom: 4%;
        padding-left: 4%;
        padding-right: 4%;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }
    .tienda:hover {
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




