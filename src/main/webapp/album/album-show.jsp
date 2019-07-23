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

        <script>
            $("#album-table").jqGrid({
                    url : "${pageContext.request.contextPath}/album/allAlbum",
                    //  ${pageContext.request.contextPath}/album/selectAllAlbum
                    datatype : "json",
                    height : 190,
                    colNames : [ '编号', '标题', '图片', '章节数', '得分', '作者','播音员','简介','创建日期'],
                    colModel : [
                        {name : 'id',hidden:true},
                        {name : 'title',editable:true},
                        {name : 'cover',editable:true,edittype:"file",
                            formatter: function (value, options, row) {
                                //alert(value+"--------"+options+"--------"+row)
                                var temp = "<img height='25xp' src='/album/img/" + row.cover + "'/>";
                                return temp;
                            }},
                        {name : 'count',editable:true},
                        {name : 'score',editable:true},
                        {name : 'author',editable:true},
                        {name : 'broadcast',editable:true},
                        {name : 'brief',editable:true},
                        {name : 'createDate'}
                    ],
                    autowidth:true,
                    styleUI:"Bootstrap",
                    rowNum : 5,
                    height:"500px",
                    rowList : [ 5, 10, 20, 30 ],
                    pager : '#album-pager',
                    sortname : 'id',
                    viewrecords : true,
                    sortorder : "desc",
                    multiselect : false,
                    caption : "专辑展示表",
                    editurl: '${path}/album/editAlbum',
                    subGrid : true,
//-------------------------------------------------------------------------------------------------
                subGridRowExpanded : function(subgrid_id, albumId) {// 1.装子表的容器id    2.关系属性
                       // alert(albumId);
                    var subgrid_table_id = subgrid_id + "_t";   //子表的table  id
                    var pager_id = "p_" + subgrid_table_id;     //子表的div  id
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                        "<div id='" + pager_id + "' class='scroll'></div>");
                    $("#" + subgrid_table_id).jqGrid(
                        {
                            url : "${pageContext.request.contextPath}/chapter/allChapter?id="+albumId,  //查询当前专辑下的所有章节？当前专辑的id
                            editurl : "${pageContext.request.contextPath}/chapter/editChapter?aid="+albumId,
                            datatype : "json",
                            colNames : [ '编号', '标题', '大小', '时长','文件名',"创建日期","在线播放" ],
                            colModel : [
                                {name : "id", hidden:true},
                                {name : "title",editable:true,width : 60},
                                {name : "size",width : 60},
                                {name : "duration",width : 60},
                                {name : "name",editable:true,edittype:"file",width : 60},
                                {name : "createDate",width : 60},
                                {name : "aa",formatter:function (value,options,row) {
                                    //alert(value+"----"+options+"----"+row)
                                        return "<audio controls loop>\n" +
                                            "  <source src='${pageContext.request.contextPath}/album/music/"+row.name+"' type=\"audio/ogg\">\n" +
                                            "</audio>";
                                    }}
                            ],
                            autowidth:true,
                            styleUI:"Bootstrap",
                            rowNum : 5,
                            rowList : [ 5, 10, 20, 30 ],
                            viewrecords: true,// 是否显示总记录条数
                            pager : pager_id,
                            sortname : 'createDate',
                            sortorder : "asc",
                            height : '100%'
                        }).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit : false,
                            add : true,
                            del : false,
                            search:false
                        },{
                            //控制子表的修改
                        },{
                            //关闭添加对话框
                            closeAfterAdd:close,
                            afterSubmit:function (response) {
                                var status = response.responseJSON.status;
                                var id = response.responseJSON.message;
                               // alert(status+"------"+id);
                                if(status){
                                    //alert("2222");
                                    $.ajaxFileUpload({
                                        url:"${pageContext.request.contextPath}/chapter/upload",
                                        fileElementId:"name",
                                        data:{id:id},
                                        type:"post",
                                        success:function () {
                                            $("#" + subgrid_table_id).trigger("reloadGrid");
                                        }
                                    });
                                }
                                return "12312";
                            }
                        });
                },
//-------------------------------------------------------------------------------------------------
            }).navGrid("#album-pager", {edit : false,add : true,del : false,search:false},
                {
                    //修改额外事务
                },{
                    //添加额外事务
                    //关闭添加对话框
                    closeAfterAdd:close,
                    afterSubmit:function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        if(status){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/album/upload",
                                fileElementId:"cover",
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    $("#album-table").trigger("reloadGrid");
                                }
                            });
                        }
                        return "12312";
                    }
                },{
                //删除额外事务
                });






</script>

</head>
<body>
<table id="album-table"></table>
<div id="album-pager" style="height: 40px"></div>
</body>

</html>