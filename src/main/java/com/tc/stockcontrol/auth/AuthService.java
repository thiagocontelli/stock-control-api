package com.tc.stockcontrol.auth;

import com.tc.stockcontrol.auth.dtos.ResendValidationCodeReqDTO;
import com.tc.stockcontrol.auth.dtos.SignUpReqDTO;
import com.tc.stockcontrol.auth.dtos.ValidateEmailReqDTO;
import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.errors.RecordNotFoundException;
import com.tc.stockcontrol.errors.ServerException;
import com.tc.stockcontrol.user.User;
import com.tc.stockcontrol.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public void signUp(SignUpReqDTO dto) {
        var encryptedPassword = encryptPassword(dto.password());

        var existingUser = userRepository.findByEmail(dto.email());

        if (existingUser != null) {
            throw new BadRequestException("user_already_exists");
        }

        var user = userRepository.save(User.builder().name(dto.name()).email(dto.email()).password(encryptedPassword).isEmailValid(false).build());

        var code = sendEmailValidationCode(user.getEmail());

        user.setEmailValidationCode(code);

        userRepository.save(user);
    }

    public int sendEmailValidationCode(String to) {
        int code = new Random().nextInt(90000) + 10_000;

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String htmlMsg = "<h1>Stock Control API</h1>\n" +
                "\n" +
                "<h2>E-mail Validation</h2>\n" +
                "\n" +
                "<p>Below is the code for you to validate your email:</p>\n" +
                "\n" +
                "<strong>" + code + "</strong>\n" +
                "\n" +
                "<p>If you weren't the one who made the request to validate the email, please ignore this message.</p>";

        try {
            helper.setText(htmlMsg, true);
            helper.setTo(to);
            helper.setSubject("E-mail Validation");
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new ServerException("error_while_send_email");
        }

        mailSender.send(message);

        System.out.println("E-mail sent!");

        return code;
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public void validateEmail(ValidateEmailReqDTO dto) {
        var user = userRepository.findByEmail(dto.email());

        if (user == null) {
            throw new RecordNotFoundException();
        }

        if (user.getIsEmailValid()) {
            throw new BadRequestException("email_already_valid");
        }

        if (dto.code() != user.getEmailValidationCode()) {
            throw new BadRequestException("invalid_code");
        }

        user.setIsEmailValid(true);
        user.setEmailValidationCode(null);

        userRepository.save(user);
    }

    public void resendValidationCode(ResendValidationCodeReqDTO dto) {
        User user = userRepository.findByEmail(dto.email());

        if (user == null) {
            throw new RecordNotFoundException();
        }

        var code = sendEmailValidationCode(dto.email());

        user.setEmailValidationCode(code);

        userRepository.save(user);
    }
}
