package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
}
