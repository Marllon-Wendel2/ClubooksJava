package com.example.Clubooks.infra.security;

import com.example.Clubooks.infra.security.dto.DataAutentication;
import com.example.Clubooks.user.dto.ApiResponse;
import com.example.Clubooks.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity doLogin(@RequestBody @Valid DataAutentication data) {
        System.out.println(data.password());
        var token = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        System.out.println(token);
        var authentication = manager.authenticate(token);
        System.out.println(authentication);

        var tokenJWT = tokenService.buildToken((User) authentication.getPrincipal());
        System.out.println(tokenJWT);

        return ResponseEntity.ok(new ApiResponse(true, tokenJWT));
    }
}
