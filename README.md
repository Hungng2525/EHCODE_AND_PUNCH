# EHCODE_AND_PUNCH
**CLASS MANAGEMENT**
Code in my project: **Java, MySql.** 
**- Chức năng:**
* Quản lí thông tin:
  + Giáo viên có thể thêm, sửa, xóa các thông tin của sinh viên và chính mình.
  + Sinh viên có thể thêm, sửa thông tin của chính mình trừ tên đăng nhập và họ tên.
  + Ai cũng có thể xem danh sách các người dùng và xem thông tin chi tiết của người khác trừ tên đăng nhập và mật khẩu. 
* Chức năng giao bài, trả bài
  + Giáo viên upload file bài tập lên. Các sinh viên có thể xem danh sách bài tập và tải file bài tập về.
  + Sinh viên có thể upload bài làm tương ứng với bài tập được giao. Chỉ giáo viên mới thấy.
**Database Shema**
users(id, username, password, full_name, phone, email, status, created_at, updated_at)
user_roles(user_id, role_id)
roles(id, code, name)
teacher_profiles(user_id, teacher_code, department)
student_profiles(user_id, student_code)
assignments(id, title, description, file_path, created_by_id, created_at)
submissions(id, assignment_id, student_id, file_path, submitted_at, grade, teacher_comment)
