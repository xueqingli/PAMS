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
			    actionUrl: "/baseAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'userId',
			    
				/**排序方式**/
		    	dir: 'desc',
		    		
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
		            name: 'deptId',
		            type: 'combotree',
		            field:'belongDept.uid' ,
		            header: '所属机构',
		            onceLoad : false,  
	                dataUrl : '${path}/userManage/departmentAction', 
		            sortable: true,
		            hidden : true,
		            searchable: true
		        },{
		            name: 'deptName',
		            type: 'textfield',
		            field:'belongDept.deptName',
		            header: '所属机构',
		            sortable: true,
		            searchable: false
	        	},{
		            type: 'combo',
		            name: 'valid',
		            field:'valid',
		            header: '是否有效',
		            sortable: true,
		            searchable: true,
		            codeNo: 'Boolean',
		            sortable: false
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
			        fieldLabel: '密码',
			        name:'password',
			        field: 'password',
			        value: '098f6bcd4621d373cade4e832627b4f6',
			        hidden: true,  
			        allowBlank: true,
			        byUse: {new_panel:true}    
			    },{
			        xtype:'textfield',
			        field: 'belongDept.uid',
	                fieldLabel : '所属机构',  
			        id :'deptUid',
			        name:'deptUid',
			        hidden : true
	            },{
			        xtype:'combotree',
			        fieldLabel: '所属机构',
			        name:'deptName',
			        field: 'belongDept.deptName',
			        relativeField : 'deptUid',
	                allowBlank : false,
	                allowUnLeafClick : true,
	                onceLoad : false,
	                dataUrl : '${path}/userManage/departmentAction'
			    },{
			        xtype:'combo',
                    fieldLabel: '是否有效',
			        name:'valid',
			        field:'valid',
                    editable : false,
			        codeNo: 'Boolean'
                }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : ${pt:hasRight(curUser,'query', entityName)}},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
				]
			};			
			
			var user = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			user.initGridAndForm();

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
		            name: 'valid',
		            field:'valid' ,
		            header: '是否有效'
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
			var userPanel = user.getGridPanel();
			
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
