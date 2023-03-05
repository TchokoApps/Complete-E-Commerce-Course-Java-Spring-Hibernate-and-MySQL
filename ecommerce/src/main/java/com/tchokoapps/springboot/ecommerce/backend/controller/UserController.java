package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.service.RoleService;
import com.tchokoapps.springboot.ecommerce.backend.service.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import com.tchokoapps.springboot.ecommerce.common.Message;
import com.tchokoapps.springboot.ecommerce.common.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        log.info("createUser() :: User = {}", user);
        if (bindingResult.hasErrors()) {
            final List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "admin/users/create-form";
        }
        userService.save(user);
        Message message = new Message("User has been CREATED successfully.", MessageType.getCssClass(MessageType.SUCCESS));
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/users";

    }

    @PostMapping("admin/users/edit")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
            throws UserNotFoundException {

        log.info("editUser() :: User: {}", user);

        if (bindingResult.hasErrors()) {
            List<String> fields = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .filter(s -> !List.of("email", "password").contains(s))
                    .toList();

            if (fields.size() != 0) {
                final List<Role> roles = roleService.findAll();
                model.addAttribute("roles", roles);
                return "admin/users/edit-form";
            }
        }
        User userFound = userService.findUserById(user.getId());
        userFound.setEmail(user.getEmail());
        userFound.setLastName(user.getLastName());
        userFound.setFirstName(user.getFirstName());
        userFound.setRoles(user.getRoles());
        userFound.setEnabled(user.isEnabled());
        userService.save(userFound);
        Message message = new Message("User has been UPDATED successfully.", MessageType.getCssClass(MessageType.SUCCESS));
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit-form/{id}")
    public String editUserForm(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findUserById(id);
            final List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "/admin/users/edit-form";
        } catch (UserNotFoundException e) {
            Message message = new Message(e.getMessage(), MessageType.getCssClass(MessageType.DANGER));
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            log.info("deleteUser() :: User with id = {} deleted successfully", id);
            Message message = new Message("User has been DELETED successfully.", MessageType.getCssClass(MessageType.DANGER));
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/admin/users";

        } catch (UserNotFoundException e) {
            Message message = new Message(e.getMessage(), MessageType.getCssClass(MessageType.DANGER));
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/admin/users";
        }
    }
}

