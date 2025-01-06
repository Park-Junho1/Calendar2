package hello.calendar2.service;

import hello.calendar2.dto.UserRequestDto;
import hello.calendar2.dto.UserResponseDto;
import hello.calendar2.entity.User;
import hello.calendar2.exception.CustomException;
import hello.calendar2.exception.ErrorCode;
import hello.calendar2.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.update(userRequestDto.getUsername(), userRequestDto.getEmail());
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if(!user.getPassword().equals(password)) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        return user;
    }
}
