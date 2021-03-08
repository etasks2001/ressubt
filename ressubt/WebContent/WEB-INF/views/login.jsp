<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="css/login/style.css">
	</head>

	<body>
		<form action="/ressubt/control" method="post">
			<input type="hidden" name="action" value="Login">
			<fieldset>
			<p><label for="email">Email</label><input id="email" type="text" name="email" autofocus="autofocus" <c:if test="${not empty email}">value="${email}"</c:if>/></p>
			<p><label for="senha">Senha</label><input id="senha" type="password" name="senha" <c:if test="${not empty senha}">value="${senha}"</c:if>/></p>
			<p><button>Ok</button></p>
			
				<c:if test="${not empty message}">
					<h2>${message}</h2>
				</c:if>
			</fieldset>
		</form>
		<script src="js/login.js"></script>
	</body>



	</html>