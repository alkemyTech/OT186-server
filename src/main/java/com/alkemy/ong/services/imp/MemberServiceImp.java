package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}