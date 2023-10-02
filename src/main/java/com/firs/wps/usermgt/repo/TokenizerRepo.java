package com.firs.wps.usermgt.repo;

import com.firs.wps.usermgt.entity.Tokenizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenizerRepo extends JpaRepository<Tokenizer, Long> {
    Tokenizer findByToken(String token);
    Tokenizer findByPhoneOrEmail(String phone, String email);
    Tokenizer findByPhoneAndToken(String phone, String token);
    Tokenizer findByEmailAndToken(String email, String token);
}
