package com.ssafy.vue.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class HouseMapController {

	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "동 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<SidoGugunCodeDto>> dong(
			@RequestParam("gugun") @ApiParam(value = "구군코드.", required = true) String gugun) throws Exception {
		logger.info("dong - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}

	@ApiOperation(value = "아파트 목록", notes = "동에 속하는 아파트 목록을 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<HouseInfoDto>> aptList(
			@RequestParam("dong") @ApiParam(value = "동코드.", required = true) String dong) throws Exception {
		logger.info("aptList - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트", notes = "아파트코드에 해당하는 아파트를 반환한다.", response = HouseInfoDto.class)
	@GetMapping("/apt")
	public ResponseEntity<HouseInfoDto> getApt(
			@RequestParam("aptcode") @ApiParam(value = "아파트코드.", required = true) String aptcode) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<HouseInfoDto>(haHouseMapService.getApt(aptcode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 검색 목록", notes = "검색한 아파트 이름에 속하는 아파트 목록을 반환한다.", response = List.class)
	@GetMapping("/search")
	public ResponseEntity<List<HouseInfoDto>> aptSearchList(
			@RequestParam("word") @ApiParam(value = "아파트 이름.", required = true) String word) throws Exception {
		logger.info("apSearchtList - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInSearch(word), HttpStatus.OK);
	}
	
}
