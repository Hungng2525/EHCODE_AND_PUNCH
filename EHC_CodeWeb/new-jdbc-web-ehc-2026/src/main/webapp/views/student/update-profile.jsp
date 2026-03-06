<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cập Nhật Thông Tin</title>
</head>
<body>
    <div align="center">
        <h2>Cập Nhật Thông Tin Cá Nhân</h2>
        
        <form action="<c:url value='/student/update-profile'/>" method="post"> 
            <table border="1" cellpadding="10">
                <tr>
                    <td>Tên đăng nhập:</td>
                    <td><input type="text" value="${USER_INFO.userName}" readonly style="background-color: #e9e9e9;"/></td>
                </tr>
                <tr>
                    <td>Họ và tên:</td>
                    <td>
                        <input type="text" value="${USER_INFO.fullName}" readonly style="background-color: #e9e9e9;"/>
                        <br><small style="color:red;">(Không được phép sửa)</small>
                    </td>
                </tr>
                <tr>
                    <td>Số điện thoại:</td>
                    <td><input type="text" name="phone" value="${USER_INFO.phone}" required/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" value="${USER_INFO.email}" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Lưu Thay Đổi</button>
                        <button type="button" onclick="history.back()">Hủy bỏ</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>