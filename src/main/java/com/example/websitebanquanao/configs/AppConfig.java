package com.example.websitebanquanao.configs;

import com.example.websitebanquanao.infrastructures.filters.AdminFilter;
import com.example.websitebanquanao.infrastructures.filters.UserFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<UserFilter> userSessionFilter() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/gio-hang/*");
        registrationBean.addUrlPatterns("/hoa-don/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminSessionFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        return registrationBean;
    }
}
