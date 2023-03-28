package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.auth.DefaultUserDetails;
import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.exception.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.RoleService;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import com.tchokoapps.springboot.ecommerce.common.fileexporter.UserCsvFileExporter;
import com.tchokoapps.springboot.ecommerce.common.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.commons.io.FileUtils.ONE_MB;

@Slf4j
@Controller
@AllArgsConstructor
public class UserController {

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 64;
    private UserService userService;
    private RoleService roleService;
    private UserCsvFileExporter userCsvFileExporter;

    private PasswordEncoder passwordEncoder;

    @GetMapping("admin/users")
    public String findAll(Model model) {
        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users/index";
    }

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    @GetMapping("admin/users/create")
    public String createUserForm(Model model) {
        final List<Role> roles = roleService.findAll();
        final User user = new User();
        user.setEnabled(false);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/users/create-form";
    }

    @PostMapping("admin/users/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes
            redirectAttributes, Model model, @RequestParam(name = "image") MultipartFile multipartFile) {
        log.info("createUser() :: User = {}", user);

        if (bindingResult.hasErrors()) {
            final List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "admin/users/create-form";
        }
        String email = user.getEmail();
        try {
            userService.findUserByEmail(email);
            bindingResult.rejectValue("email", null, "Email already exist");
            return "admin/users/create-form";
        } catch (UserNotFoundException ignored) {

        }

        final long maxFileSize = ONE_MB;
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                try {
                    String savedFileName = FileUploadUtil.saveFile(multipartFile);
                    user.setPhoto(savedFileName);
                } catch (IOException e) {
                    log.error("Error saving file", e);
                    addMessage(redirectAttributes, e.getMessage(), "error");
                    return "redirect:/admin/users";
                }
            } else {
                bindingResult.rejectValue("photo", null, String.format("File size should be less or equal %s MB", maxFileSize / ONE_MB));
                return "admin/users/create-form";
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        addMessage(redirectAttributes, "User CREATED successfully", "success");
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUserForm(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findUserById(id);
            final List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "admin/users/edit-form";
        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/users";
        }
    }

    @PostMapping("admin/users/edit")
    public String editUser(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam(name = "image") MultipartFile multipartFile) {

        log.info("editUser() :: User: {}", user);

        final long maxFileSize = ONE_MB;
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                try {
                    String savedFileName = FileUploadUtil.saveFile(multipartFile);
                    user.setPhoto(savedFileName);
                } catch (IOException e) {
                    log.error("Error saving file", e);
                    addMessage(redirectAttributes, e.getMessage(), "error");
                    return "redirect:/admin/users";
                }
            } else {
                bindingResult.rejectValue("photo", null, String.format("File size should be less or equal %s MB", maxFileSize / ONE_MB));
                return "admin/users/edit-form";
            }
        }

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

            addMessage(redirectAttributes, String.format("User %s successfully", newStatus), "success");
            log.info("enableUser() :: User {} status changed to {}", userFound.getId(), newStatus);

        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/admin/users/csv")
    public void exportToCsv(HttpServletResponse response) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateAndTime = dateFormat.format(new Date());
        String fileName = "users_" + dateAndTime + ".csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            userCsvFileExporter.exportToCsv(userService.findAll(), response.getWriter());
        } catch (IOException e) {
            log.error("Export file to CSV FAILED.", e);
        }
    }

    @GetMapping("admin/users/profile")
    public String showProfilePage(@Valid Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        log.info("showProfilePage() :: username = {}", defaultUserDetails.getUsername());
        String email = defaultUserDetails.getUsername();
        try {
            User user = userService.findUserByEmail(email);
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin";
        }
        return "admin/users/profile";
    }

    @PostMapping("admin/users/profile")
    public String editProfilePage(User user, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam("image") MultipartFile multipartFile, @AuthenticationPrincipal DefaultUserDetails defaultUserDetails) {
        log.info("editProfilePage() :: User: {}", user);

        final long maxFileSize = ONE_MB;

        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                try {
                    String savedFileName = FileUploadUtil.saveFile(multipartFile);
                    user.setPhoto(savedFileName);
                } catch (IOException e) {
                    log.error("Error saving file", e);
                    addMessage(redirectAttributes, e.getMessage(), "error");
                    return "redirect:/admin";
                }
            } else {
                bindingResult.rejectValue("photo", null, String.format("File size should be less or equal %s MB", maxFileSize / ONE_MB));
                return "admin/users/profile";
            }
        }

        try {
            User userFound = userService.findUserById(user.getId());
            userFound.setEmail(user.getEmail());
            userFound.setFirstName(user.getFirstName());
            userFound.setLastName(user.getLastName());
            if (user.getPhoto() != null) {
                userFound.setPhoto(user.getPhoto());
            }

            if (StringUtils.isNotBlank(user.getPassword())) {

                if (!user.getPassword().equals(user.getConfirmPassword())) {
                    bindingResult.rejectValue("password", null, "The Password and Confirm Password fields should be identical.");
                    bindingResult.rejectValue("confirmPassword", null, "The Password and Confirm Password fields should be identical.");
                    return "admin/users/profile";
                }

                if (user.getPassword().length() < MIN_LENGTH) {
                    bindingResult.rejectValue("password", null, "The Password cannot be less than 8 characters.");
                    return "admin/users/profile";
                }

                if (user.getPassword().length() > MAX_LENGTH) {
                    bindingResult.rejectValue("password", null, "The Password cannot exceed 64 characters.");
                    return "admin/users/profile";
                }

                userFound.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            defaultUserDetails.setFirstName(user.getFirstName());
            defaultUserDetails.setLastName(user.getLastName());
            defaultUserDetails.setEmail(user.getEmail());

            userService.save(userFound);
            addMessage(redirectAttributes, "User profile UPDATED successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/users";
    }

}

