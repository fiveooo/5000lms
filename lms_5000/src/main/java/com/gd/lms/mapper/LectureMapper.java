package com.gd.lms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureMapper {
	// 수강신청을 하기위한 개설 강좌 리스트 보기
	List<Map<String, Object>> selectLectureListForSign();
}