package com.gd.lms.service;

import java.util.List;
import java.util.Map;

import com.gd.lms.vo.Totalgrade;

public interface ITotalGradeService {
	
	//학생 성적 리스트 생성 서비스
	List<Map<String, Object>> getTotalgradePro(int lectureNo);
	
	//성적 처리 서비스
	int calGrade(int lectureNo, int [] paper);
	
	//등수 서비스
	int gradeRank(int SignNo, Totalgrade gradeTotal);

}