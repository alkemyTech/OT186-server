package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {

    public MemberDTO member2DTO(Member member) {

        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setDescription(member.getDescription());
        dto.setImage(member.getImage());
        dto.setFacebookUrl(member.getFacebookUrl());
        dto.setInstagramUrl(member.getInstagramUrl());
        dto.setLinkedinUrl(member.getLinkedinUrl());
        return dto;
    }

    public List<MemberDTO> entity2DTOList(List<Member> memberList) {

        List<MemberDTO> dtoList = new ArrayList<>();
        for (Member member : memberList) {
            dtoList.add(this.member2DTO(member));
        }
        return dtoList;
    }

    public Member updateDTO2entity(Member member, MemberDTO updated){

        member.setName(updated.getName());
        member.setDescription(updated.getDescription());
        member.setImage(updated.getImage());
        member.setFacebookUrl(updated.getFacebookUrl());
        member.setLinkedinUrl(updated.getLinkedinUrl());
        member.setInstagramUrl(updated.getInstagramUrl());
        return member;
    }

    public Member memberDTO2MemberEntity(MemberDTO dto) {
        Member entity = new Member();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        entity.setFacebookUrl(dto.getFacebookUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setLinkedinUrl(dto.getLinkedinUrl());
        return entity;
    }
}