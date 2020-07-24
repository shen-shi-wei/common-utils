<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>测试</title>
</head>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/reAjax.js"></script>
<%--<script type="text/javascript" src="../../../static/js/reAjax.js"></script>--%>

<body>
<a id="name" onclick="idbtn()">${user.user_name}</a>
<input  id="id" value="${user.id}">
<%--<input type="hidden" id="id" value="${user.id}">--%>
</body>

<script type="text/javascript">
    
    function idbtn() {
        var data = $("#id").val();
        $.ajax({
            url:"${pageContext.request.contextPath}/user/sendBackData",
            data: {"id":data},
            async:true,
            type:"POST",
            dataType:"json",
            success: function(data){
                alert(JSON.stringify(data));
            },
            error: function(data){
                console.log("cuole");
            }
        });
    }
    
</script>





</html>