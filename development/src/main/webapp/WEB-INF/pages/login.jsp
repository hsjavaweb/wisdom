<%--
  Created by IntelliJ IDEA.
  User: OnlyLoveYou
  Date: 2017/12/10
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        用户名:<input type="text" name="username"/><br/>
        密  码:<input type="text" name="password"><br/>
        <input type="submit" value="登录">
    </form>
</body>
</html>
