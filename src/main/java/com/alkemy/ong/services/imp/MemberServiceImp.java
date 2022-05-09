package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.entity.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberServiceImp implements MemberService {

    private final String pattern="localhost:8080/member?page=";
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;

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

    public void delete(UUID id) {
        this.memberRepository.deleteById(id);
    }

    public MemberDTO create(MemberDTO memberDTO) {
        try {
            Member member = memberMapper.memberDTO2MemberEntity(memberDTO);
            Member savedMemberEntity = memberRepository.save(member);
            return memberMapper.member2DTO(savedMemberEntity);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public PageFormatter<MemberDTO> findPageable(Pageable pageable){
            Page<Member> members = memberRepository.findAll(pageable);
            if(members.isEmpty()){
                throw new EntityNotFoundException("Member not found");
            }else{
                Page<MemberDTO> memberDTOS = members.map(entity -> memberMapper.member2DTO(entity));
                PageFormatter<MemberDTO> pageFormatter = new PageFormatter<>();
                pageFormatter.setPageContent(memberDTOS.getContent());
                if(memberDTOS.getNumber() > 0){
                    pageFormatter.setPreviousPageUrl(this.pattern + (memberDTOS.getNumber() - 1));
                }else{
                    pageFormatter.setPreviousPageUrl("void");
                }
                if(memberDTOS.getNumber()<memberDTOS.getTotalPages()-1){
                    pageFormatter.setNextPageUrl(this.pattern + (memberDTOS.getNumber() + 1));
                }else{
                    pageFormatter.setNextPageUrl("void");
                }
                return pageFormatter;
            }
    }
}