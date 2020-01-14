package org.easymis.easysaas.gateway.config;

import org.easymis.easysaas.gateway.security.handler.JwtAuthenticationSuccessHandler;
import org.easymis.easysaas.gateway.security.test1.AuthenticationFaillHandler;
import org.easymis.easysaas.gateway.security.test1.CustomAccessDeineHandler;
import org.easymis.easysaas.gateway.security.test1.CustomHttpBasicServerAuthenticationEntryPoint;
import org.easymis.easysaas.gateway.security.test1.JwtWebConfig;
import org.easymis.easysaas.gateway.security.test1.filter.JwtLoginFilter;
import org.easymis.easysaas.gateway.security.test1.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfigType1 {

    @Autowired
    private JwtAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFaillHandler authenticationFaillHandler;
    @Autowired
    private CustomHttpBasicServerAuthenticationEntryPoint customHttpBasicServerAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;

    //security的鉴权排除列表
    private static final String[] excludedAuthPages = {
            "/user/login",
            "/auth/logout",
            "/health",
            "/api/socket/**"
    };

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    	
        // 自定义登陆拦截器
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();
        http
                .authorizeExchange()
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                //.addFilterAt( jwtLoginFilter, SecurityWebFiltersOrder.LOGIN_PAGE_GENERATING) //登录拦截
                .addFilterAt(new JwtWebConfig(), SecurityWebFiltersOrder.AUTHORIZATION)
                .formLogin().loginPage("/user/login")//登录页面访问路径
                .authenticationSuccessHandler(authenticationSuccessHandler) //认证成功
                .authenticationFailureHandler(authenticationFaillHandler) //登陆验证失败
                .and().exceptionHandling().authenticationEntryPoint(customHttpBasicServerAuthenticationEntryPoint)  //用来解决匿名用户访问无权限资源时的异常,基于http的接口请求鉴权失败
                .accessDeniedHandler(customAccessDeineHandler)//没有访问权限
                .and() .csrf().disable()//必须支持跨域
                .logout().disable();

        return http.build();
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return  NoOpPasswordEncoder.getInstance(); //默认
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	public static void main(String[] args) {
		    Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("1");
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	 
	        String pbk1 = pbkdf2PasswordEncoder.encode("123456");
	        pbkdf2PasswordEncoder.upgradeEncoding("2");
	        String pbk2 = pbkdf2PasswordEncoder.encode("123456");
	 
	        String bcr1 = bCryptPasswordEncoder.encode("123456");
	        String bcr2 = bCryptPasswordEncoder.encode("123456");
	 
	        System.out.println("pbk1: " + pbk1.length());
	        System.out.println("pbk2: " + pbk2.length());
	        System.out.println("pbk1 password:" + pbkdf2PasswordEncoder.matches("123456",pbk1));
	        System.out.println("pbk2 password:" + pbkdf2PasswordEncoder.matches("123456",pbk2));
	 
	        System.out.println("---------------------");
	 
	        System.out.println("bcr1: " + bcr1);
	        System.out.println("bcr2: " + bcr2.length());
	        System.out.println("bcr1 password:" + bCryptPasswordEncoder.matches("123456",bcr1));
	        System.out.println("bcr2 password:" + bCryptPasswordEncoder.matches("123456",bcr2));
	}
}