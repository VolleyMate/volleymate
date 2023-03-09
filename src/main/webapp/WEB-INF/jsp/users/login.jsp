<!DOCTYPE html>
<html>
<head>
<title>Bienvenido</title>
</head>
<body>
	<h1>probando cosas</h1>

	<form action="login" method="post">
		<table>
			<tr style="color: red;">
				<td></td>
				<td>${SPRING_SECURITY_LAST_EXCEPTION.message}</td>
			</tr>
			<tr>
				<td>User name:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
			</tr>
		</table>
	</form>
</body>
</html>