package com.ssafy.vue.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.InterestDto;
import com.ssafy.vue.model.service.InterestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/interest")
@Api("Interest 컨트롤러  API V1")
public class InterestController {
	
	private final Logger logger = LoggerFactory.getLogger(InterestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private InterestService interestService;

	@ApiOperation(value = "관심아파트 등록", notes = "관심아파트의 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> regist(@RequestBody @ApiParam(value = "관심 아파트 정보(userid, aptcode)", required = true) Map<String,Object> map){
		logger.info("interest regist - 호출");
		try {
			if (interestService.regist(map)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("관심 아파트 등록 중 에러 발생");
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "관심아파트 삭제", notes = "관심아파트를 삭제한다 . 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam @ApiParam(value = "관심 아파트 정보(userid, aptcode)", required = true) Map<String,Object> map) throws Exception {
		System.out.println(map);
		logger.info("interest delete - 호출");
		if (interestService.delete(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "관심아파트 목록", notes = "관심아파트 목록을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<InterestDto>> list(
			@RequestParam @ApiParam(value = "관심 아파트 정보(userid)", required = true) Map<String,Object> map) throws Exception {
		logger.info("interest aptList - 호출");
		return new ResponseEntity<List<InterestDto>>(interestService.list(map), HttpStatus.OK);
	}
}
