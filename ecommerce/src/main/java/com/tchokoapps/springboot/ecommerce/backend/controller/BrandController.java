package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.exception.BrandNotFoundExcepion;
import com.tchokoapps.springboot.ecommerce.backend.service.BrandService;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import com.tchokoapps.springboot.ecommerce.common.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        List<Brand> brands = brandService.findAllByOrderByCreatedTimeDesc();
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
    public String createBrand(@Valid Brand brand, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model,
                              @RequestParam(name = "image") MultipartFile multipartFile) throws IOException {
        log.info("createBrand - creating brand {}", brand);
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("categories", categories);
            return "admin/brands/create-form";
        }

        try {
            brandService.findByName(brand.getName());
            bindingResult.rejectValue("name", "brand.name.already.exist",
                    String.format("Brand name %s exist already", brand.getName()));
            return "admin/brands/create-form";
        } catch (BrandNotFoundExcepion ignored) {

        }

        if (!multipartFile.isEmpty()) {
            String savedFileName = FileUploadUtil.saveFile(multipartFile);
            brand.setPhoto(savedFileName);
        }

        brandService.save(brand);
        addMessage(redirectAttributes, "Brand Created Successfully", "success");

        return "redirect:/admin/brands";
    }

    @GetMapping("admin/brands/update/{id}")
    public String updateBrandForm(@PathVariable(name = "id") Integer id,
                                  Model model, RedirectAttributes redirectAttributes) {
        try {
            Brand brand = brandService.findById(id);
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("brand", brand);
            model.addAttribute("categories", categories);
            return "admin/brands/update-form";

        } catch (BrandNotFoundExcepion e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/brands";
        }
    }

    @PostMapping("/admin/brands/update")
    public String updateBrand(Brand brand, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(name = "image") MultipartFile multipartFile) {

        log.info("updateBrand - Brand to update {}", brand);

        try {

            if (!multipartFile.isEmpty()) {
                String savedFileName = FileUploadUtil.saveFile(multipartFile);
                brand.setPhoto(savedFileName);
            }

            Brand brandFound = brandService.findById(brand.getId());

            if (StringUtils.isNotBlank(brand.getName())) {
                brandFound.setName(brand.getName());
            }

            if (StringUtils.isNotBlank(brand.getPhoto())) {
                if (StringUtils.isNotBlank(brandFound.getPhoto())) {
                    FileUploadUtil.deleteQuietly(brandFound.getPhoto());
                }
                brandFound.setPhoto(brand.getPhoto());
            }

            brandService.save(brandFound);
            addMessage(redirectAttributes, "Brand Updated Successfully", "success");

        } catch (BrandNotFoundExcepion | IOException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }

        return "redirect:/admin/brands";
    }

    @GetMapping("admin/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        log.info("deleteBrand - deleting Brand(id={})", id);

        try {
            brandService.delete(id);
        } catch (BrandNotFoundExcepion e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/brands";
        }

        addMessage(redirectAttributes, "Brand Deleted Successfully", "success");

        return "redirect:/admin/brands";
    }

}
