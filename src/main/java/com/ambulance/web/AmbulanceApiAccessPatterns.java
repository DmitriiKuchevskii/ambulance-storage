package com.ambulance.web;

public final class AmbulanceApiAccessPatterns
{
    public final static String[] API_PUBLICLY_AVAILABLE = new String[] {
            AmbulanceApi.API_LOGIN_ROOT_REQUEST_MAP + AmbulanceApi.API_LOGIN
    };

    public final static String[] API_USER_ONLY_AVAILABLE = new String[] {
            AmbulanceApi.API_USER_ROOT_REQUEST_MAP + "/**"
    };

    public final static String[] API_ADMIN_ONLY_AVAILABLE = new String[] {
            AmbulanceApi.API_ADMIN_ROOT_REQUEST_MAP + "/**"
    };
}
