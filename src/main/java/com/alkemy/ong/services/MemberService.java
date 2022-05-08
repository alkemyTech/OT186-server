package com.alkemy.ong.services;

import com.alkemy.ong.dto.MemberDTO;

import java.util.List;
import java.util.UUID;

public interface MemberService {

    List<MemberDTO> getAll();
    MemberDTO update(UUID id, MemberDTO updated);
    MemberDTO create(MemberDTO memberDTO);
}
