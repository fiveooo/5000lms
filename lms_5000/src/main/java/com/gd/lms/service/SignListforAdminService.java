package com.gd.lms.service;

import java.util.Arrays;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.mapper.SignListForAdminMapper;
import com.gd.lms.mapper.TotalGradeMapper;
import com.gd.lms.vo.Sign;
import com.gd.lms.vo.SignCancel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignListforAdminService implements ISignListforAdminService{
	@Autowired SignListForAdminMapper signListForAdminMapper;
	@Autowired TotalGradeMapper totalGradeMapper;
	
	
	// 강좌 리스트
	@Override
	public List<Map<String, Object>> StudentSignList(int beginRow, int rowPerPage) {
		List<Map<String, Object>> StudentSignList = signListForAdminMapper.selectLectureList(beginRow, rowPerPage);
		// 디버깅
		log.debug(TeamColor.YHW + "-- 강좌리스트 -service--"+ StudentSignList );
		return StudentSignList;
	}
	
	// 강좌별 수강신청 리스트
	@Override
	public List<Map<String, Object>> getStudentListByLecture(Sign sign) {
		List<Map<String, Object>> getStudentListByLecture = signListForAdminMapper.selectStudentListByLecture(sign);
		// 디버깅
		log.debug(TeamColor.YHW + "-- 수강신청 리스트 -service--"+ getStudentListByLecture );
		return getStudentListByLecture;
	}
	
	// 과목 정보
	@Override
	public List<Map<String, Object>> getSubjectInfo() {
		List<Map<String, Object>> getSubjectInfo = signListForAdminMapper.selectSubjectInfo();
		// 디버깅
		log.debug(TeamColor.YHW + "-- 과목 정보 -service--"+ getSubjectInfo );
	return getSubjectInfo;
	}
	// 학생 수강상태 변경
	@Override
	public int modifySignState(Sign sign, SignCancel signCancel) {
		
		//수정 쿼리 실행
		int modifySignState = signListForAdminMapper.updateSignState(sign);
		
		//수강 신청 완료시
		if(sign.getSignState().equals("1")) {
			
			//수강신청 완료시 출석 테이블에 자동 입력
			int row = 0;
			
			//수강 신청 완료시 학생 성적 테이블 자동 입력
			row += totalGradeMapper.insertTotalgrade(sign.getSignNo());
			log.debug(TeamColor.KHJ + "학생 자동 입력 현황 : "+ row );
			
			
			int [] week = signListForAdminMapper.selectLectureWeek(sign.getLectureNo());
			log.debug("week 결과값 : " + Arrays.toString(week));
			
			for(int a : week) {
				row = signListForAdminMapper.insertAttendance(a, sign.getSignNo());
				log.debug(a+" 주차 출석 insert 결과값 : " + row);
			}
			

			
		}
		
		// 취소 주체에 따른 cancle 핸들링
		//운영자가 수강 취소할 경우 signcancel테이블에 추가하기
		if(sign.getSignState().equals("2")) {
			//삽입 쿼리 실행
			modifySignState = signListForAdminMapper.insertSignCancel(signCancel);
			// 디버깅
			log.debug(TeamColor.YHW + "-- 운영자가 수강 취소 했다면 -service--"+ modifySignState );
			
		//운영자가 수강취소가 아닐 경우 cancel 테이블에서 행 제거
		} else {
			//삭제 커리 실행
			modifySignState = signListForAdminMapper.deleteSignCancel(signCancel);
			
			// 디버깅
			log.debug(TeamColor.YHW + "-- 운영자가 수강 취소 말고 다른걸 했다면 -service--"+ modifySignState );
						
		}
		// 디버깅
		log.debug(TeamColor.YHW + "-- mapper에서 넘어온 수강상태 변경 -service--"+ modifySignState );
		return modifySignState;
	}

	// 강좌 총 갯수
	@Override
	public int getTotal() {
		int getTotal = signListForAdminMapper.getTotal();
		return getTotal;
	}

	

}
