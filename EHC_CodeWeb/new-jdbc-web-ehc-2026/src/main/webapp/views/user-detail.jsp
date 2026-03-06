<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hồ Sơ Người Dùng</title>
</head>
<body>
    <div align="center">
        <h2>Thông Tin Chi Tiết</h2>
        <hr>
        
        <table border="1" cellpadding="10" style="width: 40%; text-align: left;">
            <tr>
                <th style="background-color: #f2f2f2;">Họ và tên:</th>
                <td>${USER_DETAIL.fullName}</td>
            </tr>
            <tr>
                <th style="background-color: #f2f2f2;">Email:</th>
                <td>${USER_DETAIL.email}</td>
            </tr>
            <tr>
                <th style="background-color: #f2f2f2;">Số điện thoại:</th>
                <td>${USER_DETAIL.phone}</td>
            </tr>
            <tr>
                <th style="background-color: #f2f2f2;">Vai trò:</th>
                <td>
                    <c:if test="${USER_DETAIL.roleCode == 'TEACHER'}">Giáo Viên</c:if>
                    <c:if test="${USER_DETAIL.roleCode == 'STUDENT'}">Sinh Viên</c:if>
                </td>
            </tr>
            <tr>
                <th style="background-color: #f2f2f2;">Trạng thái:</th>
                <td>${USER_DETAIL.status}</td>
            </tr>
        </table>
        <br>
        
        <button onclick="history.back()">Quay lại danh sách</button>
    </div>
</body>
</html>