package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.BrandService;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import com.tchokoapps.springboot.ecommerce.backend.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class ProductController {

    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;

    private static void addMessage(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertType", alertType);
    }

    @GetMapping("admin/products")
    public String findAll(Model model) {
        log.info("findAll - Display all products");
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/all";
    }

    @GetMapping("admin/products/{id}/enabled/{status}")
    public String enableOrDisableProductStatus(@PathVariable(name = "id") Integer id,
                                               @PathVariable(name = "status") boolean status,
                                               RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.findById(id);
            product.setEnabled(status);
            productService.save(product);
            String newStatus = status ? "Enabled" : "Disabled";
            addMessage(redirectAttributes, String.format("Product Status changed to %s", newStatus), "success");
            log.info("enableOrDisableProductStatus -  Product Status with id {} changed to {}", id, newStatus);
        } catch (ProductNotFoundException e) {
            addMessage(redirectAttributes, String.format("Product with id = %s doesn´t exist", id), "error");
        }

        return "redirect:/admin/products";
    }

    @GetMapping("admin/products/create")
    public String createProductForm(Model model) {
        Product product = new Product();
        List<Brand> brands = brandService.findAllByOrderByName();
        List<Category> categories = categoryService.findAllHierarchically();
        model.addAttribute("product", product);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "admin/products/create-form";
    }

    @PostMapping("admin/products/create")
    public String createProduct(@Valid Product product, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {

        log.info("createProduct - creating Product {}", product);

        if (bindingResult.hasErrors()) {
            List<Brand> brands = brandService.findAllByOrderByName();
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("brands", brands);
            model.addAttribute("categories", categories);
            return "admin/products/create-form";
        }

        try {
            productService.findByName(product.getName());
            bindingResult.rejectValue("name", null, String.format("Product name %s exist already", product.getName()));
        } catch (ProductNotFoundException ignored) {

        }

        if (StringUtils.isBlank(product.getAlias())) {
            String alias = removeWhitespaceAndNonAlphanumericCharacters(product.getName());
            product.setAlias(alias);
        } else {
            String alias = removeWhitespaceAndNonAlphanumericCharacters(product.getAlias());
            product.setAlias(alias);
        }

        product.setCreatedTime(LocalDateTime.now());
        product.setUpdatedTime(LocalDateTime.now());

        try {
            Product productSaved = productService.save(product);
            log.info("createProduct - Product saved: {}", productSaved);
            addMessage(redirectAttributes, "Product Created Successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        log.info("deleteProduct - Product with id {} deleted successfully", id);
        addMessage(redirectAttributes, "Product Deleted Successfully", "success");
        return "redirect:/admin/products";
    }

    private String removeWhitespaceAndNonAlphanumericCharacters(String text) {
        return text.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9-]", "");
    }
}
