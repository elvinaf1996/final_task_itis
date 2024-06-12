package ru.itis.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDTO {
    @Size(min=3, message = "Имя пользователя должно быть не меньше 3 символов")
    private String username;

    @Size(min=3, message = "Пароль должен быть не меньше 3 символов")
    private String password;

    private String confirmPassword;

    private Set<String> roles;
}