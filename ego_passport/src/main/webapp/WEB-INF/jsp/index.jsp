<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>登录易购</title>
    <link type="text/css" rel="stylesheet" href="/css/login.css"/>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
  <%   
  Cookie[] cookies = request.getCookies();
  
  if(cookies!=null && cookies.length>0) {
	  for (Cookie c : cookies) {
		  PrintWriter pw = response.getWriter();
		  
		  pw.write("[Cookie]name属性--->" +c.getName() + "value属性--->" + c.getValue()+"<br>");
	  }
  } else {
	  PrintWriter pw = response.getWriter();
	  
	  pw.write("没有cookie");
  }
  
  
  
  
  %> 
</body>
</html>