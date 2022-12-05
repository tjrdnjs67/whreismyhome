package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.MemoDto;
import com.ssafy.vue.model.MemoParameterDto;
import com.ssafy.vue.model.service.MemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/memo")
@Api("댓글 컨트롤러  API V1")
public class MemoController {

	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemoService memoService;
	
	@ApiOperation(value = "댓글 작성", notes = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> regist(@RequestBody @ApiParam(value = "게시글 정보.", required = true) MemoDto memoDto) throws Exception {
		System.out.println(memoDto + "입니다.");
		logger.info("regist - 호출");
		if (memoService.regist(memoDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글 목록", notes = "게시글의 모든 댓글 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<MemoDto>> list(@ApiParam(value = "댓글을 얻기위한 부가정보.", required = true) MemoParameterDto memoParameterDto) throws Exception {
		logger.info("list - 호출");
		return new ResponseEntity<List<MemoDto>>(memoService.list(memoParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "네비게이션", notes = "댓글의 네비게이션을 반환한다.", response = String.class)
	@GetMapping("/nav")
	public ResponseEntity<String> navigation(@ApiParam(value = "네비게이션을 얻기위한 부가정보.", required = true)  MemoParameterDto memoParameterDto) throws Exception {
		logger.info("memo navigation - 호출");
		PageNavigation nav = memoService.makePageNavigation(memoParameterDto);
		
		return new ResponseEntity<String>(nav.getNavigator(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "댓글 수정", notes = "게시글의 댓글을 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> edit(@ApiParam(value = "댓글 수정을 위한 부가정보.", required = true) MemoDto memoDto) throws Exception {
		logger.info("edit - 호출");
		if (memoService.edit(memoDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "게시글의 댓글을 삭제한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> delete(@ApiParam(value = "댓글 삭제를 위한 부가정보.", required = true) int memono) throws Exception {
		logger.info("delete - 호출");
		if (memoService.delete(memono)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
