<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <%--引入kindeditor的jsc文件--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" sr="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>


    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

    <script>

        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                //相关参数
                width : '1000px',
                height: "500px",
                //展示图片空间
                allowFileManager:true,
                //图片空间对应的地址
                fileManagerJson:"${pageContext.request.contextPath}/article/browser",
                //上传图片的地址
                uploadJson:"${pageContext.request.contextPath}/article/upload",
                //上传图片时接受的参数
                filePostName:"articleImage"
            });
        });




        var goEasy = new GoEasy({
            //接收的appkey
            appkey: "BC-268c25f44f5841ff93a8f4e6aa24193a"
        });
        goEasy.subscribe({
            //当前的channel名称
            channel: "CmfzArticle",
            onMessage: function (message) {
                // alert("Channel:" + message.channel + " content:" + message.content);
                KindEditor.html("#editor_id",message.content);
            }
        });
    </script>




</head>
<body>
<textarea id="editor_id" name="content" style="width:700px;height:300px;">

    </textarea>
</body>
</html>
