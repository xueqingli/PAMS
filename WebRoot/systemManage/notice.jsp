<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="entityName" value="com.lxq.platform.systemManage.pojo.Notice"></c:set>
<html>
	<head>
		<script type="text/javascript">
			var params = {
							
			    /**panel渲染元素**/
				divId : '${pageId}',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '系统通知',
				
				/**实体对象名称**/
			    entityName: "${entityName}",
				
			    /**弹出窗口标题**/
			    title: "系统通知",
				
			    /**action地址**/
			    actionUrl: "/systemManage/noticeAction",
				
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
		            name: 'title',
		            field: 'title' ,
		            header: '标题',
		            sortable: false,
		            searchable: true
		        },{
	        	 	type: 'textfield',
		            name: 'filePath',
		            field: 'filePath',
		            header: '文件路径',
		            sortable: false,
		            searchable: false
		        },{
	        	 	type: 'textfield',
		            name: 'startDate',
		            field: 'startDate' ,
		            header: '开始日期',
		            sortable: false,
		            searchable: true
		        },{
		            type: 'datefield',
		            name: 'endDate',
		            field: 'endDate' ,
		            header: '结束日期',
		            sortable: false,
		            searchable: true
		        }],
				
				/**form表单信息项**/
			    formFields: [{
			        xtype:'textfield',
			        fieldLabel: '主键',
			        name:'uid',
			        field: 'uid',
			        readOnly : true,
			        hidden : true
			    },{
			        xtype:'textfield',
			        fieldLabel: '标题',
			        name:'title',
			        field: 'title'
			    },{
			        xtype:'datefield',
			        fieldLabel: '开始日期',
			        name:'startDate',
			        field: 'startDate',
			        minValue: new Date(),
			        format: 'Y-m-d'
			    },{
			        xtype:'datefield',
			        fieldLabel: '结束日期',
			        name:'endDate',
			        field: 'endDate',
			        format: 'Y-m-d'
                }],
			    
				buttons:[
					{ name : 'add_button', visible : true},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : true},
					{ name : 'delete_button', visible : true},
					{ name : 'search_button', visible : true},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false},    
				]
			};			
			
			var notice = new com.lxq.js.GridAndForm.BaseGridAndForm(params);

			notice.initGridAndForm();
			
			var noticePanel = notice.getGridPanel();
			var tb = notice.getToolBar();

			//添加一个按钮
			tb.insert(4,{
           		id: this.divId+'_upload_button',
           		xtype: 'button',
           		text: '上传附件',
           		icon: '${path}/resources/images/icon/arrow-up.gif',
   				listeners: { 
   					click: function(){
   						var selModel = noticePanel.getSelectionModel();
		                if (selModel.hasSelection()) {
		                	
		                	var selected = selModel.getSelections();//获取选择的列
		                	
		                	var form = new Ext.form.FormPanel({
		                        baseCls: 'x-plain',
		                        labelWidth: 80,
		                        url:'${path}/systemManage/noticeAction!upload.action',
		                        fileUpload:true,

		                        items: [{
		                            xtype: 'hidden',
		                            name:'uid',
		                            value:selected[0].data.uid
		                        },{
		                            xtype: 'hidden',
		                            name:'saveDir',
		                            value:'/WEB-INF/data/notice'
		                        },{
		                            xtype: 'textfield',
		                            fieldLabel: '文件名',
		                            name: 'upload',
		                            inputType: 'file',
		                            allowBlank: false,
		                            blankText: '请选择文件',
		                        }]
		                   	});
		                    var win = new Ext.Window({
		                        title: '文件上传',
		                        width: 350,
		                        height:100,
		                        plain:true,
		                        bodyStyle:'padding:5px;',
		                        buttonAlign:'center',
		                        items: [form],
		                        buttons: [{
		                            text: '上传',
		                            handler: function() {
		                                if(form.form.isValid()){
		                                    Ext.MessageBox.show({
		                                           title: '请等待',
		                                           msg: '上传中...',
		                                           progressText: '',
		                                           width:300,
		                                           progress:true,
		                                           closable:false,
		                                           animEl: 'loding'
		                                       });
		                                    form.getForm().submit({
		                                    
		                                        success: function(form, action){
		                                           Ext.MessageBox.show({
		                					           title: '提示',
		                					           msg: '上传成功',
		                					           icon:Ext.MessageBox.INFO,
		                					           buttons:Ext.Msg.OK,
		                					           closable:true,
		                					           fn: win.hide()
		                				           }); 
		                                           
		                                           notice.reloadGrid();
		                                        },    
		                                       failure: function(form, action){    
		                	                         Ext.MessageBox.show({
		                							     title: '提示',
		                							     msg: '上传失败',
		                							     icon:Ext.MessageBox.ERROR,
		                							     buttons:Ext.Msg.OK,
		                							     closable:true
		                						    });    
		                                       }
		                                    })               
		                                }
		                           }
		                        },{
		                            text: '关闭',
		                            handler:function(){win.hide();}
		                        }]
		                    }).show();
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
			},{
           		id: this.divId+'_view_button',
           		xtype: 'button',
           		text: '查看附件',
           		icon: '${path}/resources/images/icon/arrow-down.gif',
   				listeners: { 
   					click: function(){
   						var selModel = noticePanel.getSelectionModel();
		                if (selModel.hasSelection()) {
		                	var selected = selModel.getSelections();//获取选择的列
		                	doPost({
							    url:'${path}/systemManage/noticeAction!downloadFile.action',
							    params: {
							    	uid:selected[0].data.uid
							   }
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
