package com.ssafy.vue.model.service;

import java.util.Map;

import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void signup(MemberDto memberDto) throws Exception;
	public void edit(MemberDto memberDto) throws Exception;
	public void delete(String userid) throws Exception;
	public int idcheck(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	
	
//	public MemberDto login(MemberDto memberDto) throws Exception;
//	public MemberDto userInfo(String userid) throws Exception;
		
}
