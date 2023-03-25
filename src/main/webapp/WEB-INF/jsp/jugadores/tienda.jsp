<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="tienda">
    <div class="container">
        <div class="row">
            <div class="col-md-6" style="padding: 2%;">
                <div class="col-md-8 col-xl-6 text-center mx-auto" style="background-color: #0099bb3e; padding: 20px; width: 100%; margin: top:5%;border-radius: 20px;">
                    <h2>Comprar Volleys</h2>
                    <p style="padding-bottom: 2%;">¿Te has quedado sin Volleys? Compra algunos aquí</p>
                    <h3><c:out value="Volleys disponibles: ${jugador.volleys}"/></h3>
                    <a href="/tienda/volleys" class="btn btn-default">Comprar volleys</a>
                </div>      
            </div>
            <div class="col-md-6" style="padding: 2%;">
                <div class="col-md-8 col-xl-6 text-center mx-auto" style="background-color: #0099bb3e; padding: 20px; width: 100%; margin: top:5% ;;border-radius: 20px;">
                    <h2>Plan Premium</h2>
                    <p style="padding-bottom: 2%;">Inscríbete al plan Premium para disfrutar de VolleyMate al completo</p>
                    <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
                        <div class="card h-100">
                            <div class="card-body flex-grow-0 p-4"><span class="badge bg-primary text-uppercase mb-2">pREMIUM</span>
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
                                <button class="btn btn-default" type="button">Comprar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
   
</petclinic:layout>



