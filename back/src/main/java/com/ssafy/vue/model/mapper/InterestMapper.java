package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.InterestDto;

@Mapper
public interface InterestMapper {
	
	public List<InterestDto> list(Map<String,Object> map) throws SQLException;
	public int regist(Map<String,Object> map) throws SQLException;
	public int delete(Map<String,Object> map) throws SQLException;
}
