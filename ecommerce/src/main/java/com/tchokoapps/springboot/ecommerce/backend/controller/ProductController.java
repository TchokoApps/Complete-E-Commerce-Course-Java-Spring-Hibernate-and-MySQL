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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
