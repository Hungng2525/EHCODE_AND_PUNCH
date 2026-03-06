<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lophocvuinhon.dao.impl.UserDAO" %>
<%@ page import="com.lophocvuinhon.model.UserModel" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test JDBC</title>
</head>
<body>
    <h1>Kiểm tra kết nối Database</h1>
    <%
        UserDAO dao = new UserDAO();
        List<UserModel> list = dao.findAll();
        
        if (list != null) {
            out.println("<h3 style='color:green'>Kết nối thành công! Tìm thấy " + list.size() + " users.</h3>");
        } else {
            out.println("<h3 style='color:red'>Lỗi kết nối! Kiểm tra lại Console.</h3>");
        }
    %>
</body>
</html>