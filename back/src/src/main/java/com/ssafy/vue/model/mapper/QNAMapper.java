package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;

@Mapper
public interface QNAMapper {
	public int writeQNA(BoardDto boardDto) throws SQLException;
	public List<BoardDto> listQNA(BoardParameterDto boardParameterDto) throws SQLException;
	public int getTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	public BoardDto getQNA(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	public int modifyQNA(BoardDto boardDto) throws SQLException;
	public void deleteMemo(int articleno) throws SQLException;
	public int deleteQNA(int articleno) throws SQLException;
}
