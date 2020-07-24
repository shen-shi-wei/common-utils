<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <script type="text/javascript">
        function st(){
            window.location.href="${pageContext.request.contextPath}/tim/startCamera";
        }

        function en(){
            window.location.href="${pageContext.request.contextPath}/tim/cancelCamera";
        }

    </script>
</head>
<body>
<input type="button" value="开始" onclick="st()"/><input type="button" value="结束" onclick="en()"/>
</body>
</html>