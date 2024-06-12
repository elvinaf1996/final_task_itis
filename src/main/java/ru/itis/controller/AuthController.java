package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itis.model.dto.UserDTO;
import ru.itis.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError :allErrors){
                errors.add(objectError.getDefaultMessage());
            }
            model.addAttribute("error", String.join(", ", errors));
            return "register";
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            model.addAttribute("error", "Пароли не совпадают");
            return "register";
        }

        if (!userService.saveUser(userDTO)){
            model.addAttribute("error", "Пользователь с таким именем уже существует!");
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}