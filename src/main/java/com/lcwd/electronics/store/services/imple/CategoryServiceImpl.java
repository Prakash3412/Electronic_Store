package com.lcwd.electronics.store.services.imple;

import com.lcwd.electronics.store.dtos.CategoryDto;
import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.entities.Category;
import com.lcwd.electronics.store.exception.ResourceNotFoundException;
import com.lcwd.electronics.store.helper.Helper;
import com.lcwd.electronics.store.repositories.CategoryRepository;
import com.lcwd.electronics.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        //create categoryId: randomly help of UUID class

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        //using the modelMapper convert Dto to Entity and Entity to Dto
        Category category = modelMapper.map(categoryDto, Category.class);

        //create categoryId: randomly help of UUID class


        Category savedCategory = categoryRepository.save(category); //save() jo h entity se call hota h isiliye category me change kiye
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //get category of given id

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found given id"));

        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        //update
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found given id!!"));
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found given id!!"));
        return modelMapper.map(category,CategoryDto.class);
    }
}
