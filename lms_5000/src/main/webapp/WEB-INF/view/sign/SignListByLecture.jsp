<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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


  <c:choose>
			<c:when test="${loginUser != null}">
				<p>${loginUser.userName}님 환영합니다.</p>
				<c:if test="${loginUser.userLevel ne 3}">
					<a href="${pageContext.request.contextPath}/index">권한제한</a>
				</c:if>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/index/login">로그인하러가기</a>
			</c:otherwise>
		</c:choose>
  <!-- Content Wrapper. Contains page content -->
  <!-- 메인 컨텐츠 래퍼 -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <!-- 메인페이지 헤더(제목 등) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">수강신청 인원 리스트(운영자용 페이지)</h1>
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
	<div class = "container">
		  <div class="card card-primary card-outline">
              <div class="card-body">
				<c:forEach var="g" items="${getStudentList}">
	                <h1 class="card-title"><br><Strong>${g.subject_name} 수강신청 학생 </Strong></h1><hr>
	                <input type="hidden" name="lecture_no" id="lecture_no" value="${lecture_no}">
	                <table border="1">
						<thead>
							<tr>
								<th>전공(major_name) </th>
								<th>학년(lectureNo) - 수강 가능 학년(${g.subject_grade}학년)  </th>
								<th>학생ID(userId)</th>
								<th>학생이름(userName)</th>
								<th>상태 변경</th>
							</tr>
						</thead>
						<tbody>
								<tr>
									<td>${g.major_name}</td>
									<td>${g.grade_no}</td>
									<td>${g.user_id}</td>
									<td>${g.st_name}</td>
									<td>
										<form action="${pageContext.request.contextPath}/sign/SignListByLecture?lectureNo=${g.lecture_no}&userId=${g.user_id}" method="post">
											<select name="signState" >
												<c:choose>
													<c:when test="${g.sign_state eq '0'}">
														<option value="0" selected>승인 대기</option>
														<option value="1">신청 완료</option>
														<option value="2">수강 불가</option>
													</c:when>
													<c:when test="${g.sign_state eq '1'}">
														<option value="0" >승인 대기</option>
														<option value="1" selected>신청 완료</option>
														<option value="2" >수강 불가</option>
													</c:when>
													<c:when test="${g.sign_state eq '2'}">
														<option value="0" >승인 대기</option>
														<option value="1" >신청</option>
														<option value="2" selected>수강 불가</option>
													</c:when>
												</c:choose>											
											</select>
											<button type="submit">상태 변경</button>
										</form>
									 </td>
								</tr>
						</tbody>
					</table>
				</c:forEach>
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
              </div>
            </div><!-- /.card -->
	 </div>
	</div>
        <!-- /.row -->
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
<!-- ./wrapper -->

<!-- 페이지 삽입 - 필수적인 script -->
<%@include file="../import/script.jsp" %>

</body>
</html>