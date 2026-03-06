<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giao Bài Tập Mới</title>
</head>
<body>
    <div align="center">
        <h2>Giao Bài Tập Cho Sinh Viên</h2>
        
        <form action="<c:url value='/teacher/create-assignment'/>" method="post" enctype="multipart/form-data"> 
            <table border="1" cellpadding="10">
                <tr>
                    <td>Tiêu đề bài tập:</td>
                    <td><input type="text" name="title" required style="width: 300px;"/></td>
                </tr>
                <tr>
                    <td>Mô tả chi tiết:</td>
                    <td><textarea name="description" rows="5" style="width: 300px;"></textarea></td>
                </tr>
                <tr>
                    <td>Đính kèm File (PDF, Word...):</td>
                    <td><input type="file" name="assignmentFile" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Upload & Giao Bài</button>
                        <button type="button" onclick="window.location.href='<c:url value="/teacher-home"/>'">Hủy bỏ</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>