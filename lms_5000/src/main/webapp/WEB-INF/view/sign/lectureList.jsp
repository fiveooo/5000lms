<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<title>AdminLTE 3 | Starter</title>

<!-- 페이지 삽입 - 필수적인 레퍼런스(css, font) -->
<%@include file="../import/reference.jsp" %>

</head>
<body class="hold-transition sidebar-mini">

<!-- 전체 페이지 래퍼 -->
<div class="wrapper">


<!-- 페이지 삽입 - nav bar -->
<%@include file="../import/nav.jsp" %>

<!-- 페이지 삽입 - side bar -->
<%@include file="../import/sidebar.jsp" %>



  <!-- Content Wrapper. Contains page content -->
  <!-- 메인 컨텐츠 래퍼 -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <!-- 메인페이지 헤더(제목 등) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">Starter Page</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Starter Page</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <!-- 메인 콘텐츠 -->
    <div class="content">
      <div class="container-fluid">
      
      <!-- 여기를 밀어버리고 컨텐츠로 채우시면 됩니다 -->
      <!-- 카드형태를 옮겨 쓰셔도 무상관 -->
      <c:choose>
      	<c:when test="${loginUser.userLevel == 2 || loginUser.userLevel == 3 }">
      		<h3>나의 강의 목록</h3>
		<table border="1">
			<thead>
				<tr>
					<th>강좌이름</th>
					<th>대상학년</th>
					<th>수업요일</th>
					<th>강좌 개강일</th>
					<th>강좌 종료일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="lc" items="${lectureList}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/dashBoard/lectureDashBoard?userId=${loginUser.userId}&lectureNo=${lc.lectureNo}">${lc.subjectName}</a></td>
					<td>${lc.subjectGrade}</td>
					<td>${lc.lectureDay}</td>
					<%-- <td><a href="${pageContext.request.contextPath}
					/board/post/one?boardPostNo=${b.boardPostNo}&boardNo=${b.boardNo}&boardName=${b.boardName}">
					${b.boardPostName}</a></td> --%>
					<td>${lc.semesterStartDate}</td>
					<td>${lc.semesterEndDate}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
      	</c:when>
      </c:choose>
      



      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Control Sidebar -->
  <!-- 좌측 사이드 바 -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
    <div class="p-3">
      <h5>Title</h5>
      <p>Sidebar content</p>
    </div>
  </aside>
  <!-- /.control-sidebar -->
  
  
<!-- 페이지 삽입 - footer -->
<%@include file="../import/footer.jsp" %>

</div>
<!-- ./wrapper -->

<!-- 페이지 삽입 - 필수적인 script -->
<%@include file="../import/script.jsp" %>

</body>
</html>

