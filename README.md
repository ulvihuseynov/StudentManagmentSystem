# Student Management System Backend

Spring Boot ilə yazılmış real-world **Student Management System** backend layihəsi.

Layihədə student, teacher, course, group, enrollment, grade, attendance və JWT security modulları var. Sistem role-based və ownership-based security məntiqi ilə işləyir.

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Security
- JWT / JJWT
- Spring Data JPA / Hibernate
- MySQL
- MapStruct
- Lombok
- Validation

## Main Features

- Auth: register, login, JWT token, current user endpoint
- Student module: CRUD, status update, soft deactivate, self profile endpoint
- Teacher module: CRUD, status update, soft deactivate, self profile endpoint
- Course module: CRUD, status update, archive/delete flow
- Group module: CRUD, course + teacher relation, status update
- Enrollment module: student enrollment to group, capacity checks, duplicate enrollment checks
- Grade module: GradeType-based grading system, teacher ownership check, student self-access endpoint
- Attendance module: attendance by enrollment, duplicate attendance per lesson date check, teacher ownership check, student self-access endpoint
- Security: role-based access control, teacher ownership security, student self-access security, forbidden exception handling

## Business Model

### Main Relations

```text
Course 1 — N Group
Teacher 1 — N Group
Student 1 — N Enrollment
Group 1 — N Enrollment
Enrollment 1 — N Grade
Enrollment 1 — N Attendance
User 1 — 1 Student
User 1 — 1 Teacher
```

### Important Rule

Grade və Attendance birbaşa `Student`-ə yox, **Enrollment**-a bağlanır.

```text
Student + Group = Enrollment
Enrollment → Grades
Enrollment → Attendance
```

Bu model real sistemə daha uyğundur, çünki tələbə fərqli group-larda fərqli nəticələrə sahib ola bilər.

## Grade System

Grade sistemi `GradeType` üzərində qurulub.

| Grade Type | Max Score |
|---|---:|
| HOMEWORK | 10 |
| QUIZ | 10 |
| MIDTERM | 20 |
| FINAL_EXAM | 30 |
| PROJECT | 25 |
| PARTICIPATION | 5 |
| **TOTAL** | **100** |

Teacher request-də `maxScore` göndərmir. Sistem `GradeType`-a görə `maxScore`-u avtomatik set edir.

Example:

```json
{
  "title": "Quiz 1",
  "score": 8,
  "gradeType": "QUIZ",
  "gradeDate": "2026-06-14",
  "description": "First quiz",
  "enrollmentId": 1
}
```

Sistem özü bilir:

```text
QUIZ maxScore = 10
```

## Security Model

### Public Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Public student user register |
| POST | `/api/auth/login` | Login and get JWT token |

### Role Access Summary

| Role | Access |
|---|---|
| ADMIN | Full system management |
| TEACHER | Own group grade/attendance management |
| STUDENT | Own profile, grades, and attendance only |

### Ownership Security

Teacher sadəcə `ROLE_TEACHER` olduğu üçün hər şeyi idarə edə bilmir.

Service layer yoxlayır:

```text
currentTeacher.teacherId == enrollment.group.teacher.teacherId
```

Əgər teacher həmin group-un müəllimi deyilsə, sistem `403 Forbidden` qaytarır.

Student isə ID göndərmir. `/me` endpoint-lərində sistem token-dən current student-i tapır.

```text
SecurityContext → username → Student → own Grades / Attendance
```

## Test Users

`DataInitializer` test/dev mühitində bu user-ləri yaradır:

| Role | Username | Password |
|---|---|---|
| ADMIN | `admin` | `admin123` |
| TEACHER | `teacher` | `teacher123` |

Student üçün iki yol var:

1. Public register: `POST /api/auth/register`
2. Admin tərəfindən real student profile yaratmaq: `POST /api/students`

Student self-access endpoint-lərinin işləməsi üçün student user account-u `Student` profile ilə bağlı olmalıdır. Ona görə real test üçün admin token ilə `POST /api/students` istifadə etmək daha düzgündür.

Example student login data:

```text
username: student1
password: student123
```

## Environment Variables

`application.properties` environment variable-lardan istifadə edir.

```properties
DB_URL=jdbc:mysql://localhost:3306/student_management
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=base64_encoded_secret_key
JWT_EXPIRATION_MS=86400000
```

### JWT Secret

`JwtUtils` Base64 secret istifadə edirsə, `JWT_SECRET` Base64 formatında olmalıdır.

Example üçün 256-bit secret:

```text
bXktc3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRva2VuLTEyMzQ1Njc4OTA=
```

Production-da bu dəyəri dəyişmək lazımdır.

## How to Run

1. MySQL database yarat:

```sql
CREATE DATABASE student_management;
```

2. Environment variables əlavə et.

Linux/macOS:

```bash
export DB_URL=jdbc:mysql://localhost:3306/student_management
export DB_USERNAME=root
export DB_PASSWORD=your_password
export JWT_SECRET=bXktc3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRva2VuLTEyMzQ1Njc4OTA=
export JWT_EXPIRATION_MS=86400000
```

Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/student_management"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="bXktc3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRva2VuLTEyMzQ1Njc4OTA="
$env:JWT_EXPIRATION_MS="86400000"
```

3. Project-i run et:

```bash
mvn spring-boot:run
```

Default base URL:

```text
http://localhost:8080
```

## Endpoint List

### Auth

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/auth/register` | PUBLIC |
| POST | `/api/auth/login` | PUBLIC |
| GET | `/api/auth/me` | AUTHENTICATED |

### Students

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/students` | ADMIN |
| GET | `/api/students` | ADMIN |
| GET | `/api/students/{id}` | ADMIN |
| GET | `/api/students/me` | ADMIN, STUDENT |
| PUT | `/api/students/{id}` | ADMIN |
| PATCH | `/api/students/{id}/status` | ADMIN |
| DELETE | `/api/students/{id}` | ADMIN |

### Teachers

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/teachers` | ADMIN |
| GET | `/api/teachers` | ADMIN |
| GET | `/api/teachers/{id}` | ADMIN |
| GET | `/api/teachers/me` | ADMIN, TEACHER |
| PUT | `/api/teachers/{id}` | ADMIN |
| PATCH | `/api/teachers/{id}/status` | ADMIN |
| DELETE | `/api/teachers/{id}` | ADMIN |

### Courses

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/courses` | ADMIN |
| GET | `/api/courses` | ADMIN, TEACHER, STUDENT |
| GET | `/api/courses/{id}` | ADMIN, TEACHER, STUDENT |
| PUT | `/api/courses/{id}` | ADMIN |
| PATCH | `/api/courses/{id}/status` | ADMIN |
| DELETE | `/api/courses/{id}` | ADMIN |

### Groups

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/groups` | ADMIN |
| GET | `/api/groups` | ADMIN, TEACHER, STUDENT |
| GET | `/api/groups/{id}` | ADMIN, TEACHER, STUDENT |
| PUT | `/api/groups/{id}` | ADMIN |
| PATCH | `/api/groups/{id}/status` | ADMIN |

### Enrollments

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/enrollments` | ADMIN |
| GET | `/api/enrollments` | ADMIN |
| GET | `/api/enrollments/student/{studentId}` | ADMIN |
| GET | `/api/enrollments/group/{groupId}` | ADMIN |
| PATCH | `/api/enrollments/{id}/status` | ADMIN |
| DELETE | `/api/enrollments/{id}` | ADMIN |

### Grades

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/grades` | ADMIN, TEACHER |
| GET | `/api/grades/me` | STUDENT |
| GET | `/api/grades/student/{studentId}` | ADMIN, TEACHER |
| GET | `/api/grades/enrollment/{enrollmentId}` | ADMIN, TEACHER |
| GET | `/api/grades/enrollment/{enrollmentId}/summary` | ADMIN, TEACHER |
| PUT | `/api/grades/{id}` | ADMIN, TEACHER |
| DELETE | `/api/grades/{id}` | ADMIN, TEACHER |

### Attendance

| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/attendance` | ADMIN, TEACHER |
| GET | `/api/attendance/me` | STUDENT |
| GET | `/api/attendance/student/{studentId}` | ADMIN, TEACHER |
| GET | `/api/attendance/enrollment/{enrollmentId}` | ADMIN, TEACHER |
| PUT | `/api/attendance/{attendanceId}` | ADMIN, TEACHER |
| DELETE | `/api/attendance/{attendanceId}` | ADMIN, TEACHER |

## Recommended Test Flow

1. Login as admin.
2. Create teacher.
3. Create student.
4. Create course.
5. Create group with courseId and teacherId.
6. Create enrollment with studentId and groupId.
7. Login as teacher.
8. Teacher creates grade for own group enrollment.
9. Teacher creates attendance for own group enrollment.
10. Login as student.
11. Student calls `/api/grades/me`.
12. Student calls `/api/attendance/me`.

## Example Requests

### Login

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Create Student

```json
{
  "firstname": "Ali",
  "lastname": "Mammadov",
  "email": "student1@gmail.com",
  "phone": "0501234567",
  "dateOfBirth": "2002-05-10",
  "username": "student1",
  "password": "student123"
}
```

### Create Teacher

```json
{
  "firstname": "Elvin",
  "lastname": "Aliyev",
  "email": "teacher1@gmail.com",
  "phone": "0507654321",
  "specialization": "Java Backend",
  "username": "teacher1",
  "password": "teacher123"
}
```

### Create Course

```json
{
  "name": "Java Backend Development",
  "description": "Spring Boot and backend development course",
  "durationInMonths": 6,
  "price": 500,
  "level": "BEGINNER"
}
```

### Create Group

```json
{
  "name": "Java Backend Group 1",
  "courseId": 1,
  "teacherId": 1,
  "capacity": 15,
  "startDate": "2026-07-01",
  "endDate": "2026-12-31"
}
```

### Create Enrollment

```json
{
  "studentId": 1,
  "groupId": 1
}
```

### Create Grade

```json
{
  "title": "Quiz 1",
  "score": 8,
  "gradeType": "QUIZ",
  "gradeDate": "2026-07-15",
  "description": "First quiz",
  "enrollmentId": 1
}
```

### Create Attendance

```json
{
  "lessonDate": "2026-07-15",
  "status": "PRESENT",
  "note": "On time",
  "enrollmentId": 1
}
```

## Postman

Postman collection faylı:

```text
StudentManagementSystem.postman_collection.json
```

Collection variables:

| Variable | Value |
|---|---|
| `baseUrl` | `http://localhost:8080` |
| `adminToken` | login response-dan avtomatik yazılır |
| `teacherToken` | login response-dan avtomatik yazılır |
| `studentToken` | login response-dan avtomatik yazılır |

## Code Formatting

Final commit-dən əvvəl IntelliJ-də formatlama et:

```text
Ctrl + Alt + L
```

Sonra unused imports sil:

```text
Ctrl + Alt + O
```

Maven build yoxla:

```bash
mvn clean test
```

## Final Status

```text
Student Management System Backend: ACCEPTED
```

Bu layihə Java Backend Developer portfolio üçün real-world backend project kimi istifadə oluna bilər.
