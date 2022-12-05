package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.MemoDto;
import com.ssafy.vue.model.MemoParameterDto;

public interface MemoService {
	
	public boolean regist(MemoDto memoDto) throws Exception;
	public List<MemoDto> list(MemoParameterDto memoParameterDto) throws Exception;
	public PageNavigation makePageNavigation(MemoParameterDto memoParameterDto) throws Exception;
	
	public boolean edit(MemoDto memoDto) throws Exception;
	public boolean delete(int memono) throws Exception;
	
}
