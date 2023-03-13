<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="detallePartido">

<h1>Detalles del partido:</h1>
<div style="background-color: #0099bc; margin: 0 auto; border-radius: 50px;  text-align: center; display: flex; flex-wrap: wrap;">
   
    <div style="width: 50%;">
        <h3 style="color: #FFFFFF;">Nombre: </h3>
<h3  style="background-color: white;border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto; ">  <c:out value="${partido.nombre}"></c:out>   </h3>
<h3 style="color: #FFFFFF;">Fecha: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.fecha}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;">Sexo: </h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.sexo}"/></h3>
        <h3></h3>
<h3 style="color: #FFFFFF;"> Precio:</h3>
    <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
        <c:out value="${partido.precioPersona}"/></h3>
        <h3></h3>
    </div>
    <div style="width: 50%;">
<h3 style="color: #FFFFFF;">Descripción:</h3>
<h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
    <c:out value="${partido.descripcion}"/>
</h3>
<h3></h3>
<h3 style="color: #FFFFFF;"> Tipo: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;">
     <c:out value="${partido.tipo}"/></h3>
     <h3></h3>
<h3 style="color: #FFFFFF;">Localización: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.lugar}"/></h3>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Creador: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.creador.user.username}"/></h3>
    <h3></h3>
    <h3 style="color: #FFFFFF;">Número jugadores: </h3>
   <h3 style="background-color: white; border-radius: 5px; width: fit-content; text-align: center;  margin: 0 auto;"> 
    <c:out value="${partido.numJugadoresNecesarios}"/></h3>
    <h3></h3>
    </div>
    <div style=" margin: 0 auto;">
<button onclick="location.href='/jugadores'">Ver participantes</button>
<button onclick="location.href='/partidos'">Unirse al partido</button>
</div>

</div>
</petclinic:layout>
