<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/inc.jsp"></jsp:include>
<title>用户登录</title>
<script type="text/javascript">
    if (window != top) {
        top.location.href = location.href;
    }
    function checkLogin() {
        var username = $("#username").val();
        var password = $("#pass").val();
        if (username == "") {
            $("#error").html("登录帐号不能为空");
            return false;
        }
        if (password == "") {
            $("#error").html("登录密码不能为空");
            return false;
        }
        $("#loginForm").submit();
    }
</script>
</head>
<body>
<div class="easyui-window" style="width:500px;height:300px;background:#fafafa;overflow:hidden" title="用户登录" border="false" resizable="false" draggable="true" minimizable="false" maximizable="false">
    <div class="header" style="height:60px;">
        <div class="toptitle" style="margin-top: 20px;">后台系统</div>
    </div>
    <div style="padding:40px 0;">
        <form id="loginForm" action="${pageContext.request.contextPath}/manage/login.html" method="post">
        	<input id="password" name="password" type="hidden" />
            <div style="padding-left:50px">
                <div class="one">
					<span class="name">用户名：</span>
	                <input id="username" name="username" type="text" class="easyui-validatebox" style="height:28px;" />
                </div>
                <div class="one">
					<span class="name">密码：</span>
                    <input id="pass" name="pass" type="password" class="easyui-validatebox" style="height:28px;" />
                </div>
                <center>
                	<a class="easyui-linkbutton" onclick="return checkLogin();">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<a class="easyui-linkbutton" onclick="javascript:document.getElementById('loginForm').reset();">重置</a>
                </center>
            </div>
        </form>
        <p></p>
        <div id="error" align="center" style="color: red">${error}</div>
    </div>
</div>
</body>
</html>