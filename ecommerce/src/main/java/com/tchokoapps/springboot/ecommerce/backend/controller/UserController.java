package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.service.RoleService;
import com.tchokoapps.springboot.ecommerce.backend.service.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @GetMapping("admin")
    String showAdminPage(Model model) {
        return "admin/index";
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
        model.addAttribute("pageTitle", "Create New User");
        return "backend/users/create-form";
    }

    @PostMapping("backend/users/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws UserNotFoundException {

        log.info("user: {}", user);

        if (bindingResult.hasErrors()) {

            if (bindingResult.getErrorCount() > 1) {
                return "backend/users/create-form";
            }

            final String fieldName = bindingResult.getFieldErrors().get(0).getField();

            if (!fieldName.equals("email")) {
                return "backend/users/create-form";
            }
        }

        User userFound = userService.findUserById(user.getId());
        userFound.setEmail(user.getEmail());
        userFound.setLastName(user.getLastName());
        userFound.setFirstName(user.getFirstName());
        if (!user.getPassword().isBlank()) {
            userFound.setPassword(user.getPassword());
        }
        userService.save(userFound);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/backend/users";
    }

    @GetMapping("/backend/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findUserById(id);
            final List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User with id = " + user.getId());
            model.addAttribute("roles", roles);
            return "/backend/users/edit-form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/backend/users";
        }
    }
}

