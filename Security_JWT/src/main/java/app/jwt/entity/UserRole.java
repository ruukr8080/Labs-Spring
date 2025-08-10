package app.jwt.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN,
    ROLE_USER
}
