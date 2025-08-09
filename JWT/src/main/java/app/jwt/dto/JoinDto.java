package app.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDto {
    private String username;
    private String password;
}
