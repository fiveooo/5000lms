package com.gd.lms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gd.lms.vo.Message;

@Mapper
public interface MessageMapper {
	
	//메시지 리스트 출력 ( 전체 )
	List<Message> selectMessgeList(String sendId , String receiveId, int beginRow , int rowPerPage);

	//메시지 리스트 출력 ( 발신 )
	List<Message> selectSendMessageList(String sendId , int beginRow , int rowPerPage);
	
	//메시지 리스트 출력 ( 수신 )
	List<Message> selectReceiveMessageList(String receiveId, int beginRow , int rowPerPage);
	
	//메시지 상세보기
	public List<Message> selectMessageOne(int messageNo);
	
	//메시지 읽음표시 업데이트
	public int updateMessageState(Message message);
	public int updateMessageState(String receiveId, int messageNo);
	
	//메시지 보내기
	public int insertMessage(Message message);
	
	//메시지 삭제
	public int deleteMessage();
	
	//전체 메시지 count
	public int countMessage(String sendId , String receiveId);
	
	//보낸 메시지 count
	public int countSendMessage(String sendId);
	
	//받은 메시지 count
	public int countReceiveMessage(String receiveId);
}
