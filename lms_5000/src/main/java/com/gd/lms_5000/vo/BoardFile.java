package com.gd.lms_5000.vo;

import lombok.Data;

@Data
public class BoardFile {
	private String fileName;
	private String fileOriginname;
	private String fileType;
	private String createDate;
	private int boardPostNo;
}