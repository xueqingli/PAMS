<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<head>
		<script type="text/javascript">
			function refreshCache(){
				Ext.Ajax.request({
				    url : '${path}'+'/initResource',
				    method : 'post',
				    success : function(response, options) {
				    	Ext.MessageBox.show({
				           title: '提示',
				           msg: '刷新成功',
				           icon:Ext.MessageBox.INFO,
				           buttons:Ext.Msg.OK,
				           closable:true
				        });
				    },
				    failure : function() {
				    }
				});
			}		
		</script>
	</head>
	<body>
		<center>
			<div><a href="#" onClick="refreshCache()">刷新缓存</a></div>
		</center>
	</body>
</html>
