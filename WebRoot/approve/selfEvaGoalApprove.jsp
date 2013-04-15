<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName1" value="com.lxq.platform.goalManage.pojo.ApprovePostil"></c:set>
<c:set var="entityName2" value="com.lxq.platform.goalManage.pojo.PerformanceGoal"></c:set>
    
<html>
	<head>
		<script type="text/javascript">
			var jsonObject = Ext.decode('${ param.jsonObject }');
			var finishStatus = jsonObject.finishStatus;
			var goalType = jsonObject.goalType;
		
			var params = {		
			    /**panel渲染元素**/
				divId : '${pageId}_1',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '目标执行人列表',

			    /**弹出窗口标题**/
			    title: "期次管理",
				
			    /**实体对象名称**/
			    entityName: '${entityName1}',
				
			    /**action地址**/
			    actionUrl: "/goalManage/goalApplierAction",
				
			    /***grid查询参数**/
			    baseParams: {applyType:'selfEva',goalType:goalType,finishStatus: finishStatus},
				
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	

		    	/**grid高度*/
				gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2-10,

				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 250,
				
				/**弹出窗口的表单项的文字标签的对齐方式**/
				labelAlign: 'right',
				
				/**gird信息项**/
				gridFields: [{
		            type: 'textfield',
		            name: 'dateBatchUid',
		            field: 'goal.dateBatch.uid' ,
		            header: '期次主键',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'dateBatch',
		            field: 'goal.dateBatch.dateBatch' ,
		            header: '期次',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'ownerDeptUid',
		            field: 'goal.ownerDept.uid' ,
		            header: '执行机构',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'ownerDeptName',
		            field: 'goal.ownerDept.deptName' ,
		            header: '执行机构',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'ownerPersonUid',
		            field: 'goal.ownerPerson.uid' ,
		            header: '执行人',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'ownerPersonName',
		            field: 'goal.ownerPerson.userName' ,
		            header: '执行人',
		            sortable: true,
		            searchable: true
		        }],
				
				/**form表单信息项**/
			    formFields: [
			    {
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : false},
					{ name : 'query_button', visible : false},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : false},
					{ name : 'search_button', visible : false},
					{ name : 'extend_search_button', visible : false},
					{ name : 'export_button', visible : false}
				]
			};			
			
			var applyPerson = new com.lxq.js.GridAndForm.BaseGridAndForm(params);

			applyPerson.initGridAndForm();
			
			var params2 = {		
				    /**panel渲染元素**/
					divId : '${pageId}_2',
					
				    /**项目名称**/
				    path: '${path}',
				
					/**导出文件的名称**/
					fileName: '目标列表',

				    /**弹出窗口标题**/
				    title: "期次管理",
					
				    /**实体对象名称**/
				    entityName: '${entityName2}',
					
				    /**action地址**/
				    actionUrl: "/goalManage/goalApproveAction",
					
				    /***grid查询参数**/
				    baseParams: {applyType:'selfEva',goalType:goalType,finishStatus: finishStatus},
				    
				    /**每页显示的记录数**/
				    pageSize: 20,
				    
				    /**排序字段**/
				    sort: 'uid',
				    
					/**排序方式**/
			    	dir: 'asc',	

			    	/**grid高度*/
					gridHeight : (Ext.get("center").getHeight()-Ext.get("south").getHeight()-20)/2-10,

					/**弹出窗口的宽度**/
					formWidth: 600,
					
					/**弹出窗口的高度**/
					formHeight: 350,
					
					/**弹出窗口的表单项的文字标签的对齐方式**/
					labelAlign: 'right',
					
					/**gird信息项**/
					gridFields: [{
			            type: 'textfield',
			            name: 'uid',//必须有一个名称为“uid”的信息项,作为主键
			            field: 'goal.uid' ,
			            header: '主键',
			            searchable: false,
			            hidden: true
			        },{
			            type: 'textfield',
			            name: 'dateBatch',
			            field: 'goal.dateBatch.dateBatch' ,
			            header: '期次',
			            sortable: true,
			            searchable: true
			        },{
			            type: 'textfield',
			            name: 'goalType',
			            field: 'goal.goalType.text' ,
			            header: '目标类型',
			            sortable: true,
			            searchable: true
			        },{
			            type: 'textfield',
			            name: 'ownerDept',
			            field: 'goal.ownerDept.deptName' ,
			            header: '执行机构',
			            sortable: true,
			            searchable: true
			        },{
				        type:'textfield',
				        name:'ownerPerson',
				        field: 'goal.ownerPerson.userName',
				        header: '执行人',
				        sortable: true,
			            searchable: true
				    },{
			            type: 'textfield',
			            name: 'beginStatus',
			            field: 'goal.beginStatus.text' ,
			            header: '期次初状态',
			            sortable: true,
			            searchable: true
			        },{
			            type: 'textfield',
			            name: 'createDept',
			            field: 'goal.createDept.deptName' ,
			            header: '创建部门',
			            sortable: true,
			            searchable: true
			        },{
			            type: 'textfield',
			            name: 'createPerson',
			            field: 'goal.createPerson.userName' ,
			            header: '创建人',
			            sortable: true,
			            searchable: true
			        },{
			            type: 'textfield',
			            name: 'createTime',
			            field: 'goal.createTime' ,
			            header: '创建时间',
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
				        readOnly : true,
				        hidden : true
				    },{
				        xtype:'combo',
				        fieldLabel: '所属期次',
				        name:'dateBatch',
				        field: 'dateBatch.uid',
				        allowBlank: false,
				        codeHql:'select uid,dateBatch from DateBatch where beginStatus=true and endStatus=false '
				    },{
				    	xtype: 'textarea',
				        fieldLabel: '目标内容',
				        name:'content',
				        field: 'content',
				        height:200,
				        allowBlank: false,
				        width: 400,
				        height : 100
				    }],
				    
					//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
					buttons:[
						{ name : 'add_button', visible : false},
						{ name : 'query_button', visible : true},
						{ name : 'update_button', visible : finishStatus=='no'?true:false},
						{ name : 'delete_button', visible : false},
						{ name : 'search_button', visible : false},
						{ name : 'extend_search_button', visible : false},
						{ name : 'export_button', visible : false}
					]
				};			
				
				var deptLevel = Ext.decode('${ curDept.level.value}');
				
				//如果当前部门级别是1级部门，则展示协同部门
			    if(deptLevel == '1'){
					params2.formFields.push({
						xtype:'lovcombo',
				        fieldLabel: '协同部门',
				        name:'coorDeptsJson',
				        field: 'coorDeptsJson',
				        allowBlank: false,
				        codeHql: "select uid,deptName from Department where level.value='2'"
		            });	
			    }
			
				var selfEvaGoal = new com.lxq.js.GridAndForm.BaseGridAndForm(params2);

				selfEvaGoal.initGridAndForm();
				
				var selfEvaGoalPanel = selfEvaGoal.getGridPanel();
				var tb = selfEvaGoal.getToolBar();
				
				var dateBatchUid ;
		        var ownerDeptUid ;
		        var ownerPersonUid ;
		        
				
				//添加一个按钮
				tb.insert(4,{
	           		id: this.divId+'_sbumit_button',
	           		hidden : finishStatus=='no'?false:true,
	           		xtype: 'button',
	           		text: '审核',
	           		icon: '${path}/resources/extjs/examples/shared/icons/fam/add.gif',
	   				listeners: { 
	   					click: function(){
	   						var selModel = selfEvaGoalPanel.getSelectionModel();
			                if (selModel.hasSelection()) {
			                	
			                	var selected = selModel.getSelections();
			            		
		                		var opinion_tb = new Ext.Toolbar({
		                	       items: [
		                	       {
		                	      		text: '提交',
		                	      		xtype: 'button',
		                	      		icon: '${path}/resources/images/icon/accept.png',
		                				handler: function() {
		                	   				var form = signOpinionPanel.getForm();
		                	   				
		                			      	if (form.isValid()) {
		                			      		
		                			            form.submit({
		                			           	    url:'${path}/goalManage/goalApproveAction!signOpinion.action',
		                			           	    method : 'post',
			                			           	params:{
			     		                				uid : selected[0].data.uid,
			     		                	        	applyType : 'selfEva'
			     		                			},
		                			           	    waitTitle: '请等待',
		                			           	    waitMsg : '正在提交...',
		                			               	success: function(form, action) {
		                						    	Ext.MessageBox.show({
		                						           title: '提示',
		                						           msg: '提交成功',
		                						           icon:Ext.MessageBox.INFO,
		                						           buttons:Ext.Msg.OK,
		                						           closable:true,
		                						           fn: opinionWindow.hide()
		                						        });
		                						    	//重新加载下面gird的数据 
		                						    	applyPerson.reloadGrid();
		                						    	selfEvaGoal.reloadGrid("goal.dateBatch.uid="+dateBatchUid+" and goal.ownerDept.uid="+ownerDeptUid+" and goal.ownerPerson.uid="+ownerPersonUid);
		                			       			},
		                			     		    failure: function(form, action) {
		                							    Ext.MessageBox.show({
		                							      title: '提示',
		                							      msg: "系统错误",
		                							      icon:Ext.MessageBox.ERROR,
		                							      buttons:Ext.Msg.OK,
		                							      closable:true
		                							    });
		                				            }
		                			          });
		                			     }
		                		     	}	
		                	      	},
		                	       {//创建“取消”按钮
		                	      		text: '取消',
		                	      		xtype: 'button',
		                	      		icon: '${path}/resources/images/icon/cancel.png',
		                			    handler: function() {
		                				   opinionWindow.hide();
		                	            }
		                	      	}
		                	      ]
		                		});
			                	
			                		var opinionWindow = new Ext.Window({
			                			modal: true,
			                			title: '审核/签署意见',
			                			width: 680,
			                			height: 450,
			                			autoScroll:true,
			                			tbar: ['->',opinion_tb],
			                			closeAction: 'hide'
			                		});
			                		
			                		var signOpinionPanel = new Ext.FormPanel({
				                	      
	              					      frame: true,
				                	      bodyStyle:'padding:5px 5px 0',
				                	      reader: new Ext.data.JsonReader({
					              				successProperty: 'success',
					              				root: 'info'
					              			 },['opinionUid','action','opinion']
					              		  ),
				                	      items: [{
				          			        xtype:'textfield',
				        			        fieldLabel: '主键',
				        			        name:'opinionUid',
				        			        readOnly : true,
			                	            allowBlank: true,
				        			        hidden : true
				        			   	 },{
				                	    	  xtype:'radiogroup',
				                	    	  fieldLabel : '提交动作', 
				                	    	  name: 'action',
				                	    	  anchor:'95%',
				                	    	  columns: 3 ,
				                	    	  items:[
				                	    	      {boxLabel: "同意", name: 'action',inputValue: 'agree',checked: true},  
				                	    	      {boxLabel: "否决", name: 'action',inputValue: 'reject'},  
				                	    		  {boxLabel: "退回", name: 'action',inputValue: 'goback'}  
				                	    	  ], 
				                	          allowBlank: false,
				                	          hidden: finishStatus=='submit'||finishStatus=='goback'?true:false
				                	      },{
				                	    	  xtype: 'textarea',
				          			          fieldLabel: '目标内容',
				          			          name:'content',
				          			          field: 'content',
				          			          height:200,
				          			          allowBlank: false,
				          			          width: 400,
				          			          height : 100
				                	      }]
				                	    });		                		
			                		Ext.Ajax.request({
			                			url: '${path}/goalManage/goalApplyAction!getOpinions.action',
			                			method: 'POST',
			                			params:{
			                				uid : selected[0].data.uid,
			                	        	applyType : 'selfEva'
			                			},
			                			success: function (response) {
			                				var opinions = Ext.decode(response.responseText).info;
			                				for( var i = 0 ; i < opinions.length ; i++){
					                			var opinionPanel = new Ext.Panel({
				                				  frame: true,
						                	      bodyStyle:'padding:5px 5px 0',
						                	      
						                	      title: '审核时间:'+opinions[i].approveTime+
						                	        '&nbsp;&nbsp;&nbsp;&nbsp;审核部门:'+opinions[i].approveDept+
						                	      	'&nbsp;&nbsp;&nbsp;&nbsp;审核人:'+opinions[i].approvePerson+
						                	      	'&nbsp;&nbsp;&nbsp;&nbsp;审核动作:'+opinions[i].action+'&nbsp;&nbsp;&nbsp;&nbsp;',
						                	      html:opinions[i].opinion
						                	    });
					                			
						                		opinionWindow.add(opinionPanel);
					                		}
					                		
					                		//加载详情表单的数据
					            			signOpinionPanel.getForm().load( {
					            	           	url : '${path}/goalManage/goalApplyAction!findOpinion.action',
					            	           	params : {
					            	           		uid : selected[0].data.uid,
					                	        	applyType : 'selfEva'
					            				},
					            	           	
					            	           	waitTitle: '请等待',
					            	           	waitMsg : '正在载入数据...',
					            	           	success : function(form,action) {
					            	           	},
					            	           	failure : function(form,action) {

					            					var o_response = Ext.util.JSON.decode(action.response.responseText)
					            			    	if(!o_response.success){
					            				    	Ext.MessageBox.show({
					            						     title: '提示',
					            						     msg: o_response.info.msg,
					            						     icon:Ext.MessageBox.ERROR,
					            						     buttons:Ext.Msg.OK,
					            						     closable:true
					            					    });
					            			    	}
					            	           	}
					            	       	});
					            			opinionWindow.add(signOpinionPanel);
					                		opinionWindow.show();
			                			},
			                			failure: function(form, action) {
			                	        }
			                		});
			                		
			                		
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
				
				//添加一个按钮
				tb.insert(5,{
	           		id: this.divId+'_view-opinion_button',
	           		hidden : finishStatus=='no'?true:false,
	           		xtype: 'button',
	           		text: '查看意见',
	           		icon: '${path}/resources/images/icon/user_comment.png',
	   				listeners: { 
	   					click: function(){
	   						var selModel = selfEvaGoalPanel.getSelectionModel();
			                if (selModel.hasSelection()) {
			                	
			                	var selected = selModel.getSelections();
			            		
			                		var opinionWindow = new Ext.Window({
			                			modal: true,
			                			title: '查看意见',
			                			width: 680,
			                			height: 450,
			                			autoScroll:true,
			                			closeAction: 'hide'
			                		});
			                		
			                		Ext.Ajax.request({
			                			url: '${path}/goalManage/goalApplyAction!getOpinions.action',
			                			method: 'POST',
			                			params:{
			                				uid : selected[0].data.uid,
			                				applyType:'selfEva'
			                			},
			                			success: function (response) {
			                				var opinions = Ext.decode(response.responseText).info;
			                				for( var i = 0 ; i < opinions.length ; i++){
					                			var opinionPanel = new Ext.Panel({
				                				  frame: true,
						                	      bodyStyle:'padding:5px 5px 0',
						                	      
						                	      title: '审核时间:'+opinions[i].approveTime+
						                	        '&nbsp;&nbsp;&nbsp;&nbsp;审核部门:'+opinions[i].approveDept+
						                	      	'&nbsp;&nbsp;&nbsp;&nbsp;审核人:'+opinions[i].approvePerson+
						                	      	'&nbsp;&nbsp;&nbsp;&nbsp;审核动作:'+opinions[i].action+'&nbsp;&nbsp;&nbsp;&nbsp;',
						                	      html:opinions[i].opinion
						                	    });
					                			
						                		opinionWindow.add(opinionPanel);
					                		}
					                		opinionWindow.show();
			                			},
			                			failure: function(form, action) {
			                	        }
			                		});
			                		
			                		
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
				
				//----上下型页面，上页面行点击，下页面重新加载--------------
				
				var applyPersonPanel = applyPerson.getGridPanel();
				
				//当上面grid行点击时，重新加载下面grid的数据
				applyPersonPanel.on( "rowclick" , function( gridPanel,rowIndex,e){
				
					var selModel = gridPanel.getSelectionModel();
				    if (selModel.hasSelection()) {//有选中行
				        var selected = selModel.getSelections();//获取选择的列
				        
				        dateBatchUid = selected[0].data.dateBatchUid;
				        ownerDeptUid = selected[0].data.ownerDeptUid;
				        ownerPersonUid = selected[0].data.ownerPersonUid;
				        
						//重新加载下面gird的数据
						selfEvaGoal.reloadGrid("goal.dateBatch.uid="+dateBatchUid+" and goal.ownerDept.uid="+ownerDeptUid+" and goal.ownerPerson.uid="+ownerPersonUid);
					}
				})
				
		</script>
	</head>
	<body>
		<div id='${pageId}_1'></div>
		<div id='${pageId}_2'></div>
	</body>
</html>
