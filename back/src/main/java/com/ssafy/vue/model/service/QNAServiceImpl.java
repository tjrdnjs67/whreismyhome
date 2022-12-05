package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.mapper.QNAMapper;

@Service
public class QNAServiceImpl implements QNAService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeQNA(BoardDto boardDto) throws Exception {
		if(boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(QNAMapper.class).writeQNA(boardDto) == 1;
	}

	@Override
	public List<BoardDto> listQNA(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return sqlSession.getMapper(QNAMapper.class).listQNA(boardParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(QNAMapper.class).getTotalCount(boardParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = boardParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public BoardDto getQNA(int articleno) throws Exception {
		return sqlSession.getMapper(QNAMapper.class).getQNA(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(QNAMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyQNA(BoardDto boardDto) throws Exception {
		return sqlSession.getMapper(QNAMapper.class).modifyQNA(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteQNA(int articleno) throws Exception {
		sqlSession.getMapper(QNAMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(QNAMapper.class).deleteQNA(articleno) == 1;
	}

}
