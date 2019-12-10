package com.ambulance.web;

public final class WebUiAccessPatterns
{
    public final static String[] WEB_UI_PUBLICLY_AVAILABLE = new String[] {
            "/*", "/home/*"
    };

    public final static String[] WEB_UI_USER_ONLY_AVAILABLE = new String[] {
            "/user/**"
    };

    public final static String[] WEB_UI_ADMIN_ONLY_AVAILABLE = new String[] {
            "/admin/**"
    };
}
