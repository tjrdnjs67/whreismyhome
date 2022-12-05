package com.ssafy.vue.model.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
	}

	@Override
	public void signup(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberMapper.class).signup(memberDto);		
	}

	@Override
	public void edit(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberMapper.class).edit(memberDto);		
	}

	@Override
	public void delete(String userid) throws Exception {
		System.out.println("delete service 들어옴 : " + userid);
		sqlSession.getMapper(MemberMapper.class).deleteMemo(userid);
		sqlSession.getMapper(MemberMapper.class).deleteArticle(userid);
		sqlSession.getMapper(MemberMapper.class).delete(userid);		
	}

	@Override
	public int idcheck(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idcheck(userid);
	}

}
