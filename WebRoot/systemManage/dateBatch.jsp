<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.goalManage.pojo.DateBatch"></c:set>
    
<html>
	<head>
		<script type="text/javascript">
			var params = {		
			    /**panel渲染元素**/
				divId : '${pageId}',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '期次列表',

			    /**弹出窗口标题**/
			    title: "期次管理",
				
			    /**实体对象名称**/
			    entityName: '${entityName}',
				
			    /**action地址**/
			    actionUrl: "/baseAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'desc',	
				
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
		            name: 'batchTypeText',
		            field: 'batchType.text' ,
		            header: '期次类型',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'combo',
		            name: 'batchTypeUid',
		            field: 'batchType.uid' ,
		            header: '期次类型',
		            sortable: false,
		            searchable: true,
		            codeNo:'BatchType',
		            hidden: true
		        },{
		            type: 'textfield',
		            name: 'dateBatch',
		            field: 'dateBatch' ,
		            header: '期次',
		            sortable: true,
		            searchable: true
		           
		        },{
		            type: 'textfield',
		            name: 'beginStatus',
		            field: 'beginStatus' ,
		            header: '期次初生效',
		            sortable: false,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'endStatus',
		            field: 'endStatus' ,
		            header: '期次末生效',
		            sortable: false,
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
			        xtype:'combo',
			        fieldLabel: '期次类型',
			        name:'batchType',
			        field: 'batchType.uid',
			        allowBlank: false,
			        codeNo:'BatchType',
			        readOnly : true,
		            style:'background:#E6E6E6'
			    },{
			        xtype:'textfield',
			        fieldLabel: '期次',
			        name:'dateBatch',
			        field: 'dateBatch',
			        allowBlank: false
			    },{
			        xtype:'combo',
			        fieldLabel: '期次初生效',
			        name:'beginStatus',
			        field: 'beginStatus',
			        allowBlank: false,
			        codeNo:'Boolean'
			    },{
			        xtype:'combo',
			        fieldLabel: '期次末生效',
			        name:'endStatus',
			        field: 'endStatus',
			        allowBlank: false,
			        codeNo:'Boolean'
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : false},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : false},
					{ name : 'search_button', visible : true},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : true}

				]
			};			
			
			new com.lxq.js.GridAndForm.BaseGridAndForm(params).initGridAndForm();

		</script>
	</head>
	<body>
		<div id='${pageId}'></div>
	</body>
</html>
