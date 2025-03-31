package com.finalProject.back.repository;

import com.finalProject.back.domain.Member;
import com.finalProject.back.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest(){
        for(int i = 0; i < 10; i++){
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .pw(passwordEncoder.encode("password" + i))
                    .nickname("nick" + i)
                    .build();
            member.addRole(MemberRole.USER);
            if(i > 5){
                member.addRole(MemberRole.MANAGER);
            }
            if(i > 8){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        }//end for
    }

    @Test
    public void selectTest(){
        String email = "user9@gmail.com";
        Member member = memberRepository.getWithRoles(email);
        log.info(member);
        log.info(member.getMemberRoleList());

    }
}
