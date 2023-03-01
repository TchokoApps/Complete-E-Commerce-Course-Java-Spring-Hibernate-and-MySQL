package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.service.RoleService;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("backend/users")
    String findAll(Model model) {

        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "backend/users/index";
    }

    @GetMapping("backend/users/create")
    public String createUser(Model model) {

        final List<Role> roles = roleService.findAll();
        final User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "backend/users/create-form";
    }

    @PostMapping("backend/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        log.info("user: {}", user);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/backend/users";
    }

    @PostMapping("/backend/users/check_email")
    public String checkDuplicatedEmail(@Param("email") String email) {
        return userService.isUserUnique(email) ? "OK" : "Duplicated";
    }
}
