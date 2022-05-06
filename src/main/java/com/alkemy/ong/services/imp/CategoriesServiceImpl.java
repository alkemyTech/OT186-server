package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.entity.Categories;
import com.alkemy.ong.mapper.CategoriesMapper;
import com.alkemy.ong.repository.CategoriesRepository;
import com.alkemy.ong.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final String pattern="localhost:8080/categories?page=";
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

    public void delete(UUID id) {
        Optional<Categories> result = categoriesRepository.findById(id);
        if (result.isPresent()) {
            categoriesRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Category not found");
        }
    }

    @Override
    public PageFormatter<CategoriesBasicDTO> findPageable(Pageable pageable) {
        Page<Categories> categories = categoriesRepository.findAll(pageable);
        if(categories.isEmpty()){
            throw new EntityNotFoundException("Categories not found");
        }
        else {
            Page<CategoriesBasicDTO> categoriesBasicDTOS = categories.map(entity -> categoriesMapper.EntityToCategoriesBasicDTO(entity));
            PageFormatter<CategoriesBasicDTO> pageFormatter = new PageFormatter<>();
            pageFormatter.setPageContent(categoriesBasicDTOS.getContent());
            if (categoriesBasicDTOS.getNumber() > 0) {
                pageFormatter.setPreviousPageUrl(this.pattern + (categoriesBasicDTOS.getNumber() - 1));
            } else {
                pageFormatter.setPreviousPageUrl("void");
            }
            if(categoriesBasicDTOS.getNumber()<categoriesBasicDTOS.getTotalPages()-1) {
                pageFormatter.setNextPageUrl(this.pattern + (categoriesBasicDTOS.getNumber() + 1));
            } else {
                pageFormatter.setNextPageUrl("void");
            }

            return pageFormatter;
        }
    }


}
