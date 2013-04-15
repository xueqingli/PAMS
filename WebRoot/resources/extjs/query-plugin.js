Ext.namespace("com.lxq.js");

com.lxq.js.query = function (store,data,pageSize){

	this.store = store;
	this.data = data;
	this.pageSize = pageSize;

	/**数字比较运算符*/
	var numericOp = new Ext.data.SimpleStore({ 
	    fields: ['value','text'], 
	    data : [['=','等于'],['<>','不等于'],['>','大于'],['>=','大于或等于'],['<','小于'],['<=','小于或等于'],['is null','为空'],['is not null','不为空']] 
	});
	var datetimeOp = numericOp;
	/**字符串比较运算符*/
	var stringOp = new Ext.data.SimpleStore({ 
	    fields: ['value','text'], 
	    data : [['=','等于'],['<>','不等于'],['>','大于'],['>=','大于或等于'],['<','小于'],['<=','小于或等于'], 
	    ["like '|%'",'以...开头'],["like '%|'",'以...结尾'],["like '%|%'",'包含'],["not like '%|%'",'不包含'], 
	    ['is null','为空'],['is not null','不为空']] 
	});
	
	/**下拉菜单比较运算符*/
	var lookupOp = new Ext.data.SimpleStore({ 
	    fields: ['value','text'], 
	    data : [['=','等于'],['<>','不等于'],['is null','为空'],['is not null','不为空']] 
	});
	
	/**根据对象数组中对象名称和属性值，查找该对象其他属性的值*/
	findRecordValue = function(store,prop1, prop1Value,prop2){ 
	    var record;
	    if(store.getCount() > 0){ 
	        store.each(function(r){ 
	            if(r.data[prop1] == prop1Value){ 
	                record = r;
	                return false;
	            } 
	        });
	    }
	    return record ? record.data[prop2] : '';
	}
	
	/**根据操作符值展示操作符名称*/
	this.displayOperator = function(v){
	    switch(v){ 
	        case 'is null': return '为空';
	        case 'is not null': return '不为空';
	        case "like '|%'": return '以...开头';
	        case "like '%|'": return '以...结尾';
	        case "like '%|%'": return '包含';
	        case "not like '%|%'": return '不包含';
	        case '=': return '等于';
	        case '<>': return '不等于';
	        case '>': return '大于';
	        case '>=': return '大于或等于';
	        case '<': return '小于';
	        case '<=': return '小于或等于';
	        default: return v;
	    } 
	}
	/**下拉菜单显示的名称*/
	var comboText;
	/**下拉树显示的名称*/
	var combotreeText;
	
	var resetColEditor = function(queryPanel,queryType,fieldsDef,colIndex,fieldValue){
		
		var dataType = findRecordValue(fieldsDef,'value',fieldValue,'type');
		if(dataType == '') return;//如果选择”字段名“，则推出
		
		var colEditorStyle;		
		if((queryType== 'simple' && colIndex == 1) || (queryType== 'advanced' && colIndex == 3)){
			var _store;
			switch(dataType){ 
				case 'textfield': _store = stringOp;break;
				case 'datefield': _store = datefieldtimeOp;break;
				case 'numberfield': _store = numericOp;break;
				case 'combo': _store = lookupOp;break;
				case 'combotree': _store = lookupOp;break;
			}
			//创建下拉列表编辑器样式
			colEditorStyle = new Ext.form.ComboBox({
			    store: _store, 
			    mode: 'local', 
			    triggerAction: 'all', 
			    valueField: 'value', 
			    displayField: 'text', 
			    editable: false 
			}) 
		}else{
		     switch(dataType){ 
	               case 'textfield': colEditorStyle = new Ext.form.TextField(); break;
	               case 'datefield': colEditorStyle = new Ext.form.DateField({format:'Y-m-d'}); break;
	                case 'numberfield': colEditorStyle = new Ext.form.NumberField({allowNegative:false,allowDecimals:false}); break;
	                case 'float': colEditorStyle = new Ext.form.NumberField({allowNegative:false}); break;
	                case 'combo':  
	                	 colEditorStyle = new Ext.form.ComboBox({ 
	                    store: findRecordValue(fieldsDef,'value',fieldValue,'store'), 
			            triggerAction: 'all',  
			            valueField: 'uid',  
			            displayField: 'text',  
			            editable: false,
			            listeners : {
			            	change : function(field,newValue,oldValue){
			            		comboText = field.getRawValue();
			            	}
			            }   
	                   });
	                   break;
	               case 'combotree':  
	                   colEditorStyle = new Ext.form.QueryTree({ 
	                    dataUrl: findRecordValue(fieldsDef,'value',fieldValue,'dataUrl'),
		                allowUnLeafClick : true,
		                listeners : {
			            	change : function(field,newValue,oldValue){
			            		combotreeText = field.getRawValue();
			            	}
			            }  
	                   });
	                   break;    
	           }
		}
		//设置内容单元格的编辑器
	   queryPanel.colModel.setEditor(colIndex, new Ext.grid.GridEditor(colEditorStyle));
	} 

	this.openSimpleQueryWindow = function () { 
	
		v_fieldname = '';
		v_operator = '';
		v_value = '';
		v_whereClause = '';
		v_dataType = '';
		v_window = '';
		
		var store = this.store;
		var pageSize = this.pageSize;
		submitQuery = function (){
			if(v_fieldname == '请选择' || v_fieldname == '' ){
				Ext.Msg.alert("错误", "请选择查询字段。"); 
				return;
			}
			if(v_value == '' && v_operator != 'is null' && v_operator != 'is not null'){
				Ext.Msg.alert("错误", "请输入查询内容。");  
				return;
			}
			var s = v_fieldname;
		    var operator = v_operator; 
		    switch(operator){ 
		        case "like '|%'": 
		        case "like '%|'": 
		        case "like '%|%'": 
		        case "not like '%|%'": 
		            s = s + ' '+operator.replace(/\|/g, v_value.replace(/\'/g,"''").replace(/\%/g,"!%")); 
		            break; 
		        case "is null": 
		        case "is not null": 
		            s = s + ' '+operator; 
		            break; 
		        default: 
		            s = s + ' '+operator; 
		            var dataType = v_dataType;
		            switch(dataType){ 
		                case 'textfield': 
		                    s = s + ' '+"'" + v_value.replace(/\'/g,"''") + "'"; 
		                    break; 
		                case 'datefield': 
		                    s = s + ' '+"'" + Ext.util.Format.date(v_value,'Y-m-d') + "'"; 
		                    break; 
		                case 'combo': 
		                    s = s + ' '+"'" + v_value + "'"; 
		                    break;
		                case 'combotree': 
		                    s = s + ' '+"'" + v_value + "'"; 
		                    break;
		                default: 
		                    s = s + ' '+ v_value; 
		                    break; 
		            } 
		            break; 
		    }
			
			store.baseParams.queryClause = s;
			store.load({ 
		        params:{
		        	start:0,
		        	limit:pageSize
		        }  
		    });
		    v_window.close();
		    
		}
		
		//数据格式
		var fieldsDef = new Ext.data.JsonStore({ 
		    fields: ['value','text','type','store','dataUrl'], 
		    data:this.data
		});
				
		//可编辑grid的列格式
		var dsPQ=new Ext.data.JsonStore({ 
		   fields:["fieldname","operator","value"],
		   data:[]
		});
			
		var qRowData = Ext.data.Record.create([ 
		    {name:'fieldname',type:'textfield'}, 
		    {name:'operator',type:'textfield'}, 
		    {name:'value',type:'textfield'}
		]);
		
		var displayValue = function (v,m,record){
		    var operator = record.get('operator');
		    if (operator=='is null'||operator=='is not null') return '';
	
		    var dataType = findRecordValue(fieldsDef,'value',record.get('fieldname'),'type');
		    
		    switch(dataType){
		        case 'datefield': 
		        	try{
			        	return v ? v.dateFormat('Y-m-d'): '';
		        	}catch(e){
		        		return '';
		        	}
		        case 'combo': 
				    return comboText;
		        case 'combotree': 
				    return combotreeText;
				    
		        default: return v;
		    } 
		}
		
		var colM=new Ext.grid.ColumnModel([ 
		    { 
		        dataIndex:"fieldname", 
		        width:130,
		        editor:new Ext.form.ComboBox({ 
		            store: fieldsDef, 
		            mode: 'local', 
		            triggerAction: 'all', 
		            valueField: 'value', 
		            displayField: 'text', 
		            editable: false,
		            listeners:{
		            	change:function(field,newValue,oldValue){
				            var record = dsPQ.getAt(0);//获取当前行记录
				            record.set('operator','=');//重置"operator"列的值为"="
				            record.set('value','');//重置"value"列的值为空
		            	}
			        }
		        }),
		        renderer: function(value, metadata, record, rowIndex, colIndex, store){
		        	if(value == '请选择'){
		                return value;
		        	}else{
			        	return findRecordValue(fieldsDef,'value',value,'text');
		        	}
	            }
		    },{ 
		        width:100, 
		        dataIndex:"operator",
		        renderer: this.displayOperator
		    },{ 
		        dataIndex:"value", 
		        width:130,
		        renderer: displayValue
		    },{
		    	width: 85, 
		    	sortable: false, 
		    	renderer: function (value, metadata, record, rowIndex, colIndex, store) {
					
					v_fieldname = record.get('fieldname');
					v_operator = record.get('operator');
					v_value = record.get('value');
					v_dataType = findRecordValue(fieldsDef,'value',v_fieldname,'type');
					
		    		return '<input type="button" value="查询" onclick = "submitQuery()"/>'; 
		    	}
		    }
		]);
	
	    var r = new qRowData({fieldname:'请选择',operator:'=',value:''});
	    dsPQ.insert(0,r);
	    dsPQ.commitChanges();
	
		var queryPanel = new Ext.grid.EditorGridPanel({ 
		    height:500, 
		    cm:colM, 
		    sm:new Ext.grid.RowSelectionModel({singleSelect:false}), 
		    store:dsPQ, 
		    region:'center', 
		    border: false, 
		    enableColumnMove: false, 
		    enableHdMenu: false, 
		    loadMask: {msg:'加载数据...'}, 
		    clicksToEdit:1,
		    listeners: {
	            //单元格点击时触发，单元格点击时，重新设置列编辑器
	           	'cellclick': function(grid, rowIndex, columnIndex, e){
		    		if (columnIndex==1 || columnIndex==2) { //是"运算符"和"内容"列
		    			var record = grid.getStore().getAt(rowIndex);//获取当前行
		    			resetColEditor(queryPanel,'simple',fieldsDef,columnIndex,record.get('fieldname'));//重置列编辑器
		       		}
		       }
	        }
		});
	 
		var v_window = new Ext.Window({
			modal: true,
			title: '查询条件',
			width: 420,
			height: 70,
			resizable: false,
			frame: true,
			items: [queryPanel]
		});
		v_window.show();
	}
	
	this.openAdvancedQueryWindow = function () { 
		
		var store = this.store;
		var selected_row_index;//当前选定的行
		
		var fieldsDef = new Ext.data.JsonStore({ 
		    fields: ['value','text','type','store','dataUrl'], 
		    data:this.data 
		});
			
		var dsPQ=new Ext.data.JsonStore({ 
		   data:[], 
		   fields:["idx","relation","leftParenthesis","fieldname","operator","value","rightParenthesis"]  
		});
		
		var qRowData = Ext.data.Record.create([ 
		    {name:'idx',type:'numberfield'}, 
		    {name:'relation',type:'textfield'}, 
		    {name:'leftParenthesis',type:'textfield'}, 
		    {name:'fieldname',type:'textfield'}, 
		    {name:'operator',type:'textfield'}, 
		    {name:'value',type:'textfield'}, 
		    {name:'rightParenthesis',type:'textfield'} 
		]);
	
		var displayValue = function (v, params, record){ 
		    var operator = record.get('operator');
		    if (operator=='is null'||operator=='is not null') return '';
		    var dataType = findRecordValue(fieldsDef,'value',record.get('fieldname'),'type');
		    switch(dataType){
		        case 'datefield': 
		        	try{
			        	return v ? v.datefieldFormat('Y-m-d'): '';
		        	}catch(e){
		        		return '';
		        	}
		        case 'combo': 
				    return comboText;
		        case 'combotree': 
				    return combotreeText;
				    
		        default: return v;
		    } 
		}
		var colM=new Ext.grid.ColumnModel([ 
		    { 
		        header:"关系", 
		        dataIndex:"relation", 
		        width:50,
		        renderer: function(v){return (v=='and'?'并且':'或者')}, 
		        editor:new Ext.form.ComboBox({
		            store: new Ext.data.SimpleStore( {
				        fields: ["value", "text"],
				        data: [['and','并且'],['or','或者']]
			        }),
		            mode: 'local',  
			        forceSelection: true,
		            triggerAction: 'all',  
		            allowBlank: false, 
		            valueField: 'value',
		            displayField: 'text',
			        hiddenName:'numberfielderviewsDetail.child_category',  
		            editable: false  
		        })
		    },{ 
		        header:"括号", 
		        dataIndex:"leftParenthesis", 
		        width:40, 
		        editor:new Ext.form.ComboBox({ 
	    		        store: new Ext.data.SimpleStore( {
				        fields: ["value", "text"],
				        data: [['(','('],['((','(('],['(((','((('],['((((','((((']]
			        }),
		            mode: 'local', 
		            triggerAction: 'all', 
		            valueField: 'value', 
		            displayField: 'text', 
		            editable: false 
		        }) 
		    },{ 
		        header:"字段名", 
		        dataIndex:"fieldname", 
		        width:130,
		        renderer: function(v){return findRecordValue(fieldsDef,'value',v,'text');},
		        editor:new Ext.form.ComboBox({ 
		            store: fieldsDef, 
		            mode: 'local', 
		            triggerAction: 'all', 
		            valueField: 'value', 
		            displayField: 'text', 
		            editable: false, 
		            listeners:{
		            	change:function(field,newValue,oldValue){
				           var record = queryPanel.store.getAt(selected_row_index);//获取当前行记录
				           record.set('operator','=');//重置"operator"列的值为"="
				           record.set('value','');//重置"value"列的值为空
		            	}
		            } 
		        }) 
		    },{ 
		        header:"运算符", 
		        width:80, 
		        dataIndex:"operator",
		        renderer: this.displayOperator
		    },{ 
		        header:"内容", 
		        dataIndex:"value", 
		        width:130,
		        renderer: displayValue
		    },{ 
		        header:"括号", 
		        width:40, 
		        dataIndex:"rightParenthesis", 
		        editor:new Ext.form.ComboBox({ 
	        		    store: new Ext.data.SimpleStore( {
				        fields: ["value", "text"],
				        data: [[')',')'],['))','))'],[')))',')))'],['))))','))))']]
			        }),
		            mode: 'local', 
		            triggerAction: 'all', 
		            valueField: 'value', 
		            displayField: 'text', 
		            editable: false 
		        }) 
		    } 
		]);
	
		var queryPanel = new Ext.grid.EditorGridPanel({ 
		    width:470, 
		    height:250, 
		    cm:colM, 
		    sm:new Ext.grid.RowSelectionModel({singleSelect:false}), 
		    store:dsPQ, 
		    region:'center', 
		    border: false, 
		    enableColumnMove: false, 
		    enableHdMenu: false, 
		    loadMask: {msg:'加载数据...'},
		    clicksToEdit:1,
	     	listeners: {  
	     		//列选择时触发
	              'rowclick': function(grid, rowIdx, e){
	                  selected_row_index = rowIdx;//当前行点击时更新“selected_row_index”的值
	              },
	              //单元格点击时触发，单元格点击时，重新设置列编辑器
	              'cellclick': function(grid, rowIndex, columnIndex, e){
		    		if (columnIndex!=3 && columnIndex!=4) return;//不是"运算符"和"内容"列
		    		var record = grid.getStore().getAt(rowIndex);//获取当前行
		    		resetColEditor(queryPanel,'advanced',fieldsDef,columnIndex,record.get('fieldname'));//重置列编辑器
		       }	
	           },
		    tbar:[ 
		        {text:'添加',handler:function(){ 
		                var count = dsPQ.getCount();
		                var r = new qRowData({idx:count,relation:'and',leftParenthesis:'',fieldname:'',operator:'=',value:'',rightParenthesis:''});
		                dsPQ.commitChanges();
		                dsPQ.insert(count,r);
		            } 
		        }, 
		        {text:'删除',handler:function(){
		                var selections = queryPanel.getSelectionModel().getSelections();
		                for(var i = 0;i < selections.length;i++){  
		                    dsPQ.remove(selections[i]); 
		                } 
		            } 
		        }, 
		        {text:'重置',handler:function(){dsPQ.removeAll();}}, 
		        '->',
		        {text:'查询',handler:function(){getFilter(queryPanel)}}
		    ]
		});
	
	 var checkFilter = function(grid){ 
	    var n = grid.store.getCount(); 
	    var leftPLen = 0; 
	    var rightPLen = 0; 
	    for (var i=0; i<n; i++){ 
	        var record = grid.store.getAt(i); 
	        if (record.get('fieldname')==''){ 
	            grid.getSelectionModel().selectRow(i); 
	            Ext.Msg.alert("错误", "请选择查询字段。"); 
	            return false; 
	        } 
	        leftPLen += record.get('leftParenthesis').length; 
	        rightPLen += record.get('rightParenthesis').length; 
	        switch(record.get('operator')){ 
	            case "like '|%'": 
	            case "like '%|'": 
	            case "like '%|%'": 
	            case "not like '%|%'": 
	            case '=': 
	            case '<>': 
	            case '<': 
	            case '>': 
	            case '<=': 
	            case '>=': 
	                if (record.get('value')==''){ 
	                    grid.getSelectionModel().selectRow(i); 
	                    Ext.Msg.alert("错误", "请输入内容。"); 
	                    return false; 
	                } 
	        } 
	    } 
	    if (leftPLen!=rightPLen){ 
	        Ext.Msg.alert("错误", "左括号与右括号数量不匹配，请检查。"); 
	        return false; 
	    } 
	    return true;
	} 
	var getFilter = function (grid){
	
	    if (!checkFilter(grid)) return ""; 
	    var s = []; 
	    var n = grid.store.getCount(); 
	    for (var i=0; i<n; i++){ 
	        var record = grid.store.getAt(i); 
	        if (i>0) s.push(record.get('relation')); 
	        s.push(record.get('leftParenthesis')); 
	        s.push(record.get('fieldname')); 
	        var operator = record.get('operator'); 
	        switch(operator){ 
	            case "like '|%'": 
	            case "like '%|'": 
	            case "like '%|%'": 
	            case "not like '%|%'": 
	                s.push(operator.replace(/\|/g, record.get('value').replace(/\'/g,"''").replace(/\%/g,"!%")));//for sql server 
	                break; 
	            case "is null": 
	            case "is not null": 
	                s.push(operator); 
	                break; 
	            default: 
	                s.push(operator); 
	                var dataType = findRecordValue(fieldsDef,'value',record.get('fieldname'),'type'); 
	                switch(dataType){ 
	                    case 'combo': 
	                        s.push("'" + record.get('value') + "'"); 
	                        break; 
	                    case 'textfield': 
	                        s.push("'" + record.get('value').replace(/\'/g,"''") + "'"); 
	                        break; 
	                    case 'datefield': 
	                        s.push("'" + Ext.util.Format.date(record.get('value'),'Y-m-d') + "'"); 
	                        break; 
	                    default: 
	                        s.push(record.get('value')); 
	                        break; 
	                } 
	                break; 
	        } 
	        s.push(record.get('rightParenthesis')); 
	    } 
	    
	    store.baseParams.queryClause = s.join(' ');
		store.load({ 
	        params:{
	        	start:0,
	        	limit:pageSize
	        }  
	    });
	    v_window.close(); 
	} 
		var v_window = new Ext.Window({
			modal: true,
			title: '查询条件',
			width: 500,
			height: 250,
			items: [queryPanel]
		}).show();
	}
}


