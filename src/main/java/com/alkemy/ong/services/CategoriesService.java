package com.alkemy.ong.services;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;

import java.util.List;
import java.util.UUID;

public interface CategoriesService {

    List<CategoriesBasicDTO> getBasicDTOList();
    List<CategoriesDTO> getAllCategories();
    CategoriesDTO getDetailsById(UUID id);
    CategoriesDTO save(CategoriesDTO dto);
    CategoriesDTO update(UUID id, CategoriesDTO dto);
    void delete(UUID id);

}
