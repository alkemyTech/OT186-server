package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.entity.Categories;
import com.alkemy.ong.mapper.CategoriesMapper;
import com.alkemy.ong.repository.CategoriesRepository;
import com.alkemy.ong.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    public List<CategoriesBasicDTO> getBasicDTOList() {
        List<Categories> categories = categoriesRepository.findAll();
        List<CategoriesBasicDTO> result = categoriesMapper.categoriesBasicDTOList(categories);
        return result;
    }

    public List<CategoriesDTO> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        List<CategoriesDTO> result = categoriesMapper.categoriesDTOS(categories) ;
        return result;
    }

    public List<CategoriesBasicDTO> getAllBasicCategories(){
        List<Categories> categories = categoriesRepository.findAll();
        List<CategoriesBasicDTO> result = categoriesMapper.categoriesBasicDTOList(categories);
        return result;
    }

    public CategoriesDTO getDetailsById(UUID id) {
        Optional<Categories> categories = categoriesRepository.findById(id);
        if(categories.isPresent()){
            CategoriesDTO result = categoriesMapper.categories2DTO(categories.get());
            return result;
        }else{
            throw new EntityNotFoundException("Category not found");
        }
    }

    public CategoriesDTO save(CategoriesDTO dto) {
        Categories entitySaved = categoriesRepository.save(categoriesMapper.categoriesDTO2Entity(dto));
        CategoriesDTO result = categoriesMapper.categories2DTO(entitySaved);
        return result;
    }

    public CategoriesDTO update(UUID id, CategoriesDTO dto) {
        Optional<Categories> result = categoriesRepository.findById(id);
        if (result.isPresent()) {
            Categories entity = categoriesMapper.updateDTO2Entity(result.get(), dto);
            Categories entityUpdated = categoriesRepository.save(entity);
            CategoriesDTO dtoUpdated = categoriesMapper.categories2DTO(entityUpdated);
            return dtoUpdated;
        } else {
            throw new EntityNotFoundException("Category not found");
        }
    }

}
