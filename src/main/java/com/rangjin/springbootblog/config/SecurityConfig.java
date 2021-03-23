package com.rangjin.springbootblog.config;

import com.rangjin.springbootblog.service.AdminService;
import com.rangjin.springbootblog.service.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminService adminService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/codemirror/**", "/css/**", "/js/**", "/img/**", "/lib/**", "/mail/**", "/scss/**", "/vendor/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        // 페이지 권한 설정
        http.authorizeRequests()
                // 인증된 유저만 접근 가능
                .antMatchers("/post/create", "/post/edit/**", "/post/delete/**").hasRole("ADMIN")
                .antMatchers("/category/create", "/category/edit/**", "/category/delete/**").hasRole("ADMIN")
                // 모든 경로에 대해 권한없이 접근 가능
                .antMatchers("/**").permitAll();
                // 모든 요청에 대해, 인증된 사용자만 접근하도록 설정
                // .anyRequest().authenticated()

        // 로그인 설정
        http.formLogin()
                // 로그인 화면의 url
                .loginPage("/admin/login")
                // 인가를 처리하는 url
                // .loginProcessingUrl("/user/authenticate")
                // 로그인 성공 시 이동 페이지(컨트롤러 매핑 필요)
                .defaultSuccessUrl("/")
                // 로그인 실패 시 이동 페이지(컨트롤러 매핑 필요)
                .failureUrl("/admin/login?error=true")
                // 로그인 실패 헨들러
                .failureHandler(failureHandler())
                // username 파라미터 이름을 변경
                .usernameParameter("username")
                // password 파라미터 이름을 변경
                .passwordParameter("password")
                .permitAll();

        // 로그아웃 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                // 특정 쿠키 삭제시 사용
                // .deleteCookies("KEY명")
                // 로그아웃 화면의 url
                .logoutUrl("/admin/logout")
                // 로그아웃 성공 시 이동 페이지(컨트롤러 매핑 필요)
                .logoutSuccessUrl("/")
                // Http 세션 초기화
                .invalidateHttpSession(true)
                .permitAll();

        // 예외 처리
        http.exceptionHandling()
                .accessDeniedPage("/admin/denied");
    }

}
