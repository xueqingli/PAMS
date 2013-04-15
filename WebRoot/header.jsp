<%@ page import="com.lxq.platform.util.DateUtil"%>

<%
	pageContext.setAttribute("pageId",DateUtil.getNowTime("yyyy_MM_dd_HH_mm_ss_SSS"));
%>	