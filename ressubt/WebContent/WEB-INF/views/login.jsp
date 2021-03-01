<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/login_style.css">
</head>
<body>
	<form action="/ressubt/control" method="post">
		<input type="hidden" name="action" value="Login">
		<fieldset>
			Email: <input type="text" name="email" <c:if test="${not empty email}">value="${email}"</c:if>/><br> 
			Senha: <input type="password" name="senha" <c:if test="${not empty senha}">value="${senha}"</c:if>/><br> 
			<button>Ok</button>
			<c:if test="${not empty message}"><h2>${message}</h2></c:if>
		</fieldset>
	</form>
	<script src="js/login.js"></script>
</body>
</html>