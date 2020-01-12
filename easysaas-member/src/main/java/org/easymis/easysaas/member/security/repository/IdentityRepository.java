package org.easymis.easysaas.member.security.repository;


import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.easymis.easysaas.member.entitys.mybatis.dto.Permit;
import org.easymis.easysaas.member.security.exception.PrincipalNotFundException;
import org.easymis.easysaas.member.security.exception.UnknownPrincipalException;
import org.easymis.easysaas.member.security.userdetail.ExpireDateGrantedAuthority;
import org.easymis.easysaas.member.security.userdetail.SecurityUserDetails;

public interface IdentityRepository {


    /**
     * 获取 Principal
     *
     * @return permit
     */
    default SecurityUserDetails getPrincipalByAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.isNull(principal)) {
            throw new PrincipalNotFundException("not fund  user in authentication", Permit.class);
        } else {
            if (principal instanceof SecurityUserDetails) {
                return (SecurityUserDetails) principal;
            } else {   //表示 permit
                throw new UnknownPrincipalException();
            }
        }
    }

    default String getRoleSn(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.isNull(principal)) {
            throw new PrincipalNotFundException("not fund  user in authentication", Permit.class);
        } else {
            if (principal instanceof SecurityUserDetails) {
                return ((SecurityUserDetails) principal).getEffactiveAuthority().getAuthority();
            } else {   //表示 permit
                return ExpireDateGrantedAuthority.anonymous;
            }
        }
    }

    /**
     * 获取 主要的身份特征;
     *
     * @return
     */

    default String getIdentityFeature() {
        SecurityUserDetails permit = this.getPrincipalByAuthentication();
        return permit.getUserNo();
    }
}
