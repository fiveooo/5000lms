package com.gd.lms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gd.lms.vo.Totalgrade;

@Mapper
public interface TotalGradeMapper {
	
	//교수 학생 성적 리스트 출력
	List<Map<String, Object>> selectTotalgradePro(int lectureNo);
	
	//학생 수강 번호 출력쿼리
	int [] selectSignNo(int lectureNo);
	
	//해당 강좌 총 시험문제 출력 쿼리
	int selectQuestionCnt(int lecutreNo);
	
	//특정 학생의 시험점수 확인 쿼리
	int selectTestGrade(int questionCnt, int lectureNo, int signNo);
	
	//특정 학생의 출석 점수 확인 쿼리
	int selectAttGrade(int signNo);

	
	//학생 성적 기본 점수 업데이트 쿼리
	int updateGrade(Totalgrade totalgrade);
	
	//성적 순위 출력 쿼리
	int gradeRank(int signNo, Totalgrade totalgrade);
	
}