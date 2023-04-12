package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.exception.CategoryNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import com.tchokoapps.springboot.ecommerce.common.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.FileUtils.ONE_MB;

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
    public String createCategoryForm(Model model) {
        List<Category> categories = categoryService.findAllHierarchically();
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "admin/categories/create-form";
    }

    @PostMapping("admin/categories/create")
    public String createCategory(@Valid Category category, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Model model,
                                 @RequestParam(name = "image") MultipartFile multipartFile) throws IOException {
        log.info("createCategory() :: create Category {}", category);

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("categories", categories);
            return "admin/categories/create-form";
        }

        final long maxFileSize = FileUtils.ONE_MB;
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                String savedFileName = FileUploadUtil.saveFile(multipartFile);
                category.setPhoto(savedFileName);
            } else {
                bindingResult.rejectValue("photo", null,
                        String.format("File size should be less or equal %s MB", maxFileSize / FileUtils.ONE_MB));
                return "admin/categories/create-form";
            }
        }

        try {
            categoryService.save(category);
            addMessage(redirectAttributes, "Category Created Successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/categories";

    }

    @GetMapping("admin/categories/edit/{id}")
    public String editCategoryForm(@PathVariable(name = "id") Integer id,
                                   Model model, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findById(id);
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("category", category);
            model.addAttribute("categories", categories);
            return "admin/categories/edit-form";
        } catch (CategoryNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/categories";
        }
    }

    @PostMapping("admin/categories/edit")
    public String editCategory(Category category, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam(name = "image") MultipartFile multipartFile) throws IOException {
        log.info("editCategory - Category {}", category);

        final long maxFileSize = ONE_MB;
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                String savedFileName = FileUploadUtil.saveFile(multipartFile);
                category.setPhoto(savedFileName);
            } else {
                bindingResult.rejectValue("photo", null,
                        String.format("File size should be less or equal %s MB", maxFileSize / ONE_MB));
                return "admin/categories/edit-form";
            }
        }

        try {
            Category categoryFound = categoryService.findById(category.getId());

            if (StringUtils.isNotBlank(category.getName())) {
                categoryFound.setName(category.getName());
            }

            if (StringUtils.isNotBlank(category.getAlias())) {
                categoryFound.setAlias(category.getAlias());
            }

            if (StringUtils.isNotBlank(category.getPhoto())) {
                if (StringUtils.isNotBlank(categoryFound.getPhoto())) {
                    FileUploadUtil.deleteQuietly(categoryFound.getPhoto());
                }
                categoryFound.setPhoto(category.getPhoto());
            }

            if (category.getParent() != null) {
                categoryFound.setParent(category.getParent());
            }

            categoryFound.setEnabled(category.isEnabled());

            categoryService.save(categoryFound);
            addMessage(redirectAttributes, "Category Updated Successfully", "success");
        } catch (CategoryNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        log.info("deleteCategory - Deleting category (id={})", id);

        try {

            Category category = categoryService.findById(id);

            if (!category.getChildren().isEmpty()) {
                addMessage(redirectAttributes, "This category has one or more subcategories. Please delete the sub categories before you delete this one", "error");
                return "redirect:/admin/categories";
            }

            categoryService.delete(id);

            if (StringUtils.isNotBlank(category.getPhoto())) {
                FileUploadUtil.deleteQuietly(category.getPhoto());
            }

            addMessage(redirectAttributes, "Category deleted successfully", "success");

            log.info("deleteCategory - Category (id={}) deleted successfully", id);

        } catch (CategoryNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }

        return "redirect:/admin/categories";

    }

    @GetMapping("/admin/categories/{id}/enabled/{status}")
    public String enableOrDisableCategoryStatus(@PathVariable(name = "id") Integer id,
                                                @PathVariable(name = "status") boolean status,
                                                RedirectAttributes redirectAttributes) {
        try {
            final Category categoryFound = categoryService.findById(id);
            categoryFound.setEnabled(status);
            categoryService.save(categoryFound);
            final String newStatus = status ? "Enabled" : "Disabled";

            addMessage(redirectAttributes, String.format("Category %s successfully", newStatus), "success");
            log.info("enableOrDisableCategoryStatus() :: Category {} status changed to {}", categoryFound.getId(), newStatus);

        } catch (CategoryNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }
        return "redirect:/admin/categories";
    }
}

