<!--jsp文件常用于确定格式等属性的书写-->
<%@page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<!--c标签-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--时间格式-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--使用方式-->
<!-- <fmt:formatDate value="${book.book_presstime }" pattern="yyyy-MM-dd"/> -->
<!--c标签设定x动态项目路径-->
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <%--引入bootstrap的样式--%>
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.min.css">
    <%--引入bootstrap和jqgrid的整合样式--%>
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootstrap的js文件--%>
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>
    <script src="${path}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${path}/statics/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${path}/statics/jqgrid/js/ajaxfileupload.js"></script>


    <script type="text/javascript">
        $(function () {
            // 基本的数据表格构建
            $('#bannerList').jqGrid({
                url: '/banner/allBanner',// 请求数据的路径
                datatype: 'json', // 数据表格解析数据的格式
                colNames: ["图片编号", "图片名称", "图片", "描述", "状态", "创建日期"], // 指定表头
                styleUI: 'Bootstrap',
                height:"300px",
                //cellEdit: true,// 开启数据表格的编辑模式
                autowidth: true,// 数据表格是否自适应父容器的大小宽度
                cellurl: '${path}/banner/editBanner',
                colModel: [
                    {
                        name: 'id', align: 'center',hidden:true,
                    },
                    {
                        name: 'name', align: 'center', editable: true
                    },
                    {
                        name: 'cover', align: 'center', editable: true, edittype: "file",
                        formatter: function (value, options, row) {
                            //alert(value+"--------"+options+"--------"+row)
                            var temp = "<img height='25xp' src='/banner/img/" + row.cover + "'/>";
                            return temp;
                        }
                    },
                    {
                        name: 'description', align: 'center', editable: true
                    },
                    {
                        name: 'status', align: 'center', editable: true, edittype: 'select',
                        editoptions: {'value': '正常:正常;冻结:冻结'}
                    },
                    {
                        name: 'create_date', align: 'center',
                        formatter: 'date', formatoptions: {newformat: 'Y-m-d'},
                    }
                ], // 指定表中列于远程数据的匹配规则以及列其他的操作属性
                mtype: 'POST',  // 指定加载远程数据的请求方式 get/post
                pager: '#pager',// 使用分页工具栏
                rowList: [5, 10, 15, 20, 50],// 默认每页展示20条
                rowNum: 5,// 指定默认每页展示的条数，值必须来自rowList中的一个
                viewrecords: true,// 是否显示总记录条数
                caption : "轮播图的详细信息",
                editurl: '${path}/banner/editBanner',
            }).navGrid('#pager', {'add': true, 'edit': true, 'del': true, 'search': false, 'refresh': true},
                {
                    // 控制修改的额外操作
                    closeAfterEdit: close,
                    beforeShowForm: function (frm) {
                        frm.find("#cover").attr("disabled", true);
                    }
                },
                {
                    //文件上传功能
                    closeAfterAdd:true,
                    afterSubmit:function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        if(status){
                            //alert(status+"------------------"+id);
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/banner/upload",
                                fileElementId:"cover",
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    //alert("11111")
                                    $("#bannerList").trigger("reloadGrid");
                                }
                            });
                        }
                        //afterSubmit为方法可以返回任意类型实现这个方法
                        return "12312";
                    }
                }, {
                    //删除操作
                });
        })
    </script>
</head>
<body>
<h1>轮播图片展示</h1>
<div class="container-fluid">
    <div>
        <table id="bannerList"></table>
    </div>
    <div id="pager" style="height: 35px"></div>
</div>
</body>

</html>