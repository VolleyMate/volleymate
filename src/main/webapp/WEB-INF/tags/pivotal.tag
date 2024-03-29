<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<br/>
<br/>
<footer>
  <div class="container">
      <head>
        <style>
            footer {
            position: fixed;
            bottom: 0;
            padding: 10px;
            height: 100 px;
        }
        /* media queries para pantallas pequeñas */
        @media (max-width: 767px) {
          footer {
            position: static;
            height: auto;
          }
          .row {
            margin-bottom: 20px;
          }
          .col-12 {
            text-align: center;
          }
        }
        </style>
    </head>
      <div class="row">
          <div class="col-12 text-center">
              <img src="<spring:url value="/resources/images/footer2.png" htmlEscape="true" />" alt="Sponsored by Pivotal" style="max-width:90px;"/>
          </div>
      </div>
      <br/>
      <div class="row text-center"  style="background-color: #D8D8D8;">
      <div class="col-12">
        <a href="https://www.instagram.com/volleymate.es/">
          <img src="<spring:url value="/resources/images/instagram.png" htmlEscape="true" />" alt="Instagram de VolleyMate" style="max-width:40px;">
        </a>
        <a href="https://twitter.com/volleymatees">
          <img src="<spring:url value="/resources/images/twitter.png" htmlEscape="true" />" alt="Twitter de VolleyMate" style="max-width:50px;">
        </a>
        <a href="<spring:url value="/terminos" htmlEscape="true"/>"> Terminos y condiciones </a>
        
      </div>
    </div>
  </div>
</footer>

