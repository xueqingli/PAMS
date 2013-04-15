<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.systemManage.pojo.Menu"></c:set>
      
<html>
	<head>
		<script type="text/javascript">
			var params = {
			
			    /**panel渲染元素**/
				divId : '${pageId}_1',
								
			    /**项目名称**/
			    path: "${path}",

			    /**弹出窗口标题**/
			    title: "系统菜单",
				
			    /**实体对象名称**/
			    entityName: "${entityName}",
				
				/**action地址**/
			    actionUrl: "/baseAction",
			    
			    /**每页显示的记录数**/
			    pageSize: 9,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	
			
				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2+45,
				
				/**导出文件的名称**/
				fileName: '系统菜单',
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 270,
				
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
		            type: 'numberfield',
		            name: 'orderNo',
		            field: 'orderNo' ,
		            header: '排序号',
		            sortable: true,
		            searchable: false
		        },{
		            name: 'text',
		            type: 'textfield',
		            field: 'text' ,
		            header: '菜单名称',
		            sortable: true,
		            searchable: true
		        },{
		            name: 'url',
		            type: 'textfield',
		            field:'url' ,
		            header: '菜单链接地址',
		            searchable: true
		        },{
		            name: 'showTree',
		            type: 'combo',
		            field:'showTree' ,
		            header: '展示树',
		            searchable: true,
		            codeNo : 'Boolean'
		        },{
		            type: 'textfield',
		            header: '父菜单',
		            name: 'parentMenuUid',
		            field:'parentMenu.uid' ,
		            hidden : true,
		            searchable: true
		        },{
		            type: 'textfield',
		            header: '父菜单',
		            name: 'parentMenuText',
		            field:'parentMenu.text' ,
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
			        readOnly : true
			    },{
			        xtype:'numberfield',
			        fieldLabel: '排序号',
			        name:'orderNo',
			        field: 'orderNo',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '菜单名称',
			        name:'text',
			        field: 'text',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '菜单链接地址',
			        name:'url',
			        field: 'url',
			        allowBlank: true
			    },{
			        xtype:'combo',
			        fieldLabel: '展示树形菜单',
			        name:'showTree',
			        field: 'showTree',
			        allowBlank: false,
			        codeNo : 'Boolean'
			    
			    },{
			        xtype:'combo',
			        fieldLabel: '父菜单',
			        name:'parentMenu',
			        field: 'parentMenu.uid',
			        allowBlank : false,
			        codeHql : 'select id,text from Menu'
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : ${pt:hasRight(curUser,'add', entityName)}},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : ${pt:hasRight(curUser,'update', entityName)}},
					{ name : 'delete_button', visible : ${pt:hasRight(curUser,'delete', entityName)}}
				]
			};			
			
			var menu = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			menu.initGridAndForm();
			
			var params2 = {
							
			    /**panel渲染元素**/
				divId : '${pageId}_2',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '角色列表',

			    /**弹出窗口标题**/
			    title: "系统角色",
				
			    /**实体对象名称**/
			    entityName: "${entityName}",
				
			    /**action地址**/
			    actionUrl: "/systemManage/menuRoleAction",
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'id',
			    
				/**排序方式**/
		    	dir: 'asc',	
				
				/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2-60,
				
				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            name: 'menuId',
		            field: 'menuId' ,
		            header: '菜单主键',
		            searchable: false,
		            hidden: true,
		            whereClause : true
		        },{
		            type: 'textfield',
		            name: 'roleUid',
		            field: 'roleUid' ,
		            header: '角色主键',
		            searchable: false,
		            hidden: true,
		            whereClause : true
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
		            name: 'status',
		        	type: 'textfield',
		            field:'status.text' ,
		            header: '角色状态'
		        }],
				
				/**form表单信息项**/
			    formFields: [{
			        xtype:'textfield',
			        fieldLabel: '菜单主键',
			        name:'menuId',
			        field: 'menuId',
			        allowBlank : false,
			        hidden : true,
			        readOnly : true,
			        style:'background:#E6E6E6',
			        byUse : {new_panel:true,edit_panel:true}
			    },{
			        xtype:'combo',
			        fieldLabel: '角色',
			        name:'roleUid',
			        field: 'roleUid',
			        allowBlank : false,
		            codeHql : 'select uid,roleName from Role',
			        byUse : {new_panel:true,edit_panel:true}
			    }],
			    
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
			
			var role = new com.lxq.js.GridAndForm.BaseGridAndForm(params2);
			role.initGridAndForm();
			
			//----上下型页面，上页面行点击，下页面重新加载--------------
			var menuPanel = menu.getGridPanel();
			
			//当上面grid行点击时，重新加载下面grid的数据
			menuPanel.on( "rowclick" , function( gridPanel,rowIndex,e){
			
				var selModel = gridPanel.getSelectionModel();
			    if (selModel.hasSelection()) {//有选中行
			        var selected = selModel.getSelections();//获取选择的列
			        
			        var key_value = selected[0].data.uid;//获取选中的第一列，名为“uid”的属性的属性值
					
					role.getNewPanel().getForm().setValues({
						menuId : key_value
					}); 
					
					//重新加载下面gird的数据 
					role.reloadGrid(encodeURI("id='"+key_value+"'"));
				}
			})

		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
	</body>
</html>
