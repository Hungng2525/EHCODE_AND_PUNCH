<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Quản Lý Bài Nộp</title></head>
<body>
    <div align="center">
        <h2>Danh Sách Bài Làm Của Sinh Viên</h2>
        <a href="<c:url value='/teacher-home'/>">Quay lại Bảng Điều Khiển</a>
        <hr>
        <table border="1" cellpadding="10" style="width: 80%; text-align: center;">
            <thead style="background-color: #f2f2f2;">
                <tr>
                    <th>STT</th>
                    <th>Sinh Viên</th>
                    <th>Bài Tập</th>
                    <th>Ngày Nộp</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${SUBMISSION_LIST}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><b>${item.studentFullName}</b></td>
                        <td>${item.assignmentTitle}</td>
                        <td>${item.submittedAt}</td>
                        <td>
                            <a href="<c:url value='/teacher/download-submission?id=${item.id}'/>">
                                <button style="background-color: #4CAF50; color: white; cursor: pointer;">Tải Bài Làm</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>