package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.WithPaginationResDTO;
import com.tc.stockcontrol.dtos.WithPaginationResMapper;
import com.tc.stockcontrol.dtos.product.*;
import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.errors.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReqMapper productReqMapper;
    private final ProductResMapper productResMapper;
    private final WithPaginationResMapper withPaginationResMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductReqMapper productReqMapper, ProductResMapper productResMapper, WithPaginationResMapper withPaginationResMapper) {
        this.productRepository = productRepository;
        this.productReqMapper = productReqMapper;
        this.productResMapper = productResMapper;
        this.withPaginationResMapper = withPaginationResMapper;
    }

    public WithPaginationResDTO<List<ProductResDTO>> list(Integer page, Integer size) {
        Page<Product> productsWithPageData = productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));

        List<Product> products = productsWithPageData.toList();

        List<ProductResDTO> productsResDTO = new ArrayList<>(products.size());

        products.forEach(product -> {
            productsResDTO.add(productResMapper.toDTO(product));
        });

        return withPaginationResMapper.toDTO(productsResDTO, productsWithPageData.getNumber(), productsWithPageData.getTotalPages(), productsWithPageData.getTotalElements());
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
