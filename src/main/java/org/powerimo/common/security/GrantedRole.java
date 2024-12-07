package org.powerimo.common.security;

import lombok.Data;

import java.util.UUID;

@Data
public class GrantedRole {
    private UUID accountId;
    private String roleId;

    public GrantedRole(UUID accountId, String roleId) {
        this.accountId = accountId;
        this.roleId = roleId;
    }
}
