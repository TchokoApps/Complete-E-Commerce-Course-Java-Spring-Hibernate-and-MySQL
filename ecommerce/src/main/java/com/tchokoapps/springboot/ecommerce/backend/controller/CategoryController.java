package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class CategoryController {

    public CategoryService categoryService;

    @GetMapping("admin/categories")
    public String findAll(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/index";
    }

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    @GetMapping("admin/categories/create")
    public String createCategoryForm(Model model, RedirectAttributes redirectAttributes) {
        List<Category> categories = categoryService.findAllHierarchically();
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        addMessage(redirectAttributes, "Category Created Successfully", "success");
        return "admin/categories/create-form";
    }

    @PostMapping("admin/categories/create")
    public String createCategory(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, @RequestParam(name = "image") MultipartFile multipartFile) {
        log.info("createCategory() :: create Category {}", category);

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("categories", categories);
            return "admin/categories/create-form";
        }

        try {
            categoryService.save(category);
            addMessage(redirectAttributes, "Category Created Successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/categories";

    }
}
