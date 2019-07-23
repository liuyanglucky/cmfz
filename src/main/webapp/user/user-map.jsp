<%@page pageEncoding="UTF-8" %>



<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));    // 必须通过js语法获取当前div进行初始化

    // 指定图表的配置项和数据
    option = {
        title : {
            text: '持明法洲app用户地区分布图',
            subtext: '真实数据',
            left: 'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:['男','女']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            left: 'right',
            top: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        }
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    $.ajax({
        url:"${pageContext.request.contextPath}/user/map",
        type:"get",
        datetype:"json",
        success:function (response) {
            alert(response.male)
            myChart.setOption({
                series : [
                    {
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:response.male
                    },
                    {
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:response.fmale
                    }
                ]
            })
        }
    })





</script>
