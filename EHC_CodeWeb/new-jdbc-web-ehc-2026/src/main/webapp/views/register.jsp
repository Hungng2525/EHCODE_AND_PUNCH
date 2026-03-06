<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký Sinh viên</title>
</head>
<body>
    <div align="center">
        <h2>Đăng Ký Tài Khoản Sinh Viên</h2>
        
        <form action="<c:url value='/register'/>" method="post"> 
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
                    <td>Họ và tên:</td>
                    <td><input type="text" name="fullName" required/></td>
                </tr>
                <tr>
                    <td>Số điện thoại:</td>
                    <td><input type="text" name="phone" required/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Đăng Ký</button>
                        <a href="<c:url value='/login'/>">Đã có tài khoản? Đăng nhập</a>
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