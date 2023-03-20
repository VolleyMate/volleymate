<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="misPartidos">
    <h2>BIENVENIDO A LA TIENDA DE VOLLEYMATE</h2>
    <h3>Comprar volleys</h3>
    <a href="/tienda/volleys" class="btn btn-default">Comprar volleys</a>
    <h3>Premium</h3>
    <a href="/tienda/premium" class="btn btn-default">Suscríbete a Premium</a>
</petclinic:layout>
