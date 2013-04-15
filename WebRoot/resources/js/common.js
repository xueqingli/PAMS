var sScreenWidth = screen.availWidth;//当前屏幕的有效宽度
var sScreenHeight = screen.availHeight;//当前屏幕的有效高度

/**产生一个不重复的数字，将此加入url连接后，为了防止页面缓存*/
randomNumber = function ()
{
	var today = new Date();//获取当前日期，如Tue Sep 29 16:08:05 UTC+0800 2009 
	var num = Math.abs(Math.sin(today.getTime()));//获取当前时间距离标准日期之间经过的毫秒数，并进行数学转换
	return num;  
}

appendLink = function (url,param){
	var link = url.index('?')>-1?url+"&"+param:url+"?"+param
	return link; 
}

doGet = function (url){
	window.location.href=encodeURI(url);
}

doPost = function(object) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", object.url);

	var params = object.params;
    for ( var i in params) {
        if (params.hasOwnProperty(i)) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = i;
            input.value = params[i];
            form.appendChild(input);
        }
    }

    document.body.appendChild(form);

    form.submit();
}

showTip = function (data, metadata, record, rowIndex, columnIndex, store) {  
	metadata.attr = 'ext:qtip='+data;  
	return data;  
}; 