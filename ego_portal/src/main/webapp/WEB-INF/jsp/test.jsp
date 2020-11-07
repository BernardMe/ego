<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>测试页面</title>

<link href="/css/ego.css" rel="stylesheet"/>

<script type="text/javascript" src="/js/jquery-1.6.4.js" charset="utf-8"></script>
<script type="text/javascript">

$(function() {
	
	$("button").click(function() {
		$.ajax({
			type: "post",
			url: "http://localhost:8081/demo1",
			dataType: "jsonp",
			jsonpCallback: "a123",
			success: function(data) {
				alert(data);
			},
			error: function(){
                alert('失败了！');
            }
		});
	})
	
});
</script>

</head>
<body>

<button>提交</button>




</body>
</html>