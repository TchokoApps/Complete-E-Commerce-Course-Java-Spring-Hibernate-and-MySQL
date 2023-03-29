package com.tchokoapps.springboot.ecommerce.common.validator;

import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UserUniqueEmailValidator implements ConstraintValidator<UserUniqueEmail, String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository == null) {
            return true;
        }
        return userRepository.findUserByEmail(email).isEmpty();
    }
}
