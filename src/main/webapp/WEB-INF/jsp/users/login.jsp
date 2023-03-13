<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="login">
	<head>
        <title>Login Form</title>
    </head>
    <body>

        <div class="main">
            <div class="box">

                <h1 class="title">Inicio</h1>

                <c:url value="/login" var="loginUrl"/>


				<form:form modelAttribute="user" class="form-horizontal " id="add-jugador-form">
                    
					<c:if test="${param.error != null}">
                        <p class="error">Username y password incorrectos, intentalo nuevamente.</p>
                    </c:if>

                    <c:if test="${param.logout != null}">
                        <p class="logout">La sesión ha sido cerrada correctamente.</p>
                    </c:if>

                    <div>
						<petclinic:inputField label="Username" name="username"/>
                    </div>

                    <div>
                        <petclinic:inputField label="Password" name="password"/>
                    </div>

                    <button type="submit" class="btn">Log in</button>
                </form:form>

            </div>
        </div>
		<style>
			div.main {    
				font-family: Segoe UI Light;
				font-size: 11px;
			}

			div.box { 
				border: #607D8B solid 1px;
				width: 300px;
				margin: 0 auto;
				border-radius: 5px;
				padding: 20px;
			}

			form input {
				width: 100%;
				height: 20px;
				border-radius: 3px;
				border: #607D8B solid 1px;
			}

			h1.title {
				text-align: center;
				border-bottom: #607D8B solid 1px;
				color: #607D8B;
			}

			button.btn {
				width: 100%;
				height: 30px;
			}

			p.error {
				font-size: 11px;
				color: red;
				font-style: italic;
				font-family: Segoe UI Semibold;   
			}

			p.logout {
				font-size: 11px;
				color: green;
				font-style: italic;
				font-family: Segoe UI Semibold;   
			}

			form label { display: block; }
			form div { margin-bottom: 10px; }
		</style>
    </body>
</petclinic:layout>