package tencent.wx.pay;

import artoria.exception.UncheckedException;

/**
 * WeiXin pay exception.
 * @author Kahle
 */
public class WxPayException extends UncheckedException {

    public WxPayException() {

        super();
    }

    public WxPayException(String message) {

        super(message);
    }

    public WxPayException(Throwable cause) {

        super(cause);
    }

    public WxPayException(String message, Throwable cause) {

        super(message, cause);
    }

}
