package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public void signup(MemberDto memberDto) throws SQLException;
	public void edit(MemberDto memberDto) throws SQLException;
	public void deleteInterest(Map<String, Object> map) throws SQLException;
	public void deleteMemo(String userid) throws SQLException;
	public void deleteArticle(String userid) throws SQLException;
	public void delete(String userid) throws SQLException;
	public int idcheck(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;

//	public MemberDto login(MemberDto memberDto) throws SQLException;
//	public MemberDto userInfo(String userid) throws SQLException;
	
}
