package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;

public interface QNAService {
	public boolean writeQNA(BoardDto boardDto) throws Exception;
	public List<BoardDto> listQNA(BoardParameterDto boardParameterDto) throws Exception;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	
	public BoardDto getQNA(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyQNA(BoardDto boardDto) throws Exception;
	public boolean deleteQNA(int articleno) throws Exception;
}
