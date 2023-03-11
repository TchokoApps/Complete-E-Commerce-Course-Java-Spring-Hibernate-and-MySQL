package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.service.RoleService;
import com.tchokoapps.springboot.ecommerce.backend.service.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import com.tchokoapps.springboot.ecommerce.common.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.FileUtils.ONE_MB;

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

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    private static String saveImage(User user, RedirectAttributes redirectAttributes, MultipartFile multipartFile) {
        final long maxFileSize = ONE_MB;

        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                try {
                    final String savedFileName = FileUploadUtil.saveFile(multipartFile);
                    user.setPhoto(savedFileName);
                } catch (IOException e) {
                    log.error("Error saving file", e);
                    addMessage(redirectAttributes, e.getMessage(), "error");
                    return "redirect:/admin/users";
                }
            } else {
                addMessage(redirectAttributes, String.format("File size should be less or equal %s MB", maxFileSize / ONE_MB), "error");
                return "redirect:/admin/users";
            }
        }
        return null;
    }

    @GetMapping("admin/users/create")
    public String createUserForm(Model model, RedirectAttributes redirectAttributes) {
        final List<Role> roles = roleService.findAll();
        final User user = new User();
        user.setEnabled(false);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        addMessage(redirectAttributes, "User CREATED successfully", "success");

        return "admin/users/create-form";
    }

    @PostMapping("admin/users/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             Model model, @RequestParam(name = "image") MultipartFile multipartFile) {
        log.info("createUser() :: User = {}", user);

        if (bindingResult.hasErrors()) {
            final List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "admin/users/create-form";
        }

        String saveImageResult = saveImage(user, redirectAttributes, multipartFile);
        if (saveImageResult != null) return saveImageResult;

        userService.save(user);
        addMessage(redirectAttributes, "User CREATED successfully", "success");
        return "redirect:/admin/users";

    }

    @PostMapping("admin/users/edit")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           Model model, @RequestParam(name = "image") MultipartFile multipartFile) {

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

        String x = saveImage(user, redirectAttributes, multipartFile);
        if (x != null) return x;

        try {
            User userFound = userService.findUserById(user.getId());
            userFound.setEmail(user.getEmail());
            userFound.setLastName(user.getLastName());
            userFound.setFirstName(user.getFirstName());
            userFound.setRoles(user.getRoles());
            userFound.setEnabled(user.isEnabled());
            userFound.setPhoto(user.getPhoto());
            userService.save(userFound);
            addMessage(redirectAttributes, "User UPDATED successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/enabled/{status}")
    public String enableOrDisableUserStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean status, RedirectAttributes redirectAttributes) {
        try {
            final User userFound = userService.findUserById(id);
            userFound.setEnabled(status);
            userService.save(userFound);
            final String newStatus = status ? "Enabled" : "Disabled";

            addMessage(redirectAttributes, String.format("User status changed to %s", newStatus), "success");
            log.info("enableUser() :: User {} status changed to {}", userFound.getId(), newStatus);

        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUserForm(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findUserById(id);
            final List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "/admin/users/edit-form";
        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            log.info("deleteUser() :: User with id = {} deleted successfully", id);
            addMessage(redirectAttributes, "User DELETED successfully", "success");
            return "redirect:/admin/users";

        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/users";
        }
    }
}

