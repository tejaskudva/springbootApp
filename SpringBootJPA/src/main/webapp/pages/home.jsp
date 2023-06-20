<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="addAlien">
	ADD ALIEN<br>
		<input type="text" name="aid"><br><br>
		<input type="text" name="aname"><br><br>
		<input type="text" name="tech"><br><br>
		<input type="submit"><br><br>
	</form>
	
	<form action="getAlien">
	GET ALIEN<br>
		<input type="text" name="aid"><br><br>
		<input type="submit"><br>
	</form>
</body>
</html>