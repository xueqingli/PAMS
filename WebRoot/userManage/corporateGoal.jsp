<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.userManage.pojo.CorporateGoal"></c:set>
    
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
			    entityName: "com.lxq.platform.userManage.pojo.CorporateGoal",
				
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
		            name: 'catalog',
		            field:'catalog.text',
		            header: '目标类别',
		            searchable: true,
		            sortable: false
		        },{
		            type: 'numberfield',
		            name: 'status',
		            field: 'status',
		            header: '目标状态',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'contents',
		            field:'contents',
		            header: '目标内容',
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'orderperson',
		            field:'orderperson',
		            header: '目标创建人',
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'createtime',
		            field:'createtime',
		            header: '目标创建时间',
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
			    	xtype: 'combo',
			    	fieldLabel: '目标类别',
		            name: 'catalog',
		            field:'catalog.uid',
		            searchable: true,
		            codeNo: 'GoalCatalog',
		            sortable: false
			        
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标状态',
			        name: 'status',
		            field: 'status',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标内容',
			        name: 'contents',
		            field:'contents',
			        allowBlank: false    
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标创建人',
			        name: 'orderperson',
		            field:'orderperson',
			        allowBlank: false    
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标创建时间',
			        name: 'createtime',
		            field:'createtime',
			        allowBlank: false    
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : ${pt:hasRight(curUser,'query', entityName)}},
					{ name : 'update_button', visible : true},
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
