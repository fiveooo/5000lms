<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Insert title here</title>
<!-- 페이지 삽입 - 필수적인 레퍼런스(css, font) -->
<%@include file="../view/import/reference.jsp" %>
</head>
<body class="hold-transition sidebar-mini">

<!-- 전체 페이지 래퍼 -->
<div class="wrapper">


<!-- 페이지 삽입 - nav bar -->
<%@include file="../view/import/nav.jsp" %>

<!-- 페이지 삽입 - side bar -->
<%@include file="../view/import/sidebar.jsp" %>



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
		<h3>메인페이지</h3>
		<c:choose>
			<c:when test="${loginUser != null}">
				<p>${loginUser.userName}님 환영합니다.</p>
				<c:if test="${loginUser.userLevel eq 4}">
						<!-- loginUser 레벨이 4일경우 승인 페이지로 갈수있는 링크 출력-->
				<a href="${pageContext.request.contextPath}/user/userList">승인 페이지</a>
				</c:if>
				<br>
				<a href="${pageContext.request.contextPath}/lmsNotice/LmsNoticeList">공지 리스트</a><br>
				<a href="${pageContext.request.contextPath}/sign/openLectureList">강의 리스트</a><br>
				<a href="${pageContext.request.contextPath}/board/list?lectureNo=1">게시판</a><br>
				<a href="${pageContext.request.contextPath}/index/mypage">마이페이지</a><br>
				<button  onclick="window.open('${pageContext.request.contextPath}/user/messageList'
				, '새창', 'width=300px, height=500px' , 'location=no' , 'toolbar=yes'); return false">메세지</button>
				
				<p><a href="${pageContext.request.contextPath}/index/logout">로그아웃</a></p>
			</c:when>
			<c:otherwise>
			
			
				<a href="${pageContext.request.contextPath}/index/login">로그인하러가기</a>
			</c:otherwise>
		</c:choose>
		
		
	</div>
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
<%@include file="../view/import/footer.jsp" %>


<!-- ./wrapper -->

<!-- 페이지 삽입 - 필수적인 script -->
<%@include file="../view/import/script.jsp" %>

</body>
</html>