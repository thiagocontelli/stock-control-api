package com.tc.stockcontrol.auth;

import com.tc.stockcontrol.auth.dtos.SignUpReqDTO;
import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.user.User;
import com.tc.stockcontrol.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public void signUp(SignUpReqDTO dto) {
        var encryptedPassword = encryptPassword(dto.password());

        var existingUser = userRepository.findByLogin(dto.login());

        if (existingUser != null) {
            throw new BadRequestException("user_already_exists");
        }

        userRepository.save(User.builder().name(dto.name()).login(dto.login()).password(encryptedPassword).build());
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
