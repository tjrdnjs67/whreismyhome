package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
//@RestControllerAdvice 해볼거
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserid());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserid());// key, data
				memberService.saveRefreshToken(memberDto.getUserid(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// userid, username, userpwd, usertype, email
	@ApiOperation(value = "회원가입", notes = "회원정보를 등록한다.", response = Map.class)
	@PostMapping
	public ResponseEntity<Map<String, Object>> signup(
			@RequestBody @ApiParam(value = "회원가입 시 필요한 정보(아이디, 비밀번호, 이름, 이메일).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		
		logger.info("signup - 호출");
		try {
			memberService.signup(memberDto);
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			resultMap.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "아이디 중복 검사", notes = "회원 아이디가 중복되었는지 확인한다.", response = Map.class)
	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<?> idcheck(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		int chk = 1;
		try {
			chk = memberService.idcheck(userid);
		} catch (Exception e) {
			System.out.println(e);
		}
		if (chk == 0) {
			resultMap.put("message", SUCCESS);
		} else {
			resultMap.put("message", FAIL);
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보 수정", notes = "회원정보를 수정한다.", response = Map.class)
	@PutMapping
	public ResponseEntity<Map<String, Object>> useredit(
			@RequestBody @ApiParam(value = "수정할 정보(아이디, 비밀번호, 이름, 이메일).", required=false) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		logger.info("useredit - 호출");
		try {
			memberService.edit(memberDto);
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			resultMap.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "회원탈퇴", notes = "회원을 탈퇴한다.", response = Map.class)
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> userdelete(
			@RequestParam @ApiParam(value = "회원의 아이디", required = true) String userid) {
		Map<String, Object> resultMap = new HashMap<>();

		logger.info("userdelete - 호출 :" + userid);
		try {
			memberService.delete(userid);
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			resultMap.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
		// logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				// 로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserid()))) {
				String accessToken = jwtService.createAccessToken("userid", memberDto.getUserid());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
