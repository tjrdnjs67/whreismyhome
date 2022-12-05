package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.InterestDto;
import com.ssafy.vue.model.mapper.InterestMapper;

@Service
public class InterestServiceImpl implements InterestService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<InterestDto> list(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(InterestMapper.class).list(map);
	}

	@Override
	public boolean regist(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(InterestMapper.class).regist(map) == 1;
	}

	@Override
	public boolean delete(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(InterestMapper.class).delete(map) == 1;
	}

}
