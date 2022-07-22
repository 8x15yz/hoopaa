package com.ssafy.api.controller;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.response.JsonRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.db.dto.UserHistoryDto;
import com.ssafy.db.dto.UserStatDto;
import com.ssafy.db.entity.UserStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ObjectMapper mapper;

	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userEm = loginInfo.getEm();
		String password = loginInfo.getPwd();

		User user = userService.getUserByEm(userEm);

		// 탈퇴된 회원이면 로그인 실패
		if (user.getLeave_dt() != null) ResponseEntity.status(401).body(UserLoginPostRes.of(404, "Resigned User", null, null));
		
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPwd())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			String accessToken = JwtTokenUtil.getAccessToken(String.valueOf(user.getId()));
			String refreshToken = JwtTokenUtil.getRefreshToken(String.valueOf(user.getId()));
			userService.setRefresh(user.getId(),refreshToken);
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", accessToken, refreshToken));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null, null));
	}

	@PostMapping("/verify")
	@ApiOperation(value = "비밀번호 확인", notes = "<strong>비밀번호</strong>를 통해 본인 인증을 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> checkPwd(@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value="패스워드", required = true) UserLoginPostReq userLoginInfo) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		String pwd = userLoginInfo.getPwd();

		User user = userService.getUserById(Long.parseLong(userId));

		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(pwd, user.getPwd())) {

			String accessToken = JwtTokenUtil.getAccessToken(String.valueOf(user.getId()));
			String refreshToken = JwtTokenUtil.getRefreshToken(String.valueOf(user.getId()));

			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰, 리프레시 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", accessToken, refreshToken));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null, null));
	}

	@PostMapping("/refresh")
	@ApiOperation(value = "access토큰 재발급 요청", notes = "refresh token으로 <strong>access token</strong>을 요청한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> refresToken(@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value="리프레시 토큰", required = true) Map<String ,String > tokens) {

		String accessToken = tokens.get("accessToken");
		String refreshToken = tokens.get("refreshToken");
		if (refreshToken != null) {
			// parse the token and validate it (decode)
			JWTVerifier verifier = JwtTokenUtil.getRefreshVerifier();

			try {
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String id = decodedJWT.getSubject();
				String localRefreshToken = userService.getRefresh(Long.parseLong(id));

				if (refreshToken.equals(localRefreshToken)) {
					accessToken = JwtTokenUtil.getAccessToken(String.valueOf(id));
					return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", accessToken, refreshToken));
				}
			}catch (Exception e){
				return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "invalid refresh token", null, null));
			}


		}
		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "invalid refresh token", null, null));
	}

	/**
	 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
	 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
	 */
	@GetMapping("/info")
	@ApiOperation(value = "개인정보 조회", notes = "개인정보 수정을 위한 계정 정보를 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String id = userDetails.getUsername();
		User user = userService.getUserById(Long.parseLong(id));

		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	@PutMapping("/info")
	@ApiOperation(value = "회원 정보 수정", notes = "비밀번호, 닉네임 수정. ")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> modifyUser(@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value = "수정할 회원 정보", required = true) UserRegisterPostReq userRegisterInfo) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();

		User user = userService.updateUser(Long.parseLong(userId), userRegisterInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@DeleteMapping("/resign")
	@ApiOperation(value = "회원 탈퇴", notes = "현재 로그인 되어있는 사용자 탈퇴")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> resignUser(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String id = userDetails.getUsername();
		userService.resignUserById(Long.parseLong(id));

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping("/stat")
	@ApiOperation(value = "유저 스탯 조회", notes = "토큰 정보에 있는 유저의 스탯을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> getUserStat(@ApiIgnore Authentication authentication) throws JsonProcessingException {
		SsafyUserDetails ssafyUserDetails = (SsafyUserDetails)authentication.getDetails();
		String id = ssafyUserDetails.getUsername();

		UserStatDto userStatDto = userService.getUserStatById(Long.parseLong(id));
		String userStatString = mapper.writeValueAsString(userStatDto);

		return ResponseEntity.ok(JsonRes.of(200, "success", userStatString));
	}

	@GetMapping("/history")
	@ApiOperation(value = "유저 전적 조회", notes = "토큰에 있는 유저의 전적을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> getUserHistory(@ApiIgnore Authentication authentication) throws JsonProcessingException {
		SsafyUserDetails ssafyUserDetails = (SsafyUserDetails)authentication.getDetails();
		String id = ssafyUserDetails.getUsername();

		List<UserHistoryDto> userHistoryDtoList = userService.getUserHistoryById(Long.parseLong(id));
		String userHistoryString = mapper.writeValueAsString(userHistoryDtoList);

		return ResponseEntity.ok(JsonRes.of(200, "success", userHistoryString));
	}

}
