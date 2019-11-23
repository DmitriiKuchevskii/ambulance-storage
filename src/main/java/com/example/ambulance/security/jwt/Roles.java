package com.example.ambulance.security.jwt;

import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public enum Roles {
    ROLE_USER,
    ROLE_ADMIN;

    public static final RoleHierarchyImpl ROLES_HIERARCHY;
    static {
        ROLES_HIERARCHY = new RoleHierarchyImpl();
        ROLES_HIERARCHY.setHierarchy(String.format("%s > %s", ROLE_ADMIN.name(), ROLE_USER.name()));
    }
}
