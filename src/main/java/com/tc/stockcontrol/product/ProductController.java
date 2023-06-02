package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.WithPaginationResDTO;
import com.tc.stockcontrol.product.dtos.ProductReqDTO;
import com.tc.stockcontrol.product.dtos.ProductResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public WithPaginationResDTO<List<ProductResDTO>> getAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "30") Integer size) {
        return productService.list(page, size);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductResDTO addProduct(@RequestBody @Validated ProductReqDTO dto) {
        return productService.add(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResDTO updateProduct(@PathVariable String id, @RequestBody @Validated ProductReqDTO dto) {
        return productService.update(id, dto);
    }

    @GetMapping("/{barCode}")
    public ProductResDTO getOneByBarCode(@PathVariable String barCode) {
        return productService.listOneByBarCode(barCode);
    }
}
