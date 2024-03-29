package io.github.grooters.idles.net;

public class ResponseCode {
    public final static int PASS_ERROR = -1;
    public final static int ACCOUNT_NON = -2;
    public final static int LOGIN_SUCCESS = 2;
    public final static int GET_VERIFICATION_SUCCESS = 3;
    public final static int GET_VERIFICATION_FAILURE = -3;
    public final static int VERIFY_SUCCESS = 4;
    public final static int VERIFY_FAILURE = -4;
    public final static int REGISTER_SUCCESS = 5;
    public final static int REGISTER_FAILURE = -5;
    public final static int VERIFICATION_ERROR = -6;
    public final static int GET_TOKEN_SUCCESS = 7;
    public final static int GET_TOKEN_FAILURE = -7;
    public final static int UNKNOWN = 0;
}
