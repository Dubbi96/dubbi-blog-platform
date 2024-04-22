package com.dubbi.blogplatform.oneliner.application.service;

import com.dubbi.blogplatform.oneliner.application.dto.CreateOnelinerDto;
import com.dubbi.blogplatform.oneliner.domain.vo.OnelinerVo;
import org.springframework.stereotype.Service;

@Service
public interface OnelinerService {
    OnelinerVo createOneliner(CreateOnelinerDto createOnelinerDto);
}
