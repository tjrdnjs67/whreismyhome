package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.vue.model.InterestDto;

public interface InterestService {
	
	public List<InterestDto> list(Map<String,Object> map) throws Exception;
	public boolean regist(Map<String,Object> map) throws Exception;
	public boolean delete(Map<String,Object> map) throws Exception;
}
