package com.tc.stockcontrol.product;

import com.tc.stockcontrol.dtos.WithPaginationResDTO;
import com.tc.stockcontrol.dtos.WithPaginationResMapper;
import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.errors.ConflictException;
import com.tc.stockcontrol.errors.RecordNotFoundException;
import com.tc.stockcontrol.product.dtos.ProductReqDTO;
import com.tc.stockcontrol.product.dtos.ProductReqMapper;
import com.tc.stockcontrol.product.dtos.ProductResDTO;
import com.tc.stockcontrol.product.dtos.ProductResMapper;
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
        if (barCodeExists(dto.barCode())) {
            throw new ConflictException("barCode_already_exist");
        }

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
        if (barCodeExists(dto.barCode())) {
            throw new ConflictException("barCode_already_exist");
        }

        return productRepository.findById(id).map(record -> {
            record.setName(dto.name());
            record.setCategory(productReqMapper.convertValueToCategory(dto.category()));
            record.setPrice(dto.price());
            record.setQuantity(dto.quantity());
            record.setBarCode(dto.barCode());
            record.setExpirationDate(dto.expirationDate());
            Product updated = productRepository.save(record);
            return productResMapper.toDTO(updated);
        }).orElseThrow(RecordNotFoundException::new);
    }

    public ProductResDTO listOneByBarCode(String barCode) {
        Product product = productRepository.findByBarCode(barCode);

        if (product == null) {
            throw new RecordNotFoundException();
        }

        return productResMapper.toDTO(product);
    }

    private boolean barCodeExists(String barCode) {
        Product existingBarCode = productRepository.findByBarCode(barCode);

        return existingBarCode != null;
    }
}
