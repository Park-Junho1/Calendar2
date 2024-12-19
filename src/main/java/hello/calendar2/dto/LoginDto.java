package hello.calendar2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginDto {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
