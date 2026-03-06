<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ Sinh viên</title>
</head>
<body>
	<div align="center">
		<h2>Xin chào Sinh viên: ${USERMODEL.fullName}</h2>
		<a href="<c:url value='/student/update-profile'/>">Cập nhật thông tin cá nhân</a>
		<a href="<c:url value='/logout'/>">Đăng xuất</a>
		<a href="<c:url value='/student/assignments'/>">Xem Bài Tập Được Giao</a> |
		<hr>

		<h3>Danh Sách Thành Viên Lớp Học</h3>
		<table border="1" cellpadding="10">
			<thead>
				<tr>
					<th>STT</th>
					<th>Họ và Tên</th>
					<th>Email</th>
					<th>Số điện thoại</th>
					<th>Trạng thái</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${USER_LIST}" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>
							<a href="<c:url value='/user/detail?id=${item.id}'/>"
							style="text-decoration: none; color: blue;"> ${item.fullName}
							</a>
						</td>
						<td>${item.email}</td>
						<td>${item.phone}</td>
						<td>${item.status}</td>
					<!--	<td>${item.roleCode}</td> -->
 					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>