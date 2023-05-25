package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.product.ProductReqMapper;
import com.tc.stockcontrol.dtos.product.ProductReqDTO;
import com.tc.stockcontrol.dtos.product.ProductResDTO;
import com.tc.stockcontrol.dtos.product.ProductResMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReqMapper productReqMapper;
    private final ProductResMapper productResMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductReqMapper productReqMapper, ProductResMapper productResMapper) {
        this.productRepository = productRepository;
        this.productReqMapper = productReqMapper;
        this.productResMapper = productResMapper;
    }

    public List<ProductResDTO> list() {
        List<Product> products = productRepository.findAll();
        List<ProductResDTO> dto = new ArrayList<>(products.size());
        for (int i = 0; i < products.size(); i++) {
            dto.add(i, productResMapper.toDTO(products.get(i)));
        }
        return dto;
    }

    public ProductResDTO add(ProductReqDTO dto) {
        Product newProduct = productReqMapper.toEntity(dto);
        Product product = productRepository.save(newProduct);
        return productResMapper.toDTO(product);
    }

}
