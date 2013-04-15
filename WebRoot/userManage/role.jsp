<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.userManage.pojo.Role"></c:set>
  
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
			    entityName: "com.lxq.platform.userManage.pojo.Role",
				
			    /**action地址**/
			    actionUrl: "/baseAction",
				
			    /**每页显示的记录数**/
			    pageSize: 8,
			    
			    /**排序字段**/
			    sort: 'roleId',
			    
				/**排序方式**/
		    	dir: 'asc',	
				
				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2+20,
				
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
		            name: 'roleId',
		            type: 'textfield',
		            field: 'roleId' ,
		            header: '角色编号',
		            sortable: true,
		            searchable: true
		        },{
		            name: 'roleName',
		            type: 'textfield',
		            field:'roleName' ,
		            header: '角色名称',
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'status',
		            field:'status.text' ,
		            header: '角色状态',
		            searchable: true,
		            codeNo : 'Status'
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
			        fieldLabel: '角色编号',
			        name:'roleId',
			        field: 'roleId',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '角色名称',
			        name:'roleName',
			        field: 'roleName',
			        allowBlank: false    
			    },{
			        xtype:'combo',
			        fieldLabel: '是否有效',
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
					{ name : 'search_button', visible : true},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : true}
				]
			};			
			
			var role = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			role.initGridAndForm();			
			
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
			    entityName: "${entityName}",
				
			    /**action地址**/
			    actionUrl: "/userManage/rolePrivilegeAction",
				
			    /**每页显示的记录数**/
			    pageSize: 7,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	

				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2-20,
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            name: 'roleUid',
		            field: 'roleUid' ,
		            header: '角色主键',
		            hidden : true,
		            searchable: false,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'privilegeUid',
		            field: 'privilegeUid' ,
		            header: '权限主键',
		            hidden : true,
		            searchable: false,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'name',
		            field: 'name' ,
		            header: '权限名称',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'operate',
		            field: 'operate' ,
		            header: '操作名称',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'className',
		            field:'className' ,
		            header: '实体类名称',
		            searchable: true
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '角色主键',
			        name:'roleUid',
			        field: 'roleUid',
			        allowBlank: false,
			        hidden : true,
			        readOnly : true,
				    style:'background:#E6E6E6'
			    },
			    {
			        xtype:'combo',
			        fieldLabel: '权限',
			        name:'privilegeUid',
			        field: 'privilegeUid',
			        allowBlank : false,
			        codeHql : 'select uid,name from Privilege'
			    }],
			    
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false}
				]
			};			
			
			var privilege = new com.lxq.js.GridAndForm.BaseGridAndForm(params2);
			privilege.initGridAndForm();
			
			//----上下型页面，上页面行点击，下页面重新加载--------------
			var rolePanel = role.getGridPanel();
			
			//当上面grid行点击时，重新加载下面grid的数据
			rolePanel.on( "rowclick" , function( gridPanel,rowIndex,e){
			
				var selModel = gridPanel.getSelectionModel();
			    if (selModel.hasSelection()) {//有选中行
			        var selected = selModel.getSelections();//获取选择的列
			        
			        var key_value = selected[0].data.uid;//获取选中的第一列，名为“uid”的属性的属性值
					
					privilege.getNewPanel().getForm().setValues({
						roleUid : key_value
					}); 
					
					//重新加载下面gird的数据 
					privilege.reloadGrid(encodeURI("uid='"+key_value+"'"));
				}
			})
			
		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
	</body>
</html>
