package org.easymis.easysaas.gateway.security.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
  未启用
 * @Des: 用户登录验证拦截器 --  执行顺序在UsernamePasswordAuthenticationFilter 拦截器后
 */
public class JwtLoginFilter implements WebFilter {



	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		return null;
	}
}