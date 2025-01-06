package hello.calendar2.config;

import hello.calendar2.service.SessionService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> filterRegistrationBean(SessionService sessionService) {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter(sessionService));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
