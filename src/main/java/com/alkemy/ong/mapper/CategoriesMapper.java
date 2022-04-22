package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.entity.Categories;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesMapper {

    public Categories categoriesDTO2Entity(CategoriesDTO dto){
        Categories categories = new Categories();
        categories.setName(dto.getName());
        categories.setDescription(dto.getDescription());
        categories.setImage(dto.getImage());
        return categories;
    }

    public CategoriesDTO categories2DTO(Categories entity){
        CategoriesDTO dto = new CategoriesDTO();
        dto.setName(entity.getName());
	dto.setDescripyion(entity.getDescrition());
	dto.setImage(entity.getImage());
        return dto;
    }

    public List<CategoriesBasicDTO> categoriesBasicDTOList(List<Categories> entities){
        List<CategoriesBasicDTO> dtos = new ArrayList<>();
        CategoriesBasicDTO basicDTO;
        for(Categories entity : entities){
            basicDTO = new CategoriesBasicDTO();
            basicDTO.setName(entity.getName());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public List<CategoriesDTO> categoriesDTOS(List<Categories> entities){
        List<CategoriesDTO> dtos = new ArrayList<>();
        CategoriesDTO basicDTO;
        for(Categories entity : entities){
            basicDTO = new CategoriesDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setName(entity.getName());
            basicDTO.setDescription(entity.getDescription());
            basicDTO.setImage(entity.getImage());
            dtos.add(basicDTO);
        }
        return dtos;
    }



}
