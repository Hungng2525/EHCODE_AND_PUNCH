<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập hệ thống</title>
</head>
<body>
    <div align="center">
        <h2>Đăng Nhập</h2>
        
        <form action="<c:url value='/login'/>" method="post"> 
            <table border="1" cellpadding="10">
                <tr>
                    <td>Tên đăng nhập:</td>
                    <td><input type="text" name="username" required/></td>
                </tr>
                <tr>
                    <td>Mật khẩu:</td>
                    <td><input type="password" name="password" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Vào Lớp Học</button>
                        
                        <button type="button" onclick="window.location.href='<c:url value="/register"/>'">Đăng kí vào lớp</button>
                    </td>
                </tr>
            </table>
            
            <% if (request.getAttribute("message") != null) { %>
                <p style="color: red;"><%= request.getAttribute("message") %></p>
            <% } %>
            
        </form>
    </div>
</body>
</html>