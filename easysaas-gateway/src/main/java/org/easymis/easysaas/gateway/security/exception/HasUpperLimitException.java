package org.easymis.easysaas.gateway.security.exception;

/**
 *
 *  已达上限
 */
public class HasUpperLimitException extends RuntimeException {

    public HasUpperLimitException(String msg) {
        super(msg);
    }

     public HasUpperLimitException(){

    }
}
