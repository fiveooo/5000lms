<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<title>LMS 5000</title>

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
            <h1 class="m-0 text-dark">학생 성적 확인 페이지</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item active">성적</li>                  
              <li class="breadcrumb-item active"><a href="${pageContext.request.contextPath}/dashBoard/lectureDashBoard?lectureNo=${lectureNo}">${grade.subject_name}</a></li>
              <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">메인페이지</a></li>
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
      	<table border="1">
	      	<thead>
	      		<tr>  		
					<th>분류</th>     
					<th>점수</th> 	
				</tr>
	      	</thead>
	      	<tbody>
	      		<tr>  		
					<th>학생 이름(studentName)</th>     
					<td>${grade.user_name}</td> 	
				</tr>
		      	<tr>	
					<th>시험점수(testScore)</th>
					<td>${grade.gradeTest}</td>
				</tr>
		      	<tr>
					<th>과제점수(paperScore)</th>      	
					<td>${grade.gradePaper}</td>
				</tr>
		      	<tr>	
					<th>출석점수(attendance)</th>      
					<td>${grade.gradeAtt}</td>
				</tr>
		      	<tr>
					<th>총 점수(grdeTotal)</th>      
					<td>${grade.gradeTotal}</td>
				</tr>
		      	<tr>
					<th>등수(rank)</th>      
					<td>${grade.gradeRank}</td>
				</tr>
		      	<tr>
					<th>최종 학점(totalGrade)</th>      
					<td>${grade.totalGrade}</td>

	      				
	    	  	</tr>
	      	</tbody>
      	</table>
      </div><!-- /.container-fluid -->
      <div>
      	<script>
		    var data =
		    {
		        labels: ["1월", "2월", "3월", "4월", "5월", "6월"],
		        datasets:
		            [{
		                label: "My First dataset",
		                fillColor: "rgba(150,200,250,0.5)",
		                strokeColor: "rgba(150,200,250,0.8)",
		                highlightFill: "rgba(150,200,250,0.75)",
		                highlightStroke: "rgba(150,200,250,1)",
		                data: [65, 59, 80, 81, 56, 55]
		            }]
		    };
		
		    var options = { animation: false };
		    var ctx = $('#myChart').get(0).getContext('2d');
		    var myBarChart = new Chart(ctx).Bar(data, options); 
		</script>
      </div>
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


<canvas id="myChart" width="300" height="200"></canvas>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/Chart.js/0.2.0/Chart.min.js"></script>
</body>
</html>

