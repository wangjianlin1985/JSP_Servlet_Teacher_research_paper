<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>

	<head>
		<base href="<%=basePath%>">

		<link rel="stylesheet" href="./layui/css/layui.css" media="all">
	</head>

	<!-- 复选框居中调节 -->
	<style>
			.layui-table-cell .layui-form-checkbox[lay-skin="primary"] {
			top: 50%;
			transform: translateY(-50%);
	</style>

	<body>
		<div style="text-align: center;">
			<form class="layui-form layui-form-pane">
				<div class="layui-form-item">
					<hr>
					<div class="layui-inline">

						<label class="layui-form-label">职称编号</label>
						<div class="layui-input-inline">
							<input id="id" name="id" autocomplete="off" class="layui-input" type="text">
						</div>

						<label class="layui-form-label">职称名称</label>
						<div class="layui-input-inline">
							<input id="titlename" name="titlename" autocomplete="off" class="layui-input" type="text">
						</div>

						<div class="layui-input-inline" style="width: 220px ">
							<button type="button" class="layui-btn" id="demo1"><i class="layui-icon" style="font-size:15px;">&#xe615; 查询  </i></button>
							<!--  <button type="button" class="layui-btn layui-btn-danger" id="demo2"><i class="layui-icon" style="font-size:15px;">&#xe640; 批量删除</i></button>-->
							<button type="button" class="layui-btn layui-btn" id="add"><i class="layui-icon" style="font-size:15px;">&#xe654;添加</i></button>
						</div>
						
					</div>
				</div>

			</form>
		</div>
		<div style="text-align: center;">
			<div class="layui-inline">
				<table id="demo" lay-filter="demo"></table>
			</div>
		</div>

		<script src="./layui/layui.js" charset="utf-8"></script>

		<script src="./jquery/jquery-3.2.1.min.js"></script>
		<script type="text/html" id="barDemo">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		</script>
		<script>
			layui.use(['layer', 'form', 'table'], function() {
				var form = layui.form;
				var table = layui.table;
				var layer = layui.layer;

				//第一个实例
				var tableIns = table.render({
					elem: '#demo',
					method: 'POST',
					skin: 'row' //行边框风格
						,
					cellMinWidth: 20 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
						,
					even: true //开启隔行背景
						,
					width: 600,
					height: 450,
					url: 'TitleQuery',
					page: true //开启分页
						,
					cols: [
						[ //表头

						   {
								type: 'numbers',
								title: '序号'
							}, {
								field: 'id',
								title: '职称编号',
								sort: true
							}, {
								field: 'name',
								title: '职称名称',
								minWidth: 150
							}, {
								fixed: 'right',
								align: 'center',
								toolbar: '#barDemo',
								title: '操作',
								minWidth: 150
							}
						]
					]

				});

				//监听工具条
				table.on('tool(demo)', function(obj) {
					var data = obj.data;
					if(obj.event === 'del') {
						layer.confirm("确认要删除吗，删除后不能恢复", {
								title: "删除确认"
							},
							function(index) {
								layer.close(index);
								$.ajax({
									url: "TitleQuery",
									type: "POST",
									dataType: 'json',
									data: {
										"id": data.id,
										"methodname": "deletesingle"
									},
									success: function(data) {
										if(data=="404") {
											layer.msg('有数据，不能删除', {
												icon: 6,
												time: 1000 //2秒关闭（如果不配置，默认是3秒）
											});
										} else if(data == "1"){
											layer.msg('删除成功', {
												icon: 6,
												time: 1000 //2秒关闭（如果不配置，默认是3秒）
											}, function() {
												$("#demo1").click();   //调用重载函数
											});
											
										}else{
											layer.alert("删除失败！");
										}
									}
								});
							});
					} else if(obj.event === 'edit') {
						layer.open({
							type: 2,
							title: '修改职称信息',
							shade: 0,
							fixed: false,
							area: ['400', 300],
							maxmin: true, //最大化，最小化
							content: 'title/titleEdit.jsp',
							success: function(layero, index) {
									var body = layer.getChildFrame('body', index); //巧妙的地方在这里哦
									body.contents().find("#oldid").val(data.id);
									body.contents().find("#id").val(data.id);
									body.contents().find("#titlename").val(data.name);

								}

								,
							end: function() {
								tableIns.reload({
									where: {
										id: $("#id").val(),
										titlename: $("#titlename").val()

									}
								})

							}
						});
					}
				});
				
				
				//添加按钮
				$("#add").click(function(){
						layer.open({
							type: 2,
							title: '添加职称信息',
							shade: 0,
							fixed: false,
							area: ['400', 300],
							maxmin: true, //最大化，最小化
							content: 'title/titleAdd.jsp',
							end: function() {
								tableIns.reload({
									where: {
										id: $("#id").val(),
										titlename: $("#titlename").val()

									}
								})
							}
						});
				
				
				
				});
				
				
				
				
				$("#demo1").click(function() { //查询按钮
					tableIns.reload({
						where: {
							id: $("#id").val(),
							titlename: $("#titlename").val()
						},
						page: {
							curr: 1 //重新从第 1 页开始
						}
					});
				});

				$("#demo2").click(function() {
					var checkStatus = table.checkStatus('demo');
					var data = checkStatus.data;
					var length = data.length;
					if(length === 0) {
						layer.alert('请至少选择一条数据删除', {
							title: '删除提示',
							icon: 2
						}); //这时如果你也还想执行yes回调，可以放在第三个参数中
					} else {
						layer.confirm('是否批量删除' + length + '条数据,删除后不可恢复', {
							icon: 3,
							title: '删除提示'
						}, function(index) {

							var ids = "";
							for(var i = 0; i < length; i++) {
								var id = data[i].id;
								ids = ids + id + ";";
							}
							$.ajax({
								url: 'MajorQuery',
								type: 'POST',
								dataType: 'json',
								data: {
									"id": ids,
									"methodname": "deletemulti"
								},
								success: function(data) {
									if(data.success == "true") {
										layer.msg(data.msg, {
											icon: 6,
											time: 1000 //1秒关闭（如果不配置，默认是3秒）
										}, function() {

											$("#demo1").click();

										});
									} else {
										layer.alert(data.msg);
									}
								}
							});
							layer.close(index);
						});

					}
				});
			});
		</script>
	</body>

</html>