package com.petrescue.petlove.api;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(List<T> data, Meta meta) {
  public record Meta(int page,int pageSize,long total){}
  public static <T> PageResponse<T> of(Page<T> p){
    return new PageResponse<>(p.getContent(), new Meta(p.getNumber()+1, p.getSize(), p.getTotalElements()));
  }
}
