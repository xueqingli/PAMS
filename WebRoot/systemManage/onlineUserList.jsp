<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="entityName" value="com.lxq.platform.userManage.pojo.User"></c:set>
<html>
	<head>
		<script type="text/javascript">
			var params = {
							
			    /**panel渲染元素**/
				divId : '${pageId}',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '在线用户列表',

			    /**弹出窗口标题**/
			    title: "系统权限",
				
			    /**action地址**/
			    actionUrl: "/onlineUser",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            name: 'uid',//必须有一个名称为“uid”的信息项,作为主键
		            field: 'uid' ,
		            header: '主键',
		            searchable: false,
		            hidden: true
		        },{
		            type: 'textfield',
		            name: 'userId',
		            field: 'userId' ,
		            header: '用户编号',
		            sortable: false,
		            searchable: false
		        },{
	        	 	type: 'textfield',
		            name: 'userName',
		            field: 'userName' ,
		            header: '用户名称',
		            sortable: false,
		            searchable: false
		        },{
	        	 	type: 'textfield',
		            name: 'belongDept',
		            field: 'belongDept' ,
		            header: '所属机构',
		            sortable: false,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'ipAddress',
		            field: 'ipAddress' ,
		            header: '登陆地址',
		            sortable: false,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'loginTime',
		            field:'loginTime' ,
		            header: '登陆时间',
		            searchable: false
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    ],
			    
				buttons:[
					{ name : 'add_button', visible : false},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : false},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false},    
				]
			};			
			
			var onlineUser = new com.lxq.js.GridAndForm.BaseGridAndForm(params);

			onlineUser.initGridAndForm();
			
			var onlineUserPanel = onlineUser.getGridPanel();
			var tb = onlineUser.getToolBar();

			//添加一个按钮
			tb.insert(0,{
           		id: this.divId+'_reset_button',
           		hidden : !${pt:hasRight(curUser,'update', entityName)},
           		xtype: 'button',
           		text: '强制退出',
           		icon: '${path}/resources/extjs/examples/shared/icons/fam/add.gif',
   				listeners: { 
   					click: function(){
   						var selModel = onlineUserPanel.getSelectionModel();
		                if (selModel.hasSelection()) {
		                	Ext.MessageBox.show({
						           title: '提示',
						           msg: '确认强制退出此用户吗？',
						           icon:Ext.MessageBox.QUESTION,
						           buttons:Ext.Msg.YESNO,
						           closable:true,
						           fn: function (button) {
		                        if (button == "yes") {
		                            var selected = selModel.getSelections();
		
									var userId = selected[0].data.userId;
									
									Ext.Ajax.request({
									    url : '${path}/onlineUser!logout.action',
									    method : 'post',
									    params : {
									    	userId : userId
									    },
									    success : function(response, options) {
									    	var o_response = Ext.util.JSON.decode(response.responseText)
									    	if(!o_response.success){
										    	Ext.MessageBox.show({
												     title: '提示',
												     msg: o_response.msg?o_response.msg:"系统错误",
												     icon:Ext.MessageBox.ERROR,
												     buttons:Ext.Msg.OK,
												     closable:true
											    });
									    	}else{
										    	Ext.MessageBox.show({
												     title: '提示',
												     msg: o_response.msg,
												     icon:Ext.MessageBox.INFO,
												     buttons:Ext.Msg.OK,
												     closable:true
											    });
									    	}
									    },
									    failure : function(response, options) {
									    	 Ext.MessageBox.show({
											     title: '提示',
											     msg: '系统错误',
											     icon:Ext.MessageBox.ERROR,
											     buttons:Ext.Msg.OK,
											     closable:true
										    });
									    }
									 });
		                        }
		                    }});
		                }
		                else {
		                	Ext.MessageBox.show({
					           title: '提示',
					           msg: '请选择一条记录',
					           icon:Ext.MessageBox.WARNING,
					           buttons:Ext.Msg.OK,
					           closable:true
					        });
		                }
   					}
   				}	
			});
			
		</script>
	</head>
	<body>
		<div id='${pageId}'></div>
	</body>
</html>
