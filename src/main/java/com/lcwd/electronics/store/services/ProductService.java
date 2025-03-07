package com.lcwd.electronics.store.services;

import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
     void delete(String productId);

    //get single
    ProductDto getSingle(String productId);

    //get all
    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get all :Live
    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    //search product

    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

    //create products with category

    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

    //get all category of product
    PageableResponse<ProductDto> getAllCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}
