package com.lcwd.electronics.store.services.imple;

import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.ProductDto;
import com.lcwd.electronics.store.entities.Category;
import com.lcwd.electronics.store.entities.Product;
import com.lcwd.electronics.store.exception.ResourceNotFoundException;
import com.lcwd.electronics.store.helper.Helper;
import com.lcwd.electronics.store.repositories.CategoryRepository;
import com.lcwd.electronics.store.repositories.ProductRepository;
import com.lcwd.electronics.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    //other dependency

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class); //changed to Dto to Entity

        //generate product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //added date help of Date class
        product.setAddedDate( new Date());

        Product savedProduct = productRepository.save(product);    //save method need Entity
        return  modelMapper.map(savedProduct, ProductDto.class); //changed Entity to Dto because we return to Dto form
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        //fetch the product of the given id --- if any data in this id store in the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found the given id"));

        //update product me All information h jo ki database se aaya h or isiliye product se set krenge new data or dto se get
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        //update the data help of save method and it will  return updatedData

        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct,ProductDto.class); //we need to return in form of Dto so we will change
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found the given id"));
        productRepository.delete(product);

    }

    @Override
    public ProductDto getSingle(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found the given id"));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto>getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        //fetch the category from the db ,,,,we need categoryRepository
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found !!"));

        Product product = modelMapper.map(productDto, Product.class); //changed to Dto to Entity

        //generate product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //added date help of Date class
        product.setAddedDate( new Date());

        //before save product we have to set the category in the product
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);    //save method need Entity
        return  modelMapper.map(savedProduct, ProductDto.class); //changed Entity to Dto because we return to Dto form
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        //product fetch
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product of given id not found"));
        //fetch category

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category of given id not found!!"));

        product.setCategory(category);

        //update
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category of given id not found!!"));
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        
        Page<Product> page = productRepository.findByCategory(category,pageable);
        return  Helper.getPageableResponse(page,ProductDto.class);
    }

}

