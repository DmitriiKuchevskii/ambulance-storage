package com.ambulance.security;

import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public final class Roles {
    public final static String ROLE_USER = "ROLE_USER";
    public final static String ROLE_ADMIN = "ROLE_ADMIN";

    public static final RoleHierarchyImpl ROLES_HIERARCHY;
    static {
        ROLES_HIERARCHY = new RoleHierarchyImpl();
        ROLES_HIERARCHY.setHierarchy(String.format("%s > %s", ROLE_ADMIN, ROLE_USER));
    }
}
