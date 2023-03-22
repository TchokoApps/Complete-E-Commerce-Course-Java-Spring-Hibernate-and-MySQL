package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@Controller
public class AuthController {

    private UserService userService;

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    @GetMapping("admin/login")
    public String login() {
        return "/admin/auth/login";
    }

    @GetMapping("admin/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/admin/auth/register";
    }

    @PostMapping("admin/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("User to register : {}", user);
        if (bindingResult.hasErrors()) {
            return "admin/auth/register";
        }

        if (user.getConfirmPassword() == null) {
            return "admin/auth/register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", null,
                    "The Password and Confirm Password fields should be identical.");
            return "admin/auth/register";
        }

        if (userService.findUserByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", null, "Email is already taken");
        }

        user.setEnabled(true);

        userService.save(user);
        addMessage(redirectAttributes, "User created successfully! Please log in.", "success");

        return "redirect:/admin";
    }

}
