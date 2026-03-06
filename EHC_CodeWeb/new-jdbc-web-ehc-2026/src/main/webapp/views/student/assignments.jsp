<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Danh Sách Bài Tập</title></head>
<body>
    <div align="center">
        <h2>Danh Sách Bài Tập Cần Làm</h2>
        <a href="<c:url value='/trang-chu'/>">Quay lại trang chủ</a>
        <hr>
        <table border="1" cellpadding="10" style="width: 80%; text-align: center;">
            <thead style="background-color: #f2f2f2;">
                <tr>
                    <th>STT</th>
                    <th>Tiêu đề</th>
                    <th>Mô tả</th>
                    <th>Ngày giao</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${ASSIGNMENT_LIST}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><b>${item.title}</b></td>
                        <td>${item.description}</td>
                        <td>${item.createdAt}</td>
                        <td>
                            <a href="<c:url value='/download-assignment?id=${item.id}'/>">
                                <button style="background-color: #4CAF50; color: white; cursor: pointer;">Tải Đề Bài</button>
                            </a>
                            <a href="<c:url value='/student/submit?assignmentId=${item.id}'/>">
    							<button style="background-color: #008CBA; color: white; cursor: pointer; margin-left: 5px;">Nộp Bài</button>
							</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>