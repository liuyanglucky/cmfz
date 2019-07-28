
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" type="text/css" rel="stylesheet">
    <%--引入bootstrap的样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.min.css">
    <%--引入bootstrap和jqgrid的整合样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootstrap的js文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <%--引入kindeditor的js文件--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <%--引入echarts的js文件--%>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
</head>
<body>
<!--导航-->
<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <a href="#" class="navbar-brand navbar-left"><span>持明法洲后台管理系统</span></a>
        <div class="navbar-right">
            <c:if test="${adminName !=null}">
                <span class="navbar-brand">欢迎${adminName}</span>
                <a href="${pageContext.request.contextPath}/admin/exit"><span
                        class="navbar-brand ">安全退出</span>
                </a>
            </c:if>
            <c:if test="${adminName == null}">
                <a href="${pageContext.request.contextPath}/login/login.jsp"><span
                        class="navbar-brand navbar-right">登录</span></a>
            </c:if>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <div class="panel-group" id="pg">
                <%--轮播图开始--%>
                <div class="panel panel-default" id="deptPanel">
                    <div class="panel panel-heading">
                        <a href="#pd1" class="panel-title   " data-toggle="collapse" data-parent="#pg">轮播图管理</a>
                    </div>
                    <div class="panel-collapse collapse in " id="pd1">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/banner/banner-show.jsp')" data-toggle="modal">所有轮播图</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <%--专辑开始--%>
                <div class="panel panel-default" id="empPanel">
                    <div class="panel panel-heading">
                        <a href="#pd2" class="panel-title" data-toggle="collapse" data-parent="#pg">专辑管理</a>
                    </div>
                    <div class="panel-collapse collapse" id="pd2">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/album/album-show.jsp')" id="">查询所有专辑</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <%--文章管理开始--%>
                <div class="panel panel-default" id="empPanel">
                    <div class="panel panel-heading">
                        <a href="#pd3" class="panel-title" data-toggle="collapse" data-parent="#pg">文章管理</a>
                    </div>
                    <div class="panel-collapse collapse" id="pd3">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/article/article-show.jsp')">查询所有文章</a></li>
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/article/article-search.jsp')">搜索文章</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <%--用户管理开始--%>
                <div class="panel panel-default" id="empPanel">
                    <div class="panel panel-heading">
                        <a href="#pd4" class="panel-title" data-toggle="collapse" data-parent="#pg">用户管理</a>
                    </div>
                    <div class="panel-collapse collapse" id="pd4">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/user-show.jsp')">查询所有用户</a></li>
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/user-line.jsp')">用户注册趋势</a></li>
                                <li class="list-group-item"><a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/user-map.jsp')">用户地区分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <%----%>
        <div class="col-md-9" id="contentLayout">
            <%--巨幕开始--%>
            <div class="jumbotron">
                <h3>欢迎来到持明法洲后台管理系统！</h3>
            </div>
                <img src="${pageContext.request.contextPath}/img/1.png" alt="" style="width:100%">
        </div>

<div class="container-fluid" style="position: absolute;top: 700px;right: 600px">
     <span>持明法中后台管理系统@百知教育2019.7.8</span>
</div>

<script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
