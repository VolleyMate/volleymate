<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main-navbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>

		<div class="navbar-collapse collapse" id="main-navbar">

			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<sec:authorize access="isAuthenticated()">

					<petclinic:menuItem active="${name eq 'listaJugadores'}" url="/listaJugadores"
						title="listaJugadores">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span>Buscar jugador</span>
					</petclinic:menuItem>

				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<petclinic:menuItem active="${name eq 'partidos'}" url="/partidos"
						title="partidos">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Partidos</span>
					</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">

					<petclinic:menuItem active="${name eq 'listaCentros'}" url="/centros"
						title="listaCentros">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Centros</span>
					</petclinic:menuItem>

				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<petclinic:menuItem active="${name eq 'tienda'}" url="/tienda"
						title="tienda">
						<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
						<span>Tienda</span>
					</petclinic:menuItem>				
				</sec:authorize>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/jugadores/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"data-toggle="dropdown"> 
					<span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> 
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu" style="border-radius: 10px;">
							<li>
								<div class="navbar-login">
									<div class="row" >
										<div class="col-6 h5">
											<p class="text-center">
												<a href="<c:url value="/jugadores/notificaciones" />"
													style="border-radius: 20px;" class="btn btn-green"><span class="glyphicon glyphicon-bell"></span> Notificaciones</a>
											</p>
										
											<p class="col-6 h5 text-center">
												<a href="<c:url value="/jugadores" />"
													style="border-radius: 20px;" class="btn btn-default"><span class="glyphicon glyphicon-share-alt"></span> Ver perfil</a>
											</p>

											<p class="col-6 h5 text-center">
												<a href="<c:url value="/logout" />"
													style="border-radius: 20px;" class="btn btn-red"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>  Cerrar sesion</a>
											</p>			
									
										</div>
									</div>
								</div>
							</li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-info btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>

	</div>
</nav>
