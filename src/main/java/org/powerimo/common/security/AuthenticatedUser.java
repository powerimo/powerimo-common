package org.powerimo.common.security;

import lombok.Data;
import org.powerimo.common.PowerimoConst;
import org.powerimo.common.exception.ForbiddenException;

import java.util.List;
import java.util.UUID;

@Data
public class AuthenticatedUser {
    private List<UUID> accounts;
    private List<GrantedRole> roles;
    private UUID userId;
    private String name;
    private String authUserId;
    private UserStatus status;

    public boolean isApiClient() {
        if (roles != null)
            for (var role: roles) {
                if (role.getRoleId().equals(PowerimoConst.ROLE_API_CLIENT))
                    return true;
            }
        return false;
    }

    public boolean isHumanUser() {
        return !isApiClient();
    }

    public boolean isAccountAdmin(UUID accountId) {
        return hasRole(accountId, PowerimoConst.ROLE_ACCOUNT_ADMIN);
    }

    public boolean hasRole(UUID accountId, String role) {
        if (roles != null)
            for (var item: roles) {
                if (item.getRoleId().equals(role) && item.getAccountId().equals(accountId))
                    return true;
            }
        return false;
    }

    public boolean hasAnyAccountRole(UUID accountId) {
        if (roles != null)
            for (var item: roles) {
                if (item.getAccountId().equals(accountId))
                    return true;
            }
        return false;
    }

    public void checkAccountAccess(UUID accountId) {
        if (!hasAnyAccountRole(accountId)) {
            throw new ForbiddenException();
        }
    }
}
