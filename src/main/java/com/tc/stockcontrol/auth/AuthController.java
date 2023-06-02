package com.tc.stockcontrol.auth;

import com.tc.stockcontrol.auth.dtos.LoginReqDTO;
import com.tc.stockcontrol.auth.dtos.LoginResDTO;
import com.tc.stockcontrol.auth.dtos.SignUpReqDTO;
import com.tc.stockcontrol.auth.dtos.SignUpResDTO;
import com.tc.stockcontrol.infra.security.TokenService;
import com.tc.stockcontrol.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody @Valid LoginReqDTO dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        var authentication = authenticationManager.authenticate(token);

        var tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResDTO(tokenJwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResDTO> signUp(@RequestBody @Valid SignUpReqDTO dto) {
        authService.signUp(dto);

        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        var authentication = authenticationManager.authenticate(token);

        var tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResDTO(tokenJwt));
    }
}
