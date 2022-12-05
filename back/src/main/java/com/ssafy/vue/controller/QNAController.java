package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.service.QNAService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
@Api("QNA 컨트롤러  API V1")
public class QNAController {
	private static final Logger logger = LoggerFactory.getLogger(QNAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	QNAService qnaService;
	
	@ApiOperation(value = "QNA 작성", notes = "새로운 QNA를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeQNA(@RequestBody @ApiParam(value = "QNA 정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("writeQNA - 호출");
		if (qnaService.writeQNA(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "QNA 목록", notes = "모든 QNA를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardDto>> listQNA(@ApiParam(value = "QNA을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("listQNA - 호출");
		return new ResponseEntity<List<BoardDto>>(qnaService.listQNA(boardParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "네비게이션", notes = "게시판의 네비게이션을 반환한다.", response = String.class)
	@GetMapping("/nav")
	public ResponseEntity<String> navigation(@ApiParam(value = "네비게이션을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("navigation - 호출");
		PageNavigation nav = qnaService.makePageNavigation(boardParameterDto);
		
		return new ResponseEntity<String>(nav.getNavigator(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QNA 보기", notes = "글번호에 해당하는 QNA를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardDto> getArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 QNA 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("getQNA - 호출 : " + articleno);
		qnaService.updateHit(articleno);
		return new ResponseEntity<BoardDto>(qnaService.getQNA(articleno), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QNA 수정", notes = "수정할 QNA를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 QNA 정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modifyQNA - 호출 {}", boardDto);
		
		if (qnaService.modifyQNA(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "QNA 삭제", notes = "글번호에 해당하는 QNA를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 QNA의 글번호.", required = true) int articleno) throws Exception {
		logger.info("deleteQNA - 호출");
		if (qnaService.deleteQNA(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
