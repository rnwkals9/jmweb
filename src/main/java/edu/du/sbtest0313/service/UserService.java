package edu.du.sbtest0313.service;

import edu.du.sbtest0313.entity.Board;
import edu.du.sbtest0313.entity.User;
import edu.du.sbtest0313.repository.UserRepository;
import edu.du.sbtest0313.repository.BoardRepository;

import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {


    private final UserRepository userRepository;


    // 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    /**
     * 회원 등록
     */
    public User registerUser(User user) {
        // 이미 존재하는 이메일 또는 사용자명은 저장 X (옵션: 예외처리 가능)
        if (isEmailDuplicate(user.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        if (isUsernameDuplicate(user.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }

        return userRepository.save(user);
    }

    /**
     * 이메일로 사용자 조회
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * 사용자명 중복 여부
     */
    public boolean isUsernameDuplicate(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * 이메일 중복 여부
     */
    public boolean isEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
