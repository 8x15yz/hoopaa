package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.dto.UserEmNnmDto;
import com.ssafy.db.dto.UserHistoryDto;
import com.ssafy.db.dto.UserStatDto;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	// Create
	UserEmNnmDto createUser(UserRegisterPostReq userRegisterInfo);
	// Read
	User getUserById(Long id);
	User getUserByEm(String em);  // 로그인 단계에서만 사용
	// Update
	User updateUser(Long id, UserRegisterPostReq userRegisterInfo);
	User setRefresh(Long id, String refreshToken);
	String getRefresh(Long id);

	// Delete
	void resignUserById(Long id);
	UserStatDto getUserStatById(Long id);
	List<UserHistoryDto> getUserHistoryById(Long id);

	void updateEmailAuth(String em);

	void sendAuthMail(User user) throws Exception;

	Boolean checkDupl(UserEmNnmDto userEmNnmDto);
}
