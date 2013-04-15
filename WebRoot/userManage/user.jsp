<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.userManage.pojo.User"></c:set>
<c:set var="entityName2" value="com.lxq.platform.userManage.pojo.Role"></c:set>
  
<html>
	<head>
		<script type="text/javascript">
			var params = {
				
				/**树节点编号**/
				nodeId : '${ param.nodeId }',
				
			    /**panel渲染元素**/
				divId : '${pageId}_1',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '用户列表',

			    /**弹出窗口标题**/
			    title: "系统用户",
				
			    /**实体对象名称**/
			    entityName: '${entityName}',
				
			    /**action地址**/
			    actionUrl: "/userManage/userAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'userId',
			    
				/**排序方式**/
		    	dir: 'desc',
		    		
				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-40)/2,
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            name: 'uid',//必须有一个名称为“uid”的信息项,作为主键
		            type: 'textfield',
		            field: 'uid' ,
		            header: '主键',
		            searchable: false,
		            hidden: true
		        },{
		            name: 'userId',
		            type: 'textfield',
		            field: 'userId' ,
		            header: '用户编号',
		            sortable: true,
		            searchable: true
		        },{
		            name: 'userName',
		            type: 'textfield',
		            field:'userName' ,
		            header: '用户名称',
		            searchable: true
	        	},{
		            type: 'textfield',
		            name: 'status',
		            field:'status.text',
		            header: '用户状态',
		            searchable: true,
		            sortable: false
		        },{
		            name: 'deptName',
		            type: 'textfield',
		            field:'belongDept.deptName',
		            header: '所属机构',
		            sortable: true,
		            searchable: false
	        	}],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '主键',
			        name:'uid',
			        field: 'uid',
			        hidden: true,
			        readOnly : true,
			        hidden : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '用户编号',
			        name:'userId',
			        field: 'userId',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '用户名',
			        name:'userName',
			        field: 'userName',
			        allowBlank: false    
			    },{
			        xtype:'textfield',
			        field: 'belongDept.uid',
	                fieldLabel : '所属机构',  
			        name:'belongDept',
			        value:'${ param.nodeId }',
			        hidden : true
			    },{
			        xtype:'combo',
                    fieldLabel: '用户状态',
			        name:'status',
			        field:'status.uid',
                    editable : false,
			        codeNo: 'Status'
                }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
					{ name : 'search_button', visible : true},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : true}
				]
			};			
			var user = new com.lxq.js.GridAndForm.BaseGridAndForm(params);

			user.initGridAndForm();
			
			var userPanel = user.getGridPanel();
			var tb = user.getToolBar();
			
			//添加一个按钮
			tb.insert(4,{
           		id: this.divId+'_reset_button',
           		hidden : !${pt:hasRight(curUser,'update', entityName)},
           		xtype: 'button',
           		text: '密码重置',
           		icon: '${path}/resources/images/icon/arrow.png',
   				listeners: { 
   					click: function(){
   						var selModel = userPanel.getSelectionModel();
		                if (selModel.hasSelection()) {
		                	Ext.MessageBox.show({
						           title: '提示',
						           msg: '确认重置密码吗？',
						           icon:Ext.MessageBox.QUESTION,
						           buttons:Ext.Msg.YESNO,
						           closable:true,
						           fn: function (button) {
		                        if (button == "yes") {
		                            var selected = selModel.getSelections();
		
									var uid = selected[0].data.uid;

									var jsonObject = {'uid':uid};
									
									Ext.Ajax.request({
									    url : '${path}/userManage/userAction!resetPass.action',
									    method : 'post',
									    params : {
									       jsonObject : Ext.encode(jsonObject)
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
			
			var params2 = {
							
			    /**panel渲染元素**/
				divId : '${pageId}_2',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '用户角色列表',

			    /**弹出窗口标题**/
			    title: "用户角色",
				
			    /**实体对象名称**/
			    entityName: '${entityName2}',
				
			    /**action地址**/
			    actionUrl: "/userManage/userRoleAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	

				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2,
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            name: 'userUid',
		            field: 'userUid' ,
		            header: '用户主键',
		            hidden : true,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'roleUid',
		            field: 'roleUid',
		            header: '角色主键',
		            hidden : true,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'roleId',
		            field: 'roleId' ,
		            header: '角色编号',
		            sortable: true
		        },{
		            type: 'textfield',
		            name: 'roleName',
		            field: 'name' ,
		            header: '角色名称',
		            sortable: true
		        },{
		            type: 'textfield',
		            name: 'status',
		            field:'status.text' ,
		            header: '角色状态'
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '用户主键',
			        name:'userUid',
			        field: 'userUid',
			        readOnly : true,
				    style:'background:#E6E6E6',
				    hidden : true
			    },
			    {
			        xtype:'combo',
			        fieldLabel: '角色',
			        name:'roleUid',
			        field: 'roleUid',
			        allowBlank : false,
			        codeHql : 'select uid,roleName from Role'
			    }],
			    
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName2)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName2)}},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false}
				]
			};			
			
			var role = new com.lxq.js.GridAndForm.BaseGridAndForm(params2);
			role.initGridAndForm();
			
			//----上下型页面，上页面行点击，下页面重新加载--------------
			
			//当上面grid行点击时，重新加载下面grid的数据
			userPanel.on( "rowclick" , function( gridPanel,rowIndex,e){
			
				var selModel = gridPanel.getSelectionModel();
			    if (selModel.hasSelection()) {//有选中行
			        var selected = selModel.getSelections();//获取选择的列
			        
			        var key_value = selected[0].data.uid;//获取选中的第一列，名为“uid”的属性的属性值
					
					role.getNewPanel().getForm().setValues({
						userUid : key_value
					}); 
					
					//重新加载下面gird的数据 
					role.reloadGrid(encodeURI("uid='"+key_value+"'"));
				}
			})
		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
	</body>
</html>
