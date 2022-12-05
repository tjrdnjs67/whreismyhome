package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.MemoDto;
import com.ssafy.vue.model.MemoParameterDto;

@Mapper
public interface MemoMapper {
	public int regist(MemoDto memoDto) throws SQLException;
	public List<MemoDto> list(MemoParameterDto memoParameterDto) throws SQLException;
	public int getTotalCount(MemoParameterDto memoParameterDto) throws SQLException;
	public int edit(MemoDto memoDto) throws SQLException;
	public int delete(int memono) throws SQLException;
}
