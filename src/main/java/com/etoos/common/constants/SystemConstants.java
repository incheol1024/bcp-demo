package com.etoos.common.constants;

public class SystemConstants {
    private SystemConstants() {
        throw new IllegalStateException("SystemConstants class");
    }
    // 로그인 KEY
    public static final String SESSION_INFO = "sessionVO";
    public static final String PROGRESS = "progress";

    public static final String RANDOM_UPPER_STRINGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String RANDOM_LOWER_STRINGS = "abcdefghijklmnopqrstuvwxyz";
    public static final String RANDOM_INT_STRINGS = "0123456789";
    public static final String RANDOM_STRINGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final String LINE = "─────────────────────────────────────────────────────────────────────────────";
}
