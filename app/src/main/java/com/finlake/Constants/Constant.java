package com.finlake.Constants;

import java.util.UUID;

public class Constant {

    public static final String APP = "FINLAKE";
    public static final String AUTH_TOKEN = "auth_token";

    public static final String LOGGED_IN_USER_ID = "user_id";

    public static final String USER_ID = "user_id";

    public static final String IP_ADDRESS = "192.168.29.109";

    public static final String BASE_URL = "http://" + IP_ADDRESS + ":8080/v1/";

    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}
