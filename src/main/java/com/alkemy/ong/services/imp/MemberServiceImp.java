package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberServiceImp implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public List<MemberDTO> getAll() {
        List<Member> members = memberRepository.findAll();
        return memberMapper.entity2DTOList(members);
    }

    @Override
    public MemberDTO update(UUID id, MemberDTO updated) {
        Optional<Member> member = memberRepository.findById(id);
        if (!member.isPresent()){
            throw new EntityNotFoundException("Member not found.");
        }
        Member alreadyUpdated = memberMapper.updateDTO2entity(member.get(), updated);
        memberRepository.save(alreadyUpdated);
        return memberMapper.member2DTO(alreadyUpdated);
    }
}