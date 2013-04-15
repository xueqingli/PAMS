<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.flowManage.pojo.FlowCatalog"></c:set>
<c:set var="entityName2" value="com.lxq.platform.flowManage.pojo.FlowModel"></c:set>
    
<html>
	<head>
		<script type="text/javascript">
			var params = {
			
			    /**panel渲染元素**/
				divId : '${pageId}_1',
								
			    /**项目名称**/
			    path: "${path}",

			    /**弹出窗口标题**/
			    title: "代码目录",
				
			    /**实体对象名称**/
				entityName: "${entityName}",
				
				/**action地址**/
			    actionUrl: "/baseAction",
			    
			    /**每页显示的记录数**/
			    pageSize: 8,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',
			
				/**导出文件的名称**/
				fileName: '代码目录',
				
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
		            name: 'uid',
		            type: 'textfield',
		            field: 'uid' ,
		            header: '主键',
		            sortable: true,
		            searchable: false,
		            hidden : true
		        },{
		            name: 'flowNo',
		            type: 'textfield',
		            field: 'flowNo' ,
		            header: '流程编号',
		            sortable: true,
		            searchable: true
		        },{
		            name: 'flowName',
		            type: 'textfield',
		            field:'flowName' ,
		            header: '流程名称',
		            sortable: true,
		            searchable: true
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			        xtype:'textfield',
			        fieldLabel: '主键',
			        name:'uid',
			        field: 'uid',
			        readOnly : true,
			        hidden : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '流程编号',
			        name:'flowNo',
			        field: 'flowNo',
			        allowBlank : false
			    },{
			        xtype:'textfield',
			        fieldLabel: '流程名称',
			        name:'flowName',
			        field: 'flowName',
			        allowBlank : false
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}},
				]
			};			
			
			var codeCatalog = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			codeCatalog.initGridAndForm();			
			
			var params2 = {
			
			    /**panel渲染元素**/
				divId : '${pageId}_2',
								
			    /**项目名称**/
			    path: "${path}",

			    /**弹出窗口标题**/
			    title: "代码字典",
				
				/**导出文件的名称**/
				fileName: '代码字典',
				
			    /**实体对象名称**/
			    entityName: "${entityName2}",
				
				/**action地址**/
			    actionUrl: "/baseAction",
			    
			    /**排序字段**/
			    sort: 'phaseNo',
			    
				/**排序方式**/
		    	dir: 'asc',

				/**grid的高度**/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2,

				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
			    
			    /**每页显示的记录数**/
			    pageSize: 8,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            header: '主键',
		            name: 'uid',//必须有一个名称为“uid”的信息项,作为主键
		            field: 'uid' ,
		            sortable: true,
		            searchable: false,
		            hidden : true
		        },{
		            type: 'textfield',
		            header: '流程阶段编号',
		            name: 'phaseNo',
		            field: 'phaseNo' ,
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            header: '流程阶段名称',
		            name: 'phaseName',
		            field:'phaseName' ,
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            header: '审批角色',
		            name: 'roleName',
		            field:'role.roleName' ,
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            header: '下一阶段',
		            name: 'nextPhaseNo',
		            field:'nextPhaseNo' ,
		            sortable: true,
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
			        readOnly : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '流程阶段编号',
			        name:'phaseNo',
			        field: 'phaseNo',
			        allowBlank : false
			    },{
			        xtype:'textfield',
			        fieldLabel: '流程阶段名称',
			        name:'phaseName',
			        field: 'phaseName',
			        allowBlank : false
			    },{
			    	xtype:'combo',
			        fieldLabel: '审批角色',
			        name:'roleUid',
			        field: 'role.uid',
			        allowBlank : false,
			        codeHql : 'select uid,roleName from Role'
			    },{
			        xtype:'textfield',
			        fieldLabel: '下一阶段编号',
			        name:'nextPhaseNo',
			        field: 'nextPhaseNo',
			        allowBlank : false
			    },{
			        xtype:'textfield',
			        fieldLabel: '流程目录',
			        name:'flowCatalogUid',
			        field: 'flowCatalog.uid',
			        allowBlank : false,
			        hidden : true,
			        readOnly : true,
			        style:'background:#E6E6E6'
			    }],
			    
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName2)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName2)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName2)}},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : true}
				]
			};			
			
			var flowModel = new com.lxq.js.GridAndForm.BaseGridAndForm(params2);
			flowModel.initGridAndForm();
			
			//----上下型页面，上页面行点击，下页面重新加载--------------
			var flowCatalogPanel = codeCatalog.getGridPanel();
			
			//当上面grid行点击时，重新加载下面grid的数据
			flowCatalogPanel.on( "rowclick" , function( gridPanel,rowIndex,e){
				var selModel = gridPanel.getSelectionModel();
			    if (selModel.hasSelection()) {//有选中行
			        var selected = selModel.getSelections();//获取选择的列
			        var key_value = selected[0].data.uid;//获取选中的第一列，名为“uid”的属性的属性值
			        
			        flowModel.getNewPanel().getForm().setValues({
			        	flowCatalogUid : key_value
					});
				}
				//重新加载下面gird的数据 
				flowModel.reloadGrid(encodeURI("flowCatalog.uid='"+key_value+"'"));
			})
		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
	</body>
</html>
