package com.tchokoapps.springboot.ecommerce.backend.controller;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.entity.ProductImage;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.service.BrandService;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import com.tchokoapps.springboot.ecommerce.backend.service.ProductImageService;
import com.tchokoapps.springboot.ecommerce.backend.service.ProductService;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Controller
public class ProductController {

    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;
    private ProductImageService productImageService;

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
                                RedirectAttributes redirectAttributes, Model model,
                                @RequestParam(name = "image") MultipartFile multipartFile,
                                @RequestParam(name = "moreImages") MultipartFile[] multipartFileArray,
                                @RequestParam(name = "productDetailNames", required = false) String[] productDetailNames,
                                @RequestParam(name = "productDetailValues", required = false) String[] productDetailValues) throws IOException {

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
            bindingResult.rejectValue("name", null,
                    String.format("Product name %s exist already", product.getName()));
        } catch (ProductNotFoundException ignored) {

        }

        if (!multipartFile.isEmpty()) {
            String savedFileName = FileUploadUtil.saveFile(multipartFile);
            product.setMainImage(savedFileName);
        }

        for (MultipartFile multipartFile1 : multipartFileArray) {
            String fileSaved = FileUploadUtil.saveFile(multipartFile1);
            product.addProductImage(fileSaved);
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

        createProductDetails(product, productDetailNames, productDetailValues);

        try {
            Product productSaved = productService.save(product);
            log.info("createProduct - Product saved: {}", productSaved);
            addMessage(redirectAttributes, "Product Created Successfully", "success");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
        }

        return "redirect:/admin/products";
    }

    private void createProductDetails(Product product, String[] productDetailNames, String[] productDetailValues) {
        if (productDetailNames != null && productDetailNames.length != 0
                && productDetailValues != null && productDetailValues.length == productDetailNames.length) {
            for (int i = 0; i < productDetailNames.length; i++) {
                String name = productDetailNames[i];
                String value = productDetailValues[i];
                product.addProductDetail(name, value);
            }
        }
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            Product product = productService.findById(id);

            if (product.getMainImage() != null) {
                FileUploadUtil.deleteQuietly(product.getMainImage());
            }

            Set<ProductImage> productImages = product.getProductImages();

            if (productImages != null && productImages.size() != 0) {
                productImages.forEach(productImage -> FileUploadUtil.deleteQuietly(productImage.getName()));
            }
        } catch (ProductNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/products";
        }

        productService.deleteProduct(id);
        log.info("deleteProduct - Product with id {} deleted successfully", id);
        addMessage(redirectAttributes, "Product Deleted Successfully", "success");
        return "redirect:/admin/products";
    }

    @GetMapping("admin/products/update/{id}")
    public String updateProductForm(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.findById(id);
            List<Category> categories = categoryService.findAllHierarchically();
            List<Brand> brands = brandService.findAllByOrderByName();
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            model.addAttribute("brands", brands);
            return "admin/products/update-form";

        } catch (ProductNotFoundException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "redirect:/admin/products";
        }
    }

    @PostMapping("admin/products/update")
    public String updateProduct(@Valid Product product, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model,
                                @RequestParam(name = "image") MultipartFile multipartFile,
                                @RequestParam(name = "moreImages") MultipartFile[] multipartFiles,
                                @RequestParam(name = "productDetailNames", required = false) String[] productDetailNames,
                                @RequestParam(name = "productDetailValues", required = false) String[] productDetailValues) {

        log.info("updateProduct - updating product {}", product);

        if (bindingResult.hasErrors()) {
            List<Brand> brands = brandService.findAllByOrderByName();
            List<Category> categories = categoryService.findAllHierarchically();
            model.addAttribute("brands", brands);
            model.addAttribute("categories", categories);
            return "admin/products/update-form";
        }

        try {
            Product productFound = productService.findById(product.getId());
            if (!multipartFile.isEmpty()) {
                String savedFileName = FileUploadUtil.saveFile(multipartFile);
                String mainImage = productFound.getMainImage();
                if (!StringUtils.isBlank(mainImage)) {
                    FileUploadUtil.deleteQuietly(mainImage);
                }
                product.setMainImage(savedFileName);
            }

            if (multipartFiles != null && !multipartFiles[0].isEmpty()) {

                productFound.getProductImages().forEach(productImage -> FileUploadUtil.deleteQuietly(productImage.getName()));

                productImageService.deleteAllProductImagesByProductId(productFound.getId());

                for (MultipartFile multipartFile1 : multipartFiles) {
                    String fileNameSaved = FileUploadUtil.saveFile(multipartFile1);
                    product.addProductImage(fileNameSaved);
                }
            }

            if (StringUtils.isBlank(product.getAlias())) {
                String alias = removeWhitespaceAndNonAlphanumericCharacters(product.getName());
                product.setAlias(alias);
            } else {
                String alias = removeWhitespaceAndNonAlphanumericCharacters(product.getAlias());
                product.setAlias(alias);
            }

            createProductDetails(product, productDetailNames, productDetailValues);

            Product productSaved = productService.save(product);
            log.info("createProduct - Product saved: {}", productSaved);
            addMessage(redirectAttributes, "Product Created Successfully", "success");

            return "redirect:/admin/products";
        } catch (ProductNotFoundException | IOException e) {
            addMessage(redirectAttributes, e.getMessage(), "error");
            return "admin/products/all";
        }
    }


    private String removeWhitespaceAndNonAlphanumericCharacters(String text) {
        return text.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9-]", "");
    }
}
