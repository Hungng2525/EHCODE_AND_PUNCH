<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin - Quản lý Lớp Học</title>
</head>
<body>
	<div align="center">
		<h2>Xin chào Giáo viên: ${USERMODEL.fullName}</h2>
		<a href="<c:url value='/logout'/>">Đăng xuất</a>
		<hr>

		<h3>Bảng Điều Khiển Quản lý Sinh viên</h3>

		<div style="margin-bottom: 15px;">
			<a href="<c:url value='/teacher/create-assignment'/>">
				<button type="button"
					style="padding: 10px; background-color: #008CBA; color: white; border: none; cursor: pointer;">
					+ Giao Bài Tập Mới</button>
			</a>
			<a href="<c:url value='/teacher/submissions'/>">
                <button type="button" style="padding: 10px; background-color: #ff9800; color: white; border: none; cursor: pointer;">
                    Xem Bài Nộp
                </button>
            </a>
		</div>

		<table border="1" cellpadding="10"
			style="width: 80%; text-align: center;">
			<thead style="background-color: #f2f2f2;">
				<tr>
					<th>ID</th>
					<th>Tên đăng nhập</th>
					<th>Họ và Tên</th>
					<th>Email</th>
					<th>Số điện thoại</th>
					<th>Vai trò</th>
					<th>Hành động (Sửa/Xóa)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${USER_LIST}">
					<tr>
						<td>${item.id}</td>
						<td>${item.userName}</td>
						<td><a href="<c:url value='/user/detail?id=${item.id}'/>"
							style="text-decoration: none; color: blue;"> ${item.fullName}
						</a></td>
						<td>${item.email}</td>
						<td>${item.phone}</td>
						<!--  		<td>${item.roleCode}</td> -->

						<td><c:if test="${item.roleCode == 'TEACHER'}">
								<b style="color: red;">Giáo Viên</b>
							</c:if> <c:if test="${item.roleCode == 'STUDENT'}">
								<span style="color: blue;">Sinh Viên</span>
							</c:if></td>

						<td><a href="<c:url value='/teacher/update?id=${item.id}'/>">Sửa</a>
							| <c:if test="${item.id != USERMODEL.id}">
								<a href="<c:url value='/teacher/delete-user?id=${item.id}'/>"
									onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này không?');"
									style="color: red;">Xóa</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>