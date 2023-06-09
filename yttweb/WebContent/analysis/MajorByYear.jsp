<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<base href="<%=basePath%>">

		<title>Echarts</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="./layui/css/layui.css" media="all">
		<!--  <script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts.js"></script>  -->
		<script src="./js/echarts.js"></script>
		<script src="./jquery/jquery-3.2.1.min.js"></script>
		<script src="layui/layui.js" charset="utf-8"></script>
     
	
	</head>

	<body>

		<div id="main" style="height: 450px; width: 800Px"></div>
		<div style="margin-left: 80px">
			<form class="layui-form layui-form-pane">
				<div class="layui-form-item">
					<br>
					<div class="layui-inline">

						<label class="layui-form-label">选择年限</label>
						<div class="layui-input-inline">
							<input name="time" id="date" lay-verify="date" placeholder="yyyy" autocomplete="off" class="layui-input" type="text">

						</div>

						<div class="layui-input-inline">
							<button type="button" class=" layui-btn layui-btn-normal" id="demo1"><i class="layui-icon" style="font-size:15px;">&#xe669; 生成图表  </i></button>

						</div>

					</div>
				</div>

			</form>
		</div>

		
    
			<script type="text/javascript">
			// 路径配置
		/*	require.config({
				paths: {
					echarts: './js/echarts.js'
				}
			});
			// 使用
			require(['echarts', 'echarts/chart/bar', 'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
				],
				drewEcharts
			); 
		*/
		
			drewEcharts();
			function drewEcharts() {
				// 基于准备好的dom，初始化echarts图表
				 var myChart = echarts.init(document.getElementById('main'));
				 myChart.showLoading({
                        animation:false,
                        text : 'Loading',
                       textStyle : {fontSize : 20}
                    });  
				//myChart.showLoading(); 

				var option = {
					title: {
						subtext: '专业按年统计发表论文情况'
					},
					tooltip: {
						show: true,
						trigger: 'axis'
					},
					legend: {
						data: []
					},
					toolbox: {
						show: true,
						feature: {
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					xAxis: [{
						type: 'category',
						data: []
					}],
					yAxis: [{
						type: 'value'
					}],
					series: []
				};

				$.ajax({
					type: "post",
					//async : false, //同步执行
					url: "EchartTest",
					data: {},
					dataType: "json", //返回数据形式为json
					success: function(data) {
						

						//if(data){
						//alert(data[1][0]);
						//}

						var Item = function() {
							return {
								name: '',
								type: 'bar',
								markPoint: {
									data: [{
											type: 'max',
											name: '最大值'
										},
										{
											type: 'min',
											name: '最小值'
										}
									]
								},
								markLine: {
									data: [{
										type: 'average',
										name: '平均值'
									}]
								},
								data: []
							}
						}; // series中的每一项为一个item,所有的属性均可以在此处定义  

						var legends = []; // 准备存放图例数据  
						var Series = []; // 准备存放图表数据  

						var legend = {
							data: []
						};
						for(var i = 0; i < data[1].length; i++) {
							legend.data.push(data[1][i]); // 将每一项的图例名称也放到图例的数组中
						}

						option.legend = legend; //图例动态加载
						option.xAxis[0].data = data[0]; //动态加载x轴

						for(var j = 0; j < data[1].length; j++) {

							var it = new Item();
							it.name = data[1][j];
							it.data = data[j + 2];
							Series.push(it); // 将item放在series中  
						}
						option.series = Series; // 设置图表  
						myChart.setOption(option); // 重新加载图表  
						myChart.hideLoading();  //关闭加载动画

					},
					error: function(errorMsg) {
						alert("不好意思，大爷，图表请求数据失败啦!");
						myChart.hideLoading();
					}
				})

				
				layui.use(['layer', 'form', 'laydate', 'table'], function() {
				var form = layui.form;
				var layer = layui.layer;

				var laydate = layui.laydate;
				//日期渲染,日期验证
				date = new Date();
				laydate.render({
					elem: '#date',
					max: 'date',
					type: 'year',
					range: true
				});
				
				$("#demo1").click(function(){
					//alert($("#date").val());
					$.ajax({
					type: "post",
					url: "EchartTest",
					data: {"pagetime" :$("#date").val()},
					dataType: "json", //返回数据形式为json
					success: function(data) {
					
					
						var Item = function() {
							return {
								name: '',
								type: 'bar',
								markPoint: {
									data: [{
											type: 'max',
											name: '最大值'
										},
										{
											type: 'min',
											name: '最小值'
										}
									]
								},
								markLine: {
									data: [{
										type: 'average',
										name: '平均值'
									}]
								},
								data: []
							}
						}; // series中的每一项为一个item,所有的属性均可以在此处定义  

						var legends = []; // 准备存放图例数据  
						var Series = []; // 准备存放图表数据  

						var legend = {
							data: []
						};
						for(var i = 0; i < data[1].length; i++) {
							legend.data.push(data[1][i]); // 将每一项的图例名称也放到图例的数组中
						}

						option.legend = legend; //图例动态加载
						option.xAxis[0].data = data[0]; //动态加载x轴

						for(var j = 0; j < data[1].length; j++) {

							var it = new Item();
							it.name = data[1][j];
							it.data = data[j + 2];
							Series.push(it); // 将item放在series中  
						}
						option.series = Series; // 设置图表  
						myChart.setOption(option,true); // 重新加载图表  

					},
					error: function(errorMsg) {
						alert("不好意思，大爷，图表请求数据失败啦!");
						myChart.hideLoading();
					}
				})	
			});
				
		});
 }
		</script>
	</body>
   
</html>