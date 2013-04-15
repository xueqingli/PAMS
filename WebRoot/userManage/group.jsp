<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.userManage.pojo.Group"></c:set>
<c:set var="entityName2" value="com.lxq.platform.userManage.pojo.Role"></c:set>
<c:set var="entityName3" value="com.lxq.platform.userManage.pojo.User"></c:set>
  
<html>
	<head>
		<script type="text/javascript">
			var params = {
							
			    /**panel渲染元素**/
				divId : '${pageId}_1',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '角色列表',

			    /**弹出窗口标题**/
			    title: "系统角色",
				
			    /**实体对象名称**/
			    entityName: '${entityName}',
				
			    /**action地址**/
			    actionUrl: "/baseAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'groupId',
			    
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
		            name: 'uid',
		            field: 'uid' ,
		            header: '组主键',
		            searchable: false,
		            hidden: true
		        },{
		            name: 'groupId',
		            type: 'textfield',
		            field: 'groupId' ,
		            header: '组编号',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'groupName',
		            field:'groupName' ,
		            header: '组名称',
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'status',
		            field:'status.text' ,
		            header: '组状态',
		            searchable: true,
		            codeNo : 'Status'
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '组主键',
			        name:'uid',
			        field: 'uid',
			        hidden: true,
			        readOnly : true,
			        hidden : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '组编号',
			        name:'groupId',
			        field: 'groupId',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '组名称',
			        name:'groupName',
			        field: 'groupName',
			        allowBlank: false    
			    },{
			        xtype:'combo',
			        fieldLabel: '组状态',
			        name:'status',
			        field: 'status.uid',
			        allowBlank: false,
			        codeNo : 'Status'
			    }],
			    
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
				]
			};			
			
			var group = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			group.initGridAndForm();			
			
			var params2 = {
							
			    /**panel渲染元素**/
				divId : '${pageId}_2',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '权限列表',

			    /**弹出窗口标题**/
			    title: "系统权限",
				
			    /**实体对象名称**/
			    entityName: '${entityName2}',
				
			    /**action地址**/
			    actionUrl: "/userManage/groupRoleAction",
				
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
		            name: 'groupUid',
		            field: 'groupUid' ,
		            header: '组主键',
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
		            name: 'valid',
		            field:'valid' ,
		            header: '是否有效'
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '组主键',
			        name:'groupUid',
			        field: 'groupUid',
			        hidden : true,
			        readOnly : true,
				    style:'background:#E6E6E6'
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
			
			var params3 = {
							
			    /**panel渲染元素**/
				divId : '${pageId}_3',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '权限列表',

			    /**弹出窗口标题**/
			    title: "系统权限",
				
			    /**实体对象名称**/
			    entityName: '${entityName3}',
				
			    /**action地址**/
			    actionUrl: "/userManage/groupUserAction",
				
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
		            name: 'groupUid',
		            field: 'groupUid' ,
		            header: '组主键',
		            hidden : true,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'userUid',
		            field: 'userUid',
		            header: '用户主键',
		            hidden : true,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'userId',
		            field: 'userId',
		            header: '用户编号'
		        },{
		            type: 'textfield',
		            name: 'userName',
		            field: 'userName' ,
		            header: '用户名称',
		            sortable: true
		        },{
		            type: 'textfield',
		            name: 'valid',
		            field:'valid' ,
		            header: '是否有效'
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '组主键',
			        name:'groupUid',
			        field: 'groupUid',
			        hidden : true,
			        readOnly : true,
				    style:'background:#E6E6E6'
			    },
			    {
			        xtype:'combo',
			        fieldLabel: '用户',
			        name:'userUid',
			        field: 'userUid',
			        allowBlank : false,
			        codeHql : 'select uid,userName from User'
			    }],
			    
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName3)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName3)}},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false}
				]
			};			
			
			var user = new com.lxq.js.GridAndForm.BaseGridAndForm(params3);
			user.initGridAndForm();
			
			//----上下型页面，上页面行点击，下页面重新加载--------------
			var groupPanel = group.getGridPanel();
			
			//当上面grid行点击时，重新加载下面grid的数据
			groupPanel.on( "rowclick" , function( gridPanel,rowIndex,e){
			
				var selModel = gridPanel.getSelectionModel();
			    if (selModel.hasSelection()) {//有选中行
			        var selected = selModel.getSelections();//获取选择的列
			        
			        var key_value = selected[0].data.uid;//获取选中的第一列，名为“uid”的属性的属性值
					
					role.getNewPanel().getForm().setValues({
						groupUid : key_value
					}); 
					
					role.reloadGrid(encodeURI("uid='"+key_value+"'"));

					user.getNewPanel().getForm().setValues({
						groupUid : key_value
					}); 
					
					user.reloadGrid(encodeURI("uid='"+key_value+"'"));
					
					
				}
			})
			
		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
		<div id='${pageId}_3'></div>
	</body>
</html>
