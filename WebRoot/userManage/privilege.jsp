<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.userManage.pojo.Privilege"></c:set>
    
<html>
	<head>
		<script type="text/javascript">
			var params = {
							
			    /**panel渲染元素**/
				divId : '${pageId}',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '权限列表',

			    /**弹出窗口标题**/
			    title: "系统权限",
				
			    /**实体对象名称**/
			    entityName: "com.lxq.platform.userManage.pojo.Privilege",
				
			    /**action地址**/
			    actionUrl: "/baseAction",
				
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
		            name: 'name',
		            field: 'name' ,
		            header: '权限名称',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'operate',
		            field: 'operate' ,
		            header: '操作',
		            sortable: true,
		            searchable: false
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
			        fieldLabel: '主键',
			        name:'uid',
			        field: 'uid',
			        hidden: true,
			        readOnly : true,
			        hidden : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '权限名称',
			        name:'name',
			        field: 'name',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '操作名称',
			        name:'operate',
			        field: 'operate',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '实体类名称',
			        name:'className',
			        field: 'className',
			        allowBlank: false    
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : ${pt:hasRight(curUser,'query', entityName)}},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
				]
			};			
			
			new com.lxq.js.GridAndForm.BaseGridAndForm(params).initGridAndForm();

		</script>
	</head>
	<body>
		<div id='${pageId}'></div>
	</body>
</html>
