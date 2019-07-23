<%@page pageEncoding="UTF-8" %>
<%--引入样式文件--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css"
      type="text/css">
<%--引入js功能文件--%>
<script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
<%--引入echarts的js文件--%>
<script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/echarts/china.js"></script>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));    // 必须通过js语法获取当前div进行初始化

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年用户注册趋势',
            subtext: "这是个子标题"
        },
        tooltip: {},  // 数据的提示
        legend: {
            data: ['男', "女", "小孩"]
        },
        xAxis: {
            data: ["一月份", "二月份", "三月份", "四月份", "五月份", "六月份", "七月份", "八月份", "九月份", "十月份", "十一月份", "十二月份"]
        },
        yAxis: {},
        series: [{
            name: '注册人数',
            type: 'line',//bar：柱状图  line:折线图     pie:饼图
            data: [""]
        }]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    $.ajax({
        url: "${pageContext.request.contextPath}/user/mouth",
        type: "post",
        datetype: "json",
        success: function (response) {
            alert(response.date);
            myChart.setOption({
                series: [{
                    name: '注册人数',
                    type: 'line',//bar：柱状图  line:折线图     pie:饼图
                    data: response.data,
                }]
            })
        }
    })
</script>
