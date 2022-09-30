<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대충 상세보기</title>
</head>
<body>
<div>
	<h3>강의 상세보기</h3>
	<table border="1" bordercolor="green">
		<tbody>
			<tr>
				<th>강의번호</th>
				<td>${lectureOne.lectureNo}번</td>
			</tr>
			<tr>
				<th>강의시간</th>
				<td>${lectureOne.lectureTime}</td>
			</tr>
			<tr>
				<th>시작시간</th>
				<td>${lectureOne.lectureStarttime}</td>
			</tr>
			<tr>
				<th>종료시간</th>
				<td>${lectureOne.lectureEndtime}</td>
			</tr>
			<tr>
				<th>요일</th>
				<td>${lectureOne.lectureDay}</td>
			</tr>
			<tr>
				<th>강좌번호</th>
				<td>${lectureOne.subjectNo}</td>
			</tr>
			<tr>
				<th>강의실</th>
				<td>${lectureOne.classroomNo}</td>
			</tr>
			<tr>
				<th>담당교수</th>
				<td>${lectureOne.userId}</td>
			</tr>
			<tr>
				<th>학기</th>
				<td>${lectureOne.semesterNo}</td>
			</tr>
			
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/lmsLecture/updateLecture/form?lectureNo=${lectureNo}">수정</a>
	<a href="${pageContext.request.contextPath}/lmsLecture/deleteLecture?lectureNo=${lectureNo}">삭제</a>
</div>

</body>
</html>