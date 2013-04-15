<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>登陆页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">td img {display: block;}</style>
<script type="text/javascript" src="${path}/resources/js/common.js"></script>
<script type="text/javascript" src="${path}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${path}/resources/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="${path}/resources/extjs/states.js"></script>
<script language="javascript">
function submitdata(){
	var form = document.getElementById("form");
	var userId = form.userId.value;
	var password = form.password.value;
	if(userId == ""){
		document.getElementById("userdivp").innerHTML="<font size='4' color='red'>用户名不能为空!</font>";
		document.getElementById("userdivp").style.display = "";
		form.userId.focus();
	}else if(password == ""){
		document.getElementById("userdivp").innerHTML="<font size='4' color='red'>密码不能为空!</font>";
		document.getElementById("userdivp").style.display = "";
		form.password.focus();
	}else{
		Ext.Ajax.request({
			url: '${path}/login!login.action',
			method: 'POST',
			params:{
				userId : userId,
	        	password : password
			},
			success: function (response) {
			    if(Ext.decode(response.responseText).success==true){
			    	//模态窗口打开
			    	//window.showModalDialog("${path}/main.jsp?rand="+new Date(),"","dialogWidth="+sScreenWidth+";dialogHeight="+sScreenHeight);
			    	//链接重定向打开
			    	window.location.href='${path}/main.jsp?rand='+new Date();
				}else{
				    document.getElementById("userdivp").innerHTML="<font size='4' color='red'>"+Ext.decode(response.responseText).msg+"</font>";
		            document.getElementById("userdivp").style.display = "";
				}
			},
			failure: function(form, action) {
	           	document.getElementById("userdivp").innerHTML="<font size='4' color='red'>"+action.result.msg+"系统错误</font>";
		        document.getElementById("userdivp").style.display = "";
	        }
		});
	}  
}

function resetform(){
    var form = document.getElementById("form");
    form.userId.value="";
    form.password.value="";
}

function setDiv1(){
    var form = document.getElementById("form");
	var stru = document.getElementById("userId").value;
	
	if(stru==""){
		document.getElementById("userdivp").innerHTML="<font size='4' color='red'>用户名不能为空!</font>";
		document.getElementById("userdivp").style.display = "";
		form.userId.focus();
	}else{
		document.getElementById("userdivp").style.display = "none";
	}
}

function setDiv2(){
    var form = document.getElementById("form");
	var strp = document.getElementById("password").value;
	var stru = document.getElementById("userId").value;
	if(stru==""){
	    form.userId.focus();
	}else{
		if(strp==""){
			document.getElementById("userdivp").innerHTML="<font size='4' color='red'>密码不能为空!</font>";
			document.getElementById("userdivp").style.display = "";
			form.password.focus();
		}else{
			document.getElementById("userdivp").style.display = "none";
		}
	}
}
</script>

<style type="text/css">
<!--
body {
	margin-left:180px;
	margin-top: 5px;
	margin-right:50px;
	margin-bottom: 5px;
	overflow:hidden;
	background-attachment: fixed;
}
-->
</style>
</head>
<body bgcolor="#ffffff">
<form action="" name="form" id="form" >
<table border="0" cellpadding="0" cellspacing="0" width="1000">
  <tr>
   <td colspan="3" background="resources/homepage/1_130221085100_1_r1_c1.jpg" width="1000" height="259" align="center" valign="bottom">
       <div id="userdivp" style="display:none"></div>
       <br />
   </td>
   <td background="resources/homepage/spacer.gif" width="1" height="259"></td>
  </tr>
  <tr>
   <td rowspan="3" background="resources/homepage/1_130221085100_1_r2_c1.jpg" width="490" height="62"></td>
   <td>
       <input type="text" id ="userId" name="userId" onblur="setDiv1()" style="height:17px; width:130px; border:solid 0px #cadcb2; font-size:13px;" />
   </td>
   <td rowspan="3" background="resources/homepage/1_130221085100_1_r2_c3.jpg" width="368" height="62"></td>
   <td background="resources/homepage/spacer.gif" width="1" height="22" ></td>
  </tr>
  <tr>
   <td background="resources/homepage/1_130221085100_1_r3_c2.jpg" width="142" height="19"></td>
   <td background="resources/homepage/spacer.gif" width="1" height="19"></td>
  </tr>
  <tr>
   <td>
       <input type="password" id ="password" name="password" onblur="setDiv2()" style="height:17px; width:130px; border:solid 0px #cadcb2; font-size:10px;" />
   </td>
   
   <td background="resources/homepage/spacer.gif" width="1" height="21"></td>
  </tr>
  <tr>
   <td colspan="3"><img name="n1_130221085100_1_r5_c1" src="resources/homepage/1_130221085100_1_r5_c1.jpg" width="1000" height="279" border="0" id="n1_130221085100_1_r5_c1" usemap="#m_1_130221085100_1_r5_c1" alt="" /></td>
   <td background="resources/homepage/spacer.gif" width="1" height="279"></td>
  </tr>
</table>
<map name="m_1_130221085100_1_r5_c1" id="m_1_130221085100_1_r5_c1">
<area shape="rect" coords="478,17,539,44" onclick="submitdata();" href="#" />
<area shape="rect" coords="555,17,617,44" onclick="resetform();" href="#" />
</map>
</form>
</body>
</html>