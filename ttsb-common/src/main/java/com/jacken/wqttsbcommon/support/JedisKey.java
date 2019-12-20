package com.jacken.wqttsbcommon.support;

public final class JedisKey {

    public static final int ONE_SECONDS = 1;

    public static final int ONE_MINUTE = ONE_SECONDS * 60;

    public static final int TEN_MINUTE = ONE_MINUTE * 10;

    public static final int THREE_MINUTE = ONE_MINUTE * 3;

    public static final int HALF_HOUR = TEN_MINUTE * 3;

    public static final int ONE_HOUR = ONE_MINUTE * 60;

    public static final int TWO_HOURS = ONE_HOUR * 2;

    public static final int EIGHT_HOUR = ONE_HOUR * 8;

    public static final int ONE_DAY = ONE_HOUR * 24;

    public static final int ONE_WEEK = ONE_DAY * 7;

    public static final int ONE_MONTH = ONE_DAY * 30;

    public static final String PREFIX = "youcardhone:youxin";

    public static final String PREFIX_H5 = "youcardhone:youxin:h5";

    public static final String CACHE_PREFIX = ":";

    private JedisKey() {

    }

    /**
     * app短信key
     *
     * @param phone
     * @return
     */
    public static String smsKey(String phone) {
        return buildKey("smsKey", phone);
    }

    /**
     * 后台管理短信key
     *
     * @param phone
     * @return
     */
    public static String cmsSmsKey(String phone) {
        return buildKey("cmsSmsKey", phone);
    }

    public static String geotmtaiKey(String phone) {
        return buildKey("geotmtaiKey", phone);
    }

    public static String userTokenKey(Long userId) {
        return buildKey("userTokenKey", userId);
    }

    public static String sysUserTokenKey(Long userId) {
        return buildKey("sysUserTokenKey", userId);
    }


    /**
     * 短信间隔时间
     *
     * @param phone
     * @param smsType
     * @return
     */
    public static String smsCodeIntervalTimeKey(String phone, Integer smsType) {
        return buildKey("smsCodeIntervalTimeKey", phone + "_" + smsType);
    }

    /**
     * 短信间隔时间
     *
     * @param phone
     * @param smsType
     * @return
     */
    public static String cmsSmsCodeIntervalTimeKey(String phone, Integer smsType) {
        return buildKey("cmsSmsCodeIntervalTimeKey", phone + "_" + smsType);
    }

    public static String redisLock(String lockKey) {
        return buildKey(lockKey);
    }


    private static String buildKey(Object str1, Object... array) {
        StringBuilder stringBuilder = new StringBuilder(PREFIX);
        stringBuilder.append(CACHE_PREFIX).append(str1);
        for (Object obj : array) {
            stringBuilder.append(CACHE_PREFIX).append(obj);
        }
        return stringBuilder.toString();
    }


    public static String idCheckKey(String name, String idCard, String userPhone) {
        return buildKey("idCheckKey", name + "_" + idCard + "_" + userPhone);
    }

    public static String idCheckTimeKey() {
        return buildKey("idCheckTimeKey", "idCheckTimeKey");
    }
}
