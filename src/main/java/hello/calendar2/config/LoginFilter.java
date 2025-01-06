package hello.calendar2.config;

import hello.calendar2.service.SessionService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/auth/session-login"};
    private final SessionService sessionService;

    public LoginFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        if (isWhiteListed(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = httpRequest.getCookies();
        String sessionId = findCookie("sessionId", cookies);

        if (sessionId != null || !sessionService.isValidSession(sessionId)) {
            log.warn("Unauthorized Access attempt to {}", requestURI);
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteListed(String URI) {
        for (String whiteList : WHITE_LIST) {
            if (URI.startsWith(whiteList)) {
                return true;
            }
        }
        return false;
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
