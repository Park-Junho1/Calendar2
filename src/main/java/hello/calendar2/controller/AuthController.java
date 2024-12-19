package hello.calendar2.controller;

import hello.calendar2.dto.LoginDto;
import hello.calendar2.entity.User;
import hello.calendar2.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final Map<String, String> sessionStore = new HashMap<>();

    @GetMapping("/session")
    public String sessionAPI(HttpServletRequest request) {
        log.info("::: AuthController.sessionAPI()");

        Cookie[] cookies = request.getCookies();
        String sessionId = findCookie("sessionId", cookies);

        if (sessionId != null && sessionStore.containsKey(sessionId)) {
            log.info("found userId: {}", sessionId);
            return "user found: " + sessionId;
        } else {
            log.info("user not found");
            return "user not found";
        }
    }

    @PostMapping("/session-login")
    public ResponseEntity<String> cookieLoginAPI(@RequestBody LoginDto loginDto) {
        log.info("::: AuthController.cookieLoginAPI()");

        User user = userService.login(loginDto.getEmail(), loginDto.getPassword());

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, "userId: " + sessionId);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionId=" + sessionId);

        return new ResponseEntity<>("login success", headers, HttpStatus.OK);
    }

    private String findCookie(String key, Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
