package org.easymis.easysaas.netty.n3r.idworker;
public class InvalidSystemClock extends RuntimeException {

    public InvalidSystemClock(String message) {
        super(message);
    }
}
