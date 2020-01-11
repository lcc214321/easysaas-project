package org.easymis.easysaas.gateway.service;

import org.easymis.easysaas.gateway.entitys.mybatis.dto.Member;
import org.easymis.easysaas.gateway.entitys.vo.User;

import reactor.core.publisher.Mono;


public interface UserService {
	public Mono<User> findByUsername(String username);	
	public Member findByMobile(String mobile);	
	
}
