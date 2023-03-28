package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.service.BrandService;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import com.tchokoapps.springboot.ecommerce.common.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class BrandController {

    private BrandService brandService;
    private CategoryService categoryService;

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    @GetMapping("admin/brands")
    public String findAll(Model model) {

        log.info("findAll");
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "admin/brands/all";
    }

    @GetMapping("admin/brands/create")
    public String createBrandForm(Model model) {

        log.info("createBrandForm");
        Brand brand = new Brand();
        List<Category> categories = categoryService.findAllHierarchically();
        model.addAttribute("brand", brand);
        model.addAttribute("categories", categories);
        return "admin/brands/create-form";
    }

    @PostMapping("admin/brands/create")
    public String createBrand(@Valid Brand brand, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              Model model, @RequestParam(name = "image") MultipartFile multipartFile) {
        log.info("createBrand - creating brand {}", brand);

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("categories", categories);
            return "admin/brands/create-form";
        }

        final long maxFileSize = FileUtils.ONE_MB;
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() <= maxFileSize) {
                try {
                    String savedFileName = FileUploadUtil.saveFile(multipartFile);
                    brand.setPhoto(savedFileName);
                } catch (IOException e) {
                    log.error("Error happened when saving file", e);
                    addMessage(redirectAttributes, e.getMessage(), "error");
                    return "redirect:/admin/brands";
                }
            } else {
                bindingResult.rejectValue("photo", null, String.format("File size should be less or equal %s MB", maxFileSize / FileUtils.ONE_MB));
                return "admin/brands/create-form";
            }

            brandService.save(brand);
            addMessage(redirectAttributes, "Brand Created Successfully", "success");
        }

        return "redirect:/admin/brands";
    }

}
