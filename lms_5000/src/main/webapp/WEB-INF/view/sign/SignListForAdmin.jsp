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
            <h1 class="m-0 text-dark">강좌 목록(운영자용 페이지)</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">관리자 LMS 메인</a></li>
              <li class="breadcrumb-item active">개설 과목 리스트</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <!-- 메인 콘텐츠 -->
	<div class = "container">
		  <div class="card">
              <div class="card-header">
                <h1 class="card-title"><Strong>개설 강좌 리스트</Strong></h1><br>
              </div>
          <div class="card-body table-responsice p-0">
           	  <table class="table table-hover text-nowrap">
					<thead>
						<tr>
							<th>강좌번호</th>
							<th>과목이름</th>
							<th>교수 이름</th>
							<th>신청 가능 학년</th>
							<th>강의실</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="l" items="${lectureList}">
							<tr>
								<td>${l.lecture_no}</td>
								<td><a href="${pageContext.request.contextPath}/sign/SignListByLecture?lectureNo=${l.lecture_no}">${l.subject_name}</a></td>
								<td>${l.user_name}(${l.user_id})</td>
								<td>${l.subject_grade}학년</td>
								<td>${l.classroom_no}호 강의실</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="card-footer">
					  <nav aria-label="Contacts Page Navigation">
			            <ul class="pagination justify-content-center m-0">
			            	<li class="page-item "><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin&currentPage=1">첫페이지</a></li>
			            	<li class="page-item "><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin?currentPage=${currentPage - 1}">이전쪽</a></li>
								<c:forEach items="${pages}" var="p">
									<c:if test="${p eq currentPage}">

						              <li class="page-item active"><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin?currentPage=${p}">${p}</a></li>
						            </c:if>
						            <c:if test="${p ne currentPage}">
						              <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin?currentPage=${p}">${p}</a></li>
						            </c:if>
								</c:forEach>
							<li class="page-item "><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin?currentPage=${currentPage + 1}">다음쪽</a></li>
							<li class="page-item "><a class="page-link" href="${pageContext.request.contextPath}/sign/SignListForAdmin?currentPage=${realLastPage}">마지막페이지</a></li>
			            </ul> 
			          </nav>
	              </div>
              </div>
            </div><!-- /.card -->
		 </div>
		</div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    <!-- /.content -->
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