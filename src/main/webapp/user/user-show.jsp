<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html;utf-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>专辑</title>
    <%--引入样式文件--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
    <%--引入js功能文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <%--引入echarts的js文件--%>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <%--<script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>--%>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>

    <script type="text/javascript">
        //页面完全加载
        $(function () {
            alert("这是展示用户")
            $("#userList").jqGrid({
                url:'${pageContext.request.contextPath}/user/allUser',
                datatype:'json',
                styleUI:'Bootstrap',
                colNames:['编号','电话','头像','用户名','密码','法名','性别','省份','城市','签名','上传日期'],
                cellEdit:true,
                colModel:[
                    {name:'id', align:'center',width:70},
                    {name:'phone',editable:true,align:'center',width:170},
                    {name:'photo',editable:true,align:'center',edittype:'file',
                        formatter:function (value,options,row) {
                            var temp="<img style='width:50px;height:25px' src='${pageContext.request.contextPath}/image/"+row.photo+"'>";
                            return temp;
                        }
                    },
                    {name:'username',editable:true,align:'center'},
                    {name:'password',editable:true,align:'center',edittype:'select',editoptions:{value:"正常:正常;冻结:冻结"}},
                    {name:'dharma',editable:true,align:'center'},
                    {name:'sex',editable:true,align:'center',width:70},
                    {name:'province',editable:true,align:'center'},
                    {name:'city',editable:true,align:'center'},
                    {name:'sign',editable:true,align:'center'},
                    {name:'createDate',editable:false,align:'center',width:250, formatter: 'date', formatoptions: {newformat: 'Y-m-d'},}
                ],
                mtype:'post',
                pager:'#pager',
                rowList:[3,5,10,20,30],
                height:200,
                autowidth:true,
                autoencode:true,
                sortname:'id',
                sortorder:'desc',
                page:1,//默认显示第一页
                rowNum:3,//默认显示的信息条数
                viewrecords:true,//是否显示总的信息条数
               ultiselect:true//多选框


            })
        });


        function out() {
            window.location.href="${pageContext.request.contextPath}/user/out";
        }
    </script>
</head>
<body>


<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">所有用户</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="out()">导出所有用户信息</a></li>
    </ul>
</div>
<table id="userList"></table>
<div id="pager" style="height: 60px"></div>
</body>
</html>
