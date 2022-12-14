package com.gd.lms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gd.lms.vo.Major;
import com.gd.lms.vo.Subject;
import com.gd.lms.vo.User;

@Mapper
public interface SubjectMapper {

	// 강좌 리스트
	public List<Subject> selectSubjectList();
	
	// 강좌 보기
	public Map<String, Object> selectSubjectOne(int subjectNo);
	
	// 강좌 추가
	public int addSubject(Subject subject);
	
	// 강좌 수정
	public int updateSubject(Subject subject);
	
	// 강좌 삭제
	public int deleteSubject(int subjectNo);
	
	// 학과 리스트 받아오기
	public List<Major> getMajorList();
	
	// 교수 리스트 받아오기
	public List<User> getUserList();
}
