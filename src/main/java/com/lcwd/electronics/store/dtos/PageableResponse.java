package com.lcwd.electronics.store.dtos;

import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {   //generic type
     private List<T> content;

     private int pageNumber;

     private int pageSize;

     private long totalElement;

     private int totalPages;

     private boolean lastPage;
}
