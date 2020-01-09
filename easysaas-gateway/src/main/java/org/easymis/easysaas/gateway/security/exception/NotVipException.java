package org.easymis.easysaas.gateway.security.exception;


import org.springframework.security.access.AccessDeniedException;

public class NotVipException extends AccessDeniedException {

    public NotVipException(String msg) {
        super(msg);
    }

    public NotVipException(String msg, Throwable t) {
        super(msg, t);
    }
}
