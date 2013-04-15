<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/WEB-INF/platformTags.tld" prefix="pt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<c:set var="entityName" value="com.lxq.platform.goalManage.pojo.PerformanceGoal"></c:set>
    
<html>
	<head>
		<script type="text/javascript">
			var params = {		
			    /**panel渲染元素**/
				divId : '${pageId}',
				
			    /**项目名称**/
			    path: '${path}',
			
				/**导出文件的名称**/
				fileName: '部门目标查询',

			    /**弹出窗口标题**/
			    title: "部门目标查询",
				
			    /**实体对象名称**/
			    entityName: '${entityName}',
				
			    /**action地址**/
			    actionUrl: "/goalManage/goalQuery",
				
			    /**grid查询参数*/
			    baseParams: {goalType:'dept'},
			    
			    /**每页显示的记录数**/
			    pageSize: 20,
			    
			    /**排序字段**/
			    sort: 'uid',
			    
				/**排序方式**/
		    	dir: 'asc',	
				
		    	/**grid高度*/
				gridHeight : Ext.get("center").getHeight()-Ext.get("south").getHeight()-40,

				/**弹出窗口的宽度**/
				formWidth: 400,
				
				/**弹出窗口的高度**/
				formHeight: 450,
				
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
		            name: 'batchType',
		            field: 'dateBatch.batchType.text' ,
		            header: '期次类型',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'combo',
		            name: 'batchType',
		            field: 'dateBatch.batchType.uid' ,
		            header: '期次类型',
		            sortable: true,
		            searchable: true,
		            codeNo: 'batchType',
		            hidden: true
		        },{
		            type: 'textfield',
		            name: 'dateBatch',
		            field: 'dateBatch.dateBatch' ,
		            header: '期次',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'content',
		            field: 'content' ,
		            header: '目标内容',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'standard',
		            field: 'standard' ,
		            header: '评分标准',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'goalType',
		            field: 'goalType.text' ,
		            header: '目标类型',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'fullScore',
		            field: 'fullScore' ,
		            header: '标准分',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'finalScore',
		            field: 'finalScore' ,
		            header: '最终得分',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'ownerDept',
		            field: 'ownerDept.deptName' ,
		            header: '执行机构',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'ownerPerson',
		            field: 'ownerPerson.userName' ,
		            header: '执行人',
		            sortable: true,
		            searchable: true
		        },{
		            type: 'textfield',
		            name: 'beginStatus',
		            field: 'beginStatus.text' ,
		            header: '期次初状态',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'beginStatus',
		            field: 'endStatus.text' ,
		            header: '期次末状态',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'createDept',
		            field: 'createDept.deptName' ,
		            header: '创建部门',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'createPerson',
		            field: 'createPerson.userName' ,
		            header: '创建人',
		            sortable: true,
		            searchable: false
		        },{
		            type: 'textfield',
		            name: 'createTime',
		            field: 'createTime' ,
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
			        xtype:'textfield',
			        fieldLabel: '所属期次',
			        name:'dateBatch',
			        field: 'dateBatch.dateBatch',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标内容',
			        name:'content',
			        field: 'content',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '评分标准',
			        name:'standard',
			        field: 'standard',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标类型',
			        name:'goalType',
			        field: 'goalType.uid',
			        allowBlank: false
			    },{
			        xtype:'numberfield',
			        fieldLabel: '标准得分',
			        name:'fullScore',
			        field: 'fullScore',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '执行部门',
			        name:'ownerDept',
			        field: 'ownerDept.uid',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '执行人',
			        name:'ownerPerson',
			        field: 'ownerPerson.uid',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '创建部门',
			        name:'ownerDept',
			        field: 'ownerDept.uid',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '创建人',
			        name:'ownerPerson',
			        field: 'ownerPerson.uid',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '创建日期',
			        name:'createTime',
			        field: 'createTime',
			        allowBlank: false
			    },{
			        xtype:'numberfield',
			        fieldLabel: '最终得分',
			        name:'finalScore',
			        field: 'finalScore',
			        allowBlank: false
			    },{
			        xtype:'textfield',
			        fieldLabel: '目标状态',
			        name:'status',
			        field: 'beginStatus.text',
			        allowBlank: false
			    }],
			    
				//new : 新增权限,info ： 查看权限,edit ：编辑权限,cancel ：取消权限，all ：所有权限
				buttons:[
					{ name : 'add_button', visible : false},
					{ name : 'query_button', visible : true},
					{ name : 'update_button', visible : false},
					{ name : 'delete_button', visible : false},
				]
			};
			
			var goalQuery = new com.lxq.js.GridAndForm.BaseGridAndForm(params);
			goalQuery.initGridAndForm();
			var goalQueryPanel = goalQuery.getGridPanel();
			var tb = goalQuery.getToolBar();
			
			//添加一个按钮
			tb.insert(4,{
           		id: this.divId+'_view-opinion_button',
           		xtype: 'button',
           		text: '查看意见',
           		icon: '${path}/resources/images/icon/user_comment.png',
   				listeners: { 
   					click: function(){
   						var selModel = goalQueryPanel.getSelectionModel();
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
			
		</script>
	</head>
	<body>
		<div id='${pageId}'></div>
	</body>
</html>
