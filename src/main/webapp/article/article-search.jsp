<%@page pageEncoding="UTF-8" %>
<div class="row">
    <div class="col-lg-1">
    </div>
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" class="form-control" id="search-input" placeholder="请输入相关内容">
            <span class="input-group-btn">
                <button class="btn btn-primary" type="button" id="search-btn">搜索文章</button>
            </span>
        </div><!-- /input-group -->
    </div>
    <div class="col-lg-5">
    </div>
</div>
<br>
<div class="panel panel-default">
    <div class="panel-heading">已为您找到以下内容</div>
    <table class="table" id="article-table-search">

    </table>
</div>








<script>
    $("#search-btn").click(function () {
        var content = $("#search-input").val();
        $.ajax({
            url:"${pageContext.request.contextPath}/article/search",
            type:"post",
            datatype:"json",
            data:"content="+content,
            success:function (data) {
                $("#article-table-search").html("");
                $.each(data,function (index,article) {
                    //处理查询到的结果
                    $("#article-table-search").append("<tr>" +
                        "<td>"+article.title+"</td>" +
                        "<td>"+article.author+"</td>" +
                        "<td>"+article.content+"</td>" +
                        "</tr>");
                })
            }
        })

    })


</script>

