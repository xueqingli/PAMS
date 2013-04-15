/**
 
* 下拉树，支持ComboBox和TreePanel的大部分配置参数，<br>
 
* 另新增两个配置参数：sm和autoCollpase，其中sm为SelectionModel的简写，支持：'all','folder','leaf';<br>
 
* autoCollapse,布尔值，是否在ComboBox关闭的时候自动关闭树<br>
 
* 特点是可以给树设定最小高度，自动高度，无滚动条支持。
 
* @class Ext.form.ComboBoxTree
 
* @extends Ext.form.ComboBox
 
* @author xiaosilent<xiaosilent@gmail.com>
 
*/
Ext.form.ComboBoxTree = Ext.extend(Ext.form.ComboBox, {
      folderText : '',
      folderChar : '',
      checkable : false,
      editable : true,
      sm : 'all',
      mode : 'local',
      triggerAction : 'all',
      displayField : 'name',
      valueField : 'id',
      minHeight : 180,
      rootVisible : false,
      autoCollapse : false,
      store : new Ext.data.SimpleStore({
            fields : ['id', 'text'],
            data : [[]]
          }),
      putValue : function(text, value) {
        try {
          var combo = this;
          
		  Ext.getCmp(this.relativeField).setValue(value);

          combo.setValue(text);
        } catch (err) {
          alert(err);
        }
      },
 
      onRender : function(ct, position) {
        if (this.hiddenName && !Ext.isDefined(this.submitValue)) {
          this.submitValue = false;
        }
        Ext.form.ComboBox.superclass.onRender.call(this, ct, position);
        if (this.hiddenName) {
          this.hiddenField = this.el.insertSibling({
                tag : 'input',
                type : 'hidden',
                name : this.hiddenName,
                id : (this.hiddenId || Ext.id())
              }, 'before', true);
        }
        if (this.visibleName) {
          this.el.set({
                name : this.visibleName
              });
        }
        if (Ext.isGecko) {
          this.el.dom.setAttribute('autocomplete', 'off');
        }
        if (!this.lazyInit) {
          this.initList();
        } else {
          this.on('focus', this.initList, this, {
                single : true
              });
        }
      },
      initList : function() {
        if (this.list) {
          return;
        }
        this.list = new Ext.Layer({
              cls : 'x-combo-list',
              constrain : true
            });
        this.list.setWidth(Math.max(this.wrap.getWidth(), 100));
        this.view = new Ext.DataView({});
        var combo = this;
        combo.tree = new Ext.tree.TreePanel({
              border : false,
              root : this.root || new Ext.tree.AsyncTreeNode({
                    id : 'root'
                  }),
              allowUnLeafClick : this.allowUnLeafClick,
              dataUrl : this.dataUrl,
              renderTo : this.list.id,
              autoScroll : this.autoScroll || true,
              height : this.height,
              rootVisible : this.rootVisible || false,
              bodyCfg : {
                style : 'background-color:#ffffff;  height:' + this.minHeight + 'px; min-height:' + this.minHeight + 'px;'
              },
              tbar : [{
                    text : '展开',
                    handler : function() {
                      combo.tree.expandAll();
                    }
                  }, '-',{
                    text : '闭合',
                    handler : function() {
                      combo.tree.collapseAll();
                    }
                  }, '-'],
              listeners : {
                'click' : function(node) {
                  if (node.isLeaf()) {
                    if (combo.sm == 'folder') {
                      return;
                    }
                  } else {
                    if (combo.sm == 'leaf') {
                      return;
                    }
                  }
                  if (typeof(node.attributes.checked) == 'undefined') {
                    var tttext, iiid;
                    if (!node.isLeaf() && !this.allowUnLeafClick) {
						return;
                    } else {
                      tttext = node.attributes.parentsText ? node.attributes.parentsText : node.text;
                    }
                    if (!node.isLeaf() && combo.folderChar.length > 0) {
                      iiid = combo.folderChar + '_' + node.id;
                    } else {
                      iiid = node.id;
                    }
 
                    combo.putValue(tttext, iiid);
                    combo.collapse();
                    combo.fireEvent('select', combo, node);
                    combo.el.dom.focus();
                  } else {
                    node.ui.toggleCheck();
                  }
                },'beforeload': function(node){
		           this.loader.dataUrl = this.dataUrl+'?uid=' + node.id;
            	}
              }
            });
 
        var tbar = combo.tree.getTopToolbar();
 
        if (combo.tree.loader.baseParams.check == true) {
          tbar.add({
                text : '全选',
                handler : function() {
                  combo.tree.root.cascade(function() {
                        this.ui.toggleCheck(true);
                      });
                  tbar.items.get(5).handler.call(this);
                }
              });
        }
 
        tbar.add({
              text : '清空',
              handler : function() {
                combo.tree.root.cascade(function() {
                      this.ui.toggleCheck(false);
                    });
                combo.clearValue();
                combo.collapse();
              }
            });
 
        if (combo.tree.loader.baseParams.check == true) {
          tbar.add('-', {
                text : '确认',
                handler : function() {
                  if (combo.tree.loader.baseParams.check == true) {
                    var checked = combo.tree.getChecked();
                    var names = "", ids = "";
                    for (var i = 0; i < checked.length; i++) {
                      var node = checked[i];
                      if (!node.isLeaf() && combo.folderText.length > 0) {
                        names += "[" + combo.folderText + "]" + node.text + "，";
                      } else {
                        names += node.text + "，";
                      }
                      if (!node.isLeaf() && combo.folderChar.length > 0) {
                        ids += combo.folderChar + "_" + node.id + ",";
                      } else {
                        ids += node.id + ",";
                      }
                    }
                    if (names.length > 1) {
                      names = names.substring(0, names.length - 1);
                    }
                    if (ids.length > 1) {
                      ids = ids.substring(0, ids.length - 1);
                    }
                    combo.putValue(names, ids);
                    combo.setValue(ids);
                    combo.fireEvent('select', combo);
                    combo.fireEvent('change', combo, ids);
                  }
                  combo.collapse();
                }
              });
        }
 
        tbar.doLayout();
 
        this.innerList = this.list.createChild();
      },
 
      onKeyUp : function(e) {
        var k = e.getKey();
        if (this.editable !== false && this.readOnly !== true && (k == e.BACKSPACE || !e.isSpecialKey())) {
 
          this.lastKey = k;
          this.dqTask.delay(500); 
        }
        Ext.form.ComboBox.superclass.onKeyUp.call(this, e);
      },
 
      doQuery : function(q, forceAll) {
        q = Ext.isEmpty(q) ? '' : q;
        var qe = {
          query : q,
          forceAll : forceAll,
          combo : this,
          cancel : false
        };
        if (this.fireEvent('beforequery', qe) === false || qe.cancel) {
          return false;
        }
        q = qe.query;
        forceAll = qe.forceAll;
        if (q.length >= this.minChars) {
          if (this.lastQuery != q) {
            this.lastQuery = q;
            if (q.length >= this.minChars) {
              combo = qe.combo;
              combo.tree.loader.baseParams.query = q;
              combo.tree.root.reload();
            }
            this.expand();
          } else {
            this.selectedIndex = -1;
            this.onLoad();
          }
        }
      },
 
      collapse : function() {
        if (this.autoCollapse) {
          this.tree.collapseAll();
        }
        Ext.form.ComboBoxTree.superclass.collapse.call(this);
      }
    });
 
Ext.reg('combotree', Ext.form.ComboBoxTree);