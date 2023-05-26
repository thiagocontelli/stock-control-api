package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.product.ProductReqDTO;
import com.tc.stockcontrol.dtos.product.ProductReqMapper;
import com.tc.stockcontrol.dtos.product.ProductResDTO;
import com.tc.stockcontrol.dtos.product.ProductResMapper;
import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.errors.RecordNotFoundException;
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
        // TODO: find a way to validate category property

        Product newProduct = productReqMapper.toEntity(dto);
        Product product = productRepository.save(newProduct);
        return productResMapper.toDTO(product);
    }

    public void delete(String id) {
        if (id.trim().isEmpty()) {
            throw new BadRequestException("id_cant_be_empty");
        }
        Product product = productRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        productRepository.delete(product);
    }

    public ProductResDTO update(String id, ProductReqDTO dto) {
        return productRepository.findById(id).map(record -> {
            record.setName(dto.name());
            record.setCategory(dto.category());
            record.setPrice(dto.price());
            record.setQuantity(dto.quantity());
            record.setBarCode(dto.barCode());
            record.setExpirationDate(dto.expirationDate());
            Product updated = productRepository.save(record);
            return productResMapper.toDTO(updated);
        }).orElseThrow(RecordNotFoundException::new);
    }
}
