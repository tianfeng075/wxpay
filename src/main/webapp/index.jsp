<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎</title>
</head>
<body>
<h2>Hello World!</h2>

<form action="login.do">
	用户名：<input id="username" name="username" type="text"></input><br>
	密  码：<input id="password" name="password" type="password"></input><br>
	<input type="submit">
</form>
<br><br>
<form action="wxip.do">
	获取微信服务器IP<input type="submit">
</form>
<form action="delmenu.do">
	删除菜单<input type="submit">
</form>
<form action="addmenu.do">
	重置菜单<input type="submit">
</form>
<!-- <span>当前IP：<%=request.getRemoteAddr() %></span> -->
</body>
</html>
