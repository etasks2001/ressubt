<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="">
		<input type="hidden" name="sk" />
		<fieldset>
			<legend>Per�odo</legend>
			Mes:<input type="text" name="mes" /><br> Ano:<input type="text"
				name="ano" /><br>
		</fieldset>

		<fieldset>
			Nome:<input type="text" name="nome" /><br> CNPJ:<input
				type="text" name="cnpj" /><br> Insc. Est.<input type="text"
				name="ie" /><br> UF: <select>
				<option value="SP">S�O PAULO</option>
				<option value="RJ">RIO DE JANEIRO</option>
			</select><br> Munic�pio: <select>
				<option value="1234567">S�o Paulo</option>
			</select><br> Vers�o Leiaute: <input type="text" name="cod_ver" /><br>
			Finalidade: <select>
				<option value="00">Remessa regular de arquivo</option>
				<option value="01">Remessa de arquivo requerido por
					intima��o do Fisco</option>
				<option value="02">Remessa de arquivo para substitui��o de
					anterior ACOLHIDO e com Visto Eletr�nico</option>
			</select><br>
			<br>
		</fieldset>
		<button type="submit">Gravar</button>

	</form>

	<script src="js/contribuinte.js"></script>
</body>


</html>