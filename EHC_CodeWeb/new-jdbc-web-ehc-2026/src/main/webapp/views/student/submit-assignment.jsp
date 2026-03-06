<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Nộp Bài Tập</title></head>
<body>
    <div align="center">
        <h2>Nộp Bài Làm Của Bạn</h2>
        
        <form action="<c:url value='/student/submit'/>" method="post" enctype="multipart/form-data"> 
            <input type="hidden" name="assignmentId" value="${param.assignmentId}" />
            
            <table border="1" cellpadding="10">
                <tr>
                    <td>Chọn file bài làm (PDF, Word, Zip...):</td>
                    <td><input type="file" name="submissionFile" required/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Xác Nhận Nộp Bài</button>
                        <button type="button" onclick="history.back()">Hủy bỏ</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>