<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<style>
  
  label {
  display: inline-block;
  padding: 0;
  cursor: pointer;
  vertical-align: middle;
}

label.reset {
  font-size: 10px;
  border: 1px solid #000;
  border-radius: 5px;
  margin: 10px 5px;
  padding: 2px 3px;
}

label.star {
  width: 30px;
  height: 27px;
}

input[name=rating] {
  display: none;
}

input[type=radio]+label.star svg path {
  fill: #fe0
}


/* estrellas a la derecha del radiobutton checked van blanco */

input[type=radio]:checked~label.star svg path {
  fill: #fff;
}
</style>

<petclinic:layout pageName="crearValoracion">
  <div class="container">
    <h1 style="text-align: center; font-size: 1.5em;">
      <strong>Crea tu valoración para el usuario</strong>
    </h1>
    <div style="background-color: #0099bb3e; padding: 20px; width: 75%; margin: 0 auto;border-radius: 20px;">
    <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card" style="border-radius: 20px;">
          <div class="card-body">
            <form:form modelAttribute="valoracion" class="form-horizontal" id="crear_valoracion">
              <form:hidden path="id"/>
              <form:hidden path="ratedPlayer"/>
              <form:hidden path="ratingPlayer"/>
              <form:hidden path="ratingPlayer"/>
              <div class="form-group" >
                <input type="hidden" id="puntuacion" name="puntuacion" value="" />
                
                <div style="text-align: center;">

                  <input id=rating0 type=radio value=0 name=rating checked />

                  <label class=star for=rating1>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 275">
                      <path stroke="#605a00" stroke-width="15" d="M150 25l29 86h90l-72 54 26 86-73-51-73 51 26-86-72-54h90z" />
                    </svg>
                  </label>
                  <input id=rating1 type=radio value=1 name=rating />
                  
                  <label class=star for=rating2>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 275">
                      <path stroke="#605a00" stroke-width="15" d="M150 25l29 86h90l-72 54 26 86-73-51-73 51 26-86-72-54h90z" />
                    </svg>
                  </label>
                  <input id=rating2 type=radio value=2 name=rating />
                  
                  <label class=star for=rating3>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 275">
                      <path stroke="#605a00" stroke-width="15" d="M150 25l29 86h90l-72 54 26 86-73-51-73 51 26-86-72-54h90z" />
                    </svg>
                  </label>
                  <input id=rating3 type=radio value=3 name=rating />
                  
                  <label class=star for=rating4>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 275">
                      <path stroke="#605a00" stroke-width="15" d="M150 25l29 86h90l-72 54 26 86-73-51-73 51 26-86-72-54h90z" />
                    </svg>
                  </label>
                  <input id=rating4 type=radio value=4 name=rating />
                  
                  <label class=star for=rating5>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 275">
                      <path stroke="#605a00" stroke-width="15" d="M150 25l29 86h90l-72 54 26 86-73-51-73 51 26-86-72-54h90z" />
                    </svg>
                  </label>
                  <input id=rating5 type=radio value=5 name=rating />

                </div>


              </div>
              <div class="form-group text-center">
                <form:label path="comentario" style="font-size: large;">Haz algún comentario si lo ves necesario</form:label>
                <form:textarea path="comentario" class="form-control" style="border-radius: 20px; margin: 0 2.2%;" rows="4"></form:textarea>
              </div>
            
              <div class="form-group text-center">
                <button class="btn btn-md btn-default"
                type="submit">Crear valoración</button>
              
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
        <div style="text-align: center; color: #FF0000;">
            <c:out value="${error} "/>
        </div>
      </div>
    </div>
  </div>
  
</petclinic:layout>

<script>
  // para todos los radiobutton rating agregar un on change
const changeRating = document.querySelectorAll('input[name=rating]');
changeRating.forEach((radio) => {
  radio.addEventListener('change', getRating);
});

//Guardar el valor de la estrella mandándolo al postmapping del controlador
function getRating() {
  let estrellas = document.querySelector('input[name=rating]:checked').value;
  document.getElementById("puntuacion").value = estrellas;
}
</script>