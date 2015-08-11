<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extBrowser.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入my97 -->
<script src="${pageContext.request.contextPath}/static/component/My97DatePicker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
<!-- 引入bootstrap样式 -->
<link href="${pageContext.request.contextPath}/static/component/bootstrap-2.3.1/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/static/component/jquery-easyui-1.3.3/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/component/jquery-easyui-1.3.3/themes/icon.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/jquery-easyui-1.3.3/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- 修复EasyUI1.3.3中layout组件的BUG -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/jquery-easyui-1.3.3/plugins/jquery.layout.js" charset="utf-8"></script>

<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/component/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- 扩展EasyUI -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extEasyUI.js?v=201305241044" charset="utf-8"></script>

<!-- 扩展EasyUI Icon -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/component/au/extEasyUIIcon.css?v=201305301906" type="text/css">

<!-- 扩展jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/extJquery.js?v=201305301341" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.MultiFile.pack.js" charset="utf-8"></script>
<!-- 引入jquery.autocomplete -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery.autocomplete.css" type="text/css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.autocomplete.js"></script>
<!-- 引入jquery.md5 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.md5.js" charset="utf-8"></script>

<!-- 树状复选框菜单 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/component/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/component/ztree/js/jquery.ztree.excheck-3.5.js"></script>

<!-- 导入自定义css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" type="text/css" /> 