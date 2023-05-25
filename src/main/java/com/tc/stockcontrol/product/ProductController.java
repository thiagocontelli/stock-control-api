package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.product.ProductReqDTO;
import com.tc.stockcontrol.dtos.product.ProductResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResDTO> getAllProducts() {
        return productService.list();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductResDTO addProduct(@RequestBody ProductReqDTO dto) {
        return productService.add(dto);
    }
}
