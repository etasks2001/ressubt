<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>

	<body>

		<form action="/ressubt/control" method="post">
			<input type="hidden" name="action" value="Login">
			<fieldset>

				Email: <input type="text" name="email" /><br>
				Senha: <input type="password" name="senha" /><br>
				<button>Ok</button>
			</fieldset>
		</form>
		<script src="js/login.js""></script>
</body>
</html>