package com.alkemy.ong.services;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.PageFormatter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MemberService {
    
    MemberDTO update(UUID id, MemberDTO updated);
    void delete(UUID id);
    MemberDTO create(MemberDTO memberDTO);
    public PageFormatter<MemberDTO> findPageable(Pageable pageable);
}
