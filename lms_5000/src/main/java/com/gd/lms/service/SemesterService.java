package com.gd.lms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.mapper.SemesterMapper;
import com.gd.lms.vo.Semester;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SemesterService implements ISemeterService{
	@Autowired SemesterMapper semesterMapper;
	
	@Override
	public List<Semester> getSemesterList() {
		
		return semesterMapper.selectSemesterList();
	}

	@Override
	public int addSemseter(Semester semester) {
		
		int row = semesterMapper.addSemester(semester);
		
		log.debug(TeamColor.SSH + "추가 결과 : " + row);
		
		return row;
	}

	@Override
	public int updateSemester(Semester semester) {
		
		int row = semesterMapper.updateSemester(semester);
		
		return row;
	}

	@Override
	public int deleteSemester(int semesterNo) {
		
		int row = semesterMapper.deleteSemester(semesterNo);
		
		return row;
	}

	@Override
	public Map<String, Object> getSemesterOne(int semesterNo) {
		
		Map<String, Object> semesterOne = semesterMapper.selectSemesterOne(semesterNo);
		
		log.debug(TeamColor.SSH + "상세보기 값 넘기기 : " + semesterOne);
		
		return semesterOne;
	}

}
