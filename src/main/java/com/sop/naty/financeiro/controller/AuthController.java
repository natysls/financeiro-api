package com.sop.naty.financeiro.controller;

import com.sop.naty.financeiro.config.JwtUtil;
import com.sop.naty.financeiro.config.TokenBlacklist;
import com.sop.naty.financeiro.dto.DespesaDTO;
import com.sop.naty.financeiro.entity.Usuario;
import com.sop.naty.financeiro.record.AuthRequest;
import com.sop.naty.financeiro.record.AuthResponse;
import com.sop.naty.financeiro.repository.UsuarioRepository;
import com.sop.naty.financeiro.service.JwtUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final TokenBlacklist tokenBlacklist;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          JwtUserDetailsService uds, UsuarioRepository repo, PasswordEncoder encoder, TokenBlacklist tokenBlacklist) {
        this.authManager = authManager; this.jwtUtil = jwtUtil; this.userDetailsService = uds;
        this.userRepository = repo; this.passwordEncoder = encoder;
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username(), req.password())
            );
            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(ud);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.blacklist(token);
        }
        return ResponseEntity.ok().build();
    }

}

