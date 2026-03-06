<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa Thông Tin</title>
</head>
<body>
    <div align="center">
        <h2>Sửa Thông Tin Người Dùng</h2>
        
        <form action="<c:url value='/teacher/update'/>" method="post"> 
        	<input type="hidden" name="id" value="${USER_EDIT.id}" />
        	
            <table border="1" cellpadding="10">
                <tr>
                    <td>Tên đăng nhập:</td>
                    <td><input type="text" value="${USER_EDIT.userName}" readonly style="background-color: #e9e9e9;"/></td>
                </tr>
                <tr>
                    <td>Họ và tên:</td>
                    <td><input type="text" name="fullName" value="${USER_EDIT.fullName}" required/></td>
                </tr>
                <tr>
                    <td>Số điện thoại:</td>
                    <td><input type="text" name="phone" value="${USER_EDIT.phone}" required/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" value="${USER_EDIT.email}" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Lưu Thay Đổi</button>
                        <button type="button" onclick="window.location.href='<c:url value="/teacher-home"/>'">Hủy bỏ</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>