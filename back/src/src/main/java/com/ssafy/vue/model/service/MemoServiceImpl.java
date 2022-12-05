package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.MemoDto;
import com.ssafy.vue.model.MemoParameterDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.MemoMapper;

@Service
public class MemoServiceImpl implements MemoService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean regist(MemoDto memoDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemoMapper.class).regist(memoDto) == 1;
	}

	@Override
	public List<MemoDto> list(MemoParameterDto memoParameterDto) throws Exception {
		int start = memoParameterDto.getPg() == 0 ? 0 : (memoParameterDto.getPg() - 1) * memoParameterDto.getSpp();
		memoParameterDto.setStart(start);
		return sqlSession.getMapper(MemoMapper.class).list(memoParameterDto);
	}
	
	@Override
	public PageNavigation makePageNavigation(MemoParameterDto memoParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(memoParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(MemoMapper.class).getTotalCount(memoParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / memoParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = memoParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < memoParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public boolean edit(MemoDto memoDto) throws Exception {
		return sqlSession.getMapper(MemoMapper.class).edit(memoDto) == 1;
	}

	@Override
	public boolean delete(int memono) throws Exception {
		return sqlSession.getMapper(MemoMapper.class).delete(memono) == 1;
	}

	
}
