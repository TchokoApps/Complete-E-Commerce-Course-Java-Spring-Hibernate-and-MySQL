package com.tchokoapps.springboot.ecommerce.common.password;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String rawPassword = "password";
        final String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("encodedPassword = {}", encodedPassword);
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        Assertions.assertThat(matches).isTrue();

    }
}