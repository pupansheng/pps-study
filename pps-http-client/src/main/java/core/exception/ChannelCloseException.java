/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package core.exception;

/**
 * @author Pu PanSheng, 2022/1/19
 * @version OPRA v1.0
 */
public class ChannelCloseException extends RuntimeException {
    public ChannelCloseException() {
    }

    public ChannelCloseException(String message) {
        super(message);
    }

    public ChannelCloseException(Throwable cause) {
        super(cause);
    }
}
