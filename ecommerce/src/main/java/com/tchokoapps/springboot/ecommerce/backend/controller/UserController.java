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

    @GetMapping("admin/users")
    String findAll(Model model) {
        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users/index";
    }

    @GetMapping("admin/users/create-form")
    public String createUserForm(Model model) {
        final List<Role> roles = roleService.findAll();
        final User user = new User();
        user.setEnabled(false);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/users/create-form";
    }

    @PostMapping("admin/users/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("User = {}", user);
        if (bindingResult.hasErrors()) {
            final List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "admin/users/create-form";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "User has been saved successfully.");
        return "redirect:/admin/users";

    }

    @PostMapping("admin/users/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes)
            throws UserNotFoundException {

        log.info("user: {}", user);

        if (bindingResult.hasErrors()) {

            if (bindingResult.getErrorCount() > 1) {
                return "admin/users/create-form";
            }

            final String fieldName = bindingResult.getFieldErrors().get(0).getField();

            if (!fieldName.equals("email")) {
                return "admin/users/create-form";
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
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findUserById(id);
            final List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User with id = " + user.getId());
            model.addAttribute("roles", roles);
            return "/admin/users/edit-form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/users";
        }
    }
}

