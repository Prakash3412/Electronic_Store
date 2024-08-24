package com.lcwd.electronics.store.helper;

import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.UserDto;
import com.lcwd.electronics.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static<U,V> PageableResponse<V> getPageableResponse(Page<U> page ,Class<V> type) // U->UserEntity V->UserDto
    {

        List<U> entity = page.getContent();      //called getContain then we will get List    //findAll method contain pageable

        //convert to entity to dto list<UserDto>

        List<V> dtoList = entity.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V>  response = new PageableResponse<>();     //create object of PageablResponse clas

        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
