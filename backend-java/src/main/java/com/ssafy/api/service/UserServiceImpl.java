package com.ssafy.api.service;

import com.ssafy.common.util.MailUtil;
import com.ssafy.db.dto.UserEmNnmDto;
import com.ssafy.db.dto.UserHistoryDto;
import com.ssafy.db.dto.UserStatDto;
import com.ssafy.db.entity.UserHistory;
import com.ssafy.db.entity.UserStat;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStatRepository userStatRepository;

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender mailSender;

    @Override
    @Transactional
    public UserEmNnmDto createUser(UserRegisterPostReq userRegisterInfo) {
        boolean isEmDupl = userRepository.findUserByEm(userRegisterInfo.getEm()).isPresent();
        boolean isNnmDupl = userRepository.findUserByNnm(userRegisterInfo.getNnm()).isPresent();
        UserEmNnmDto userEmNnmDto = UserEmNnmDto.builder().emDuple(isEmDupl).nnmDuple(isNnmDupl).build();
        if (userEmNnmDto.isNnmDuple() || userEmNnmDto.isEmDuple()) return userEmNnmDto;

        User user = User.builder()
                .em(userRegisterInfo.getEm())
                .pwd(passwordEncoder.encode(userRegisterInfo.getPwd()))
                .nnm(userRegisterInfo.getNnm())
                .build();


        userRepository.save(user); //user table에 삽입

        UserStat userStat = UserStat.builder().id(user.getId()).build();
        userStatRepository.save(userStat);

        return null;
    }

    @Override
    public User getUserById(Long id) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
        User user = userRepository.findUserById(id).get();
        return user;
    }

    @Override
    public User getUserByEm(String em) {
        User user = userRepository.findUserByEm(em).get();
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRegisterPostReq userRegisterInfo) {
        User user = userRepository.findById(id).get();

        // 비밀번호 암호화
        if (userRegisterInfo.getPwd() != null) {
            user.setPwd(passwordEncoder.encode(userRegisterInfo.getPwd()));
        }
        if (userRegisterInfo.getNnm() != null) {
            user.setNnm(userRegisterInfo.getNnm());
        }
        // 회원 정보 수정일 설정
        user.setModify_dt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    @Override
    public User setRefresh(Long id, String refreshToken) {
        User user = userRepository.findById(id).get();

        user.setRefresh_tk(refreshToken);

        userRepository.save(user);
        return null;
    }

    @Override
    public String getRefresh(Long id) {
        return getUserById(id).getRefresh_tk();
    }

    @Override
    public void resignUserById(Long id) {
        User user = userRepository.findById(id).get();

        // user의 leave_dt 변경
        user.setLeave_dt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserStatDto getUserStatById(Long id) {
        UserStatDto userStatDto = new UserStatDto(userStatRepository.findStatById(id).get());
        System.out.println("-----------");
        System.out.println(userStatDto);
        System.out.println("-----------");
        return userStatDto;
    }

    @Override
    public List<UserHistoryDto> getUserHistoryById(Long id) {
        List<UserHistory> userHistoryList = userHistoryRepository.findUserHistoryByUserId(id).get();
        System.out.println("-----------");
        List<UserHistoryDto> userHistoryDtoList = new ArrayList<>();
        for(UserHistory u : userHistoryList) {
            userHistoryDtoList.add(new UserHistoryDto(u));
        }
        for (UserHistoryDto dto : userHistoryDtoList) System.out.println(dto);
        System.out.println("-----------");
        return userHistoryDtoList;
    }

    @Override
    public void updateEmailAuth(String em) {
        User user = userRepository.findUserByEm(em).get();

        user.setEm_auth(true);

        userRepository.save(user);
    }

    @Override
    public void sendAuthMail(User user) throws Exception{
        MailUtil sendMail = new MailUtil(mailSender);

        sendMail.setSubject("[HooPaa 회원가입 서비스 이메일 인증 입니다.]");
        sendMail.setText(new StringBuffer().append("<h1>HooPaa 가입 메일인증 입니다</h1>")
                .append("<a href='http://localhost:8083/login?em=")
                .append(user.getEm())//.append("&key=").append(key)
                .append("' target='_blenk'>가입 완료를 위해 이곳을 눌러주세요</a>").toString());
        sendMail.setFrom("wonjaechoi5295@gmail.com", "HooPaa");
        sendMail.setTo(user.getEm());
        sendMail.send();

    }

    @Override
    public Boolean checkDupl(UserEmNnmDto userEmNnmDto) {
        String em = userEmNnmDto.getEm();
        String nnm = userEmNnmDto.getNnm();

        if (em != null) return userRepository.findUserByEm(em).isPresent();
        if (nnm != null) return userRepository.findUserByNnm(nnm).isPresent();
        return null;
    }

}
