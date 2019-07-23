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
    <%--引入kindeditor的js文件--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        //页面完全加载
        $(function () {
            $("#article-table").jqGrid({
                url:'${pageContext.request.contextPath}/article/allArticle',
                datatype:'json',
                styleUI:'Bootstrap',
                colNames:['编号','标题','内容','作者','上传日期','操作'],
                //cellEdit:true,
                colModel:[
                    {name:'id', align:'center',width:70,hidden:true,},
                    {name:'title',editable:true,align:'center',width:170},
                    {name:'content',editable:true,align:'center',hidden:true
                        },
                    {name:'author',editable:true,align:'center'},
                    {name:'createDate',editable:false,align:'center',width:250,
                        //设置日期格式时间
                        formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
                    //增加修改按键，设置为点击触发事件。
                    {name : 'aa',formatter:function (value,options,row) {
                            return "<a class='btn btn-warning' onclick=\"operModal('edit','"+row.id+"')\">修改</a>";
                        }}
                ],
                mtype:'post',
                pager:'#article-pager',
                rowList:[3,5,10,20,30],
                height:200,
                autowidth:true,
                autoencode:true,
                sortname:'id',
                sortorder:'desc',
                page:1,//默认显示第一页
                rowNum:3,//默认显示的信息条数
                viewrecords:true,//是否显示总的信息条数
                editurl : "${pageContext.request.contextPath}/article/edit"
                // multiselect:true,//多选框
            }).navGrid("#article-pager",{del:true,edit:false,search:false,add:false});
        });

        //----------------------------------------------------------------------------
        //打开模态框
        function operModal(oper,id) {
            KindEditor.html("#editor_id","");
            var article = $("#article-table").jqGrid("getRowData",id);
            //给表单设置默认值
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            //内容中包含图片
            KindEditor.html("#editor_id",article.content);
            $("#article-oper").val(oper);
            //显示模态框
            $("#article-modal").modal("show");

        }
        //-------------------------------------------------------------------------
        //初始化kindeditor
        KindEditor.create('#editor_id',{
            //展示图片空间
            allowFileManager:true,
            //图片空间对应的地址
            fileManagerJson:"${pageContext.request.contextPath}/article/browser",
            //上传图片的地址
            uploadJson:"${pageContext.request.contextPath}/article/upload",
            //上传图片时接受的参数
            filePostName:"articleImage",
            //设置只能改变宽度，不能改变高度
            resizeType:1,
            //集成项目时必须添加,同步KindEditor的值到textarea文本框
            afterBlur:function () {
                this.sync();
            }
        });
        //------------------------------------------------------------------------
        //文件添加等功能
        function dealSave() {
            //文件添加
            $.ajax({
                url:"${pageContext.request.contextPath}/article/edit",
                type:"post",
                data:$("#article-form").serialize(),
                dataType:"json",
                success:function () {
                    //    关闭模态框
                    $("#article-modal").modal("hide");
                    //    刷新jqgrid
                    $("#article-table").trigger("reloadGrid");
                }
            })
        }
    </script>
</head>
<body>
<%--//---------------------------------------------------------------------------------%>
<%--//导航栏--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">所有文章</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="operModal('add')">添加文章</a></li>
    </ul>
</div>
<table id="article-table"></table>
<div id="article-pager" style="height: 40px"></div>

<%--//-------------------------------------------------------------------------------------%>
<%--添加文章的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="article-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 702px;">
            <%--头--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">我的文章</h4>
            </div>
            <%--内容--%>
            <div class="modal-body">


                <form class="form-horizontal" id="article-form">
                    <input type="hidden" id="article-id" name="id">
                    <input type="hidden" id="article-oper" name="oper">

                    <%--输入文章标题--%>
                    <div class="form-group">
                        <label for="article-title" class="col-sm-3 control-label">文章标题：</label>
                        <div class="col-sm-9">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="文章标题">
                        </div>
                    </div>
                    <%--输入文章作者--%>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-3 control-label">文章作者：</label>
                        <div class="col-sm-9">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <%--输入文章内容--%>
                        <%--kindeditor--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                    </div>
                </form>


            </div>
            <%--脚--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="dealSave()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>