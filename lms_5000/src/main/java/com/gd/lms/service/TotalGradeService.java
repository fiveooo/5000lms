package com.gd.lms.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.mapper.TotalGradeMapper;
import com.gd.lms.vo.Totalgrade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TotalGradeService implements ITotalGradeService{
	@Autowired TotalGradeMapper totalGradeMapper;
	
	//학생 성적 리스트 생성 서비스
	@Override
	public List<Map<String, Object>> getTotalgradePro(int lectureNo) {
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		
		//리턴값 
		List<Map<String, Object>> list = totalGradeMapper.selectTotalgradePro(lectureNo);
				
		//리턴
		return list;
	}
	
	//학생 성적

	//성적 처리 서비스
	@Override
	public int calGrade(int lectureNo, int [] paper) {
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		log.debug(TeamColor.KHJ + "파라미터 확인 / paper : " + Arrays.toString(paper));
		
		//리턴값
		int row = 0;
				
		//학생 수강 정보 추출
		int [] signNos = totalGradeMapper.selectSignNo(lectureNo);
		
		//출력값 확인 디버깅
		log.debug(TeamColor.KHJ + "출력값 확인 / signNos : " + Arrays.toString(signNos));
		
		//시험 총 문제수 추출
		int questionCnt = totalGradeMapper.selectQuestionCnt(lectureNo);
		
		//출력값 확인 디버깅
		log.debug(TeamColor.KHJ + "출력값 확인 / questionCnt : " + questionCnt);
				
		
		//for문용 배열 생성
		int [] arr = new int[signNos.length];
		for(int i = 0; i<arr.length;i++) {
			arr[i] = i;
		}
		//출력값 확인 디버깅
		log.debug(TeamColor.KHJ + "출력값 확인 / arr : " + Arrays.toString(arr));
				
		
		//업데이트 맨
		for(int a : arr) {
			Totalgrade temp = new Totalgrade();
			//수강 정보 세팅
			temp.setSignNo(signNos[a]);
			
			//시험 점수 세팅
			temp.setGradeTest(totalGradeMapper.selectTestGrade(questionCnt, lectureNo, signNos[a]));
		
			
			//과제 점수 세팅
			temp.setGradePaper(paper[a]);
			
			//출석 점수 세팅
			temp.setGradeAtt(totalGradeMapper.selectAttGrade(signNos[a]));
		
			//총 점수 세팅
			temp.setGradeTotal(temp.getGradePaper() + temp.getGradeTest() + temp.getGradeAtt());
			
			//출력값 확인 디버깅
			log.debug(TeamColor.KHJ + "출력값 확인 / temp : " + temp);
			
			//업데이트
			row += totalGradeMapper.updateGrade(temp);
			
		}
		//리턴
		return row;
	}

	// 가상테이블에 랭크 및 학점 출력
	@Override
	public int getRank(int lectureNo) {
		List<Map<String, Object>> table = totalGradeMapper.selectRank(lectureNo);
		log.debug(TeamColor.YHW + "가상테이블 정보 확인 : " + table);
		
		// 리턴값 셋팅
		int row = 0;
		
		// 랭크와 학점을 담을 map
		for(Map<String, Object> map : table) {
			Totalgrade update = new Totalgrade();
			update.setSignNo((Integer)map.get("sign_no"));
			// 등수 셋팅
			update.setGradeRank(Integer.parseInt(String.valueOf(map.get("RANK"))));
			log.debug(TeamColor.YHW + "등수 정보 확인 : " + update.getGradeRank());
			// 학점 셋팅
			update.setTotalGrade(Double.parseDouble(String.valueOf(map.get("POINT"))));
			log.debug(TeamColor.YHW + "학점 정보 확인 : " + update.getTotalGrade());
			
			//업데이트
			row += totalGradeMapper.updateRank(update);
		}
		return row;
	}

	// 학생 성적 확인
	@Override
	public Map<String, Object>  getTotalgradeForStu(String userId, int lectureNo) {
		// 파라미터 확인
		log.debug(TeamColor.KHJ + "파라미터 확인 / userId : " + userId);
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		
		
		//쿼리 실행
		Map<String, Object>  grade = totalGradeMapper.selectTotalgradeForStu(userId, lectureNo);
		
		return grade;
	}
}
