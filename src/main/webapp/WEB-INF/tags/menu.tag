<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">

		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>


				<petclinic:menuItem active="${name eq 'misPartidos'}" url="/jugadores/mispartidos"
					title="misPartidos">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Mis partidos</span>
				</petclinic:menuItem>


				<petclinic:menuItem active="${name eq 'partidos'}" url="/partidos"
					title="partidos">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Partidos</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'crearPartido'}" url="/partidos/new"
					title="partidos">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span>Crear partido</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'notificaciones'}" url="/jugadores/notificaciones"
					title="notificaciones">
					<span class="glyphicon glyphicon-bell" aria-hidden="true"></span>
					<span>Mis notificaciones</span>
				</petclinic:menuItem>				

				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"data-toggle="dropdown"> 
					<span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> 
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-6 h5">
											<p class="text-center">
												<a href="<c:url value="/jugadores" />"
													class="btn btn-info btn-sm"><span class="glyphicon glyphicon-share-alt"></span> Ver perfil</a>
											</p>
											<p class="col-6 h5 text-center">
												<a href="<c:url value="/logout" />"
													class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
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
