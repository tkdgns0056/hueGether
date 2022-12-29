package com.project.promotion.config.security;
import com.project.promotion.handler.security.CustomAuthenticationFailureHandler;
import com.project.promotion.handler.security.CustomAuthenticationProvider;
import com.project.promotion.handler.security.CustomAuthenticationSuccessHandler;
import com.project.promotion.service.auth.social.CustomOAuth2AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler  authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomOAuth2AuthService customOAuth2AuthService;

    /*
           정적 리소스 보안 설정(이미지, 자바스크립트, css 등)
         */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/image/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /*
       로그인 인가에 관한 설정
       위에서는 로그인 가능한 아이디인지 여부를 확인한다면
       아래에서는 일반 유저, 로그인 유저 등등이 어디서부터 어디까지 접근 가능한지
       혹은 로그인과 로그아웃과 관련된 설정에 대한 부분을 여기서 한다.

     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable(); //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.
        http
                .authorizeRequests()
                        .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll();
        http
                    .formLogin()
                    .loginPage("/user/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .loginProcessingUrl("/login-proc")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler);

        http
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");

        http
                    .oauth2Login()
                    .loginPage("/user/login").permitAll()
                    .defaultSuccessUrl("/login-proc", true)
                    .userInfoEndpoint()//OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                    .userService(customOAuth2AuthService);// SNS 로그인이 완료된 뒤 후 처리가 필요함. 엑세스 토큰 + 사용자 프로필 정보

        /* 세션 관련 */
        http
                    .sessionManagement()
                    .maximumSessions(1) //세션 최대 허용 수
                    .maxSessionsPreventsLogin(true) // 허용 가능한 세션의 수가 되었을 때,
                                                    // 추가적인 인증이 있을 경우 어떻게 처리할 것인지를 설정하는 기능
                                                    // true : 현재 요청하는 사용자의 인증을 실패 시킴.
                                                    // false :기존에 인증된 세션을 만료.
                    .expiredUrl("/"); //세션이 만료된 경우 이동할 페이지 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }
}
