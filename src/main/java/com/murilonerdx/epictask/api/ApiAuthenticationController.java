package com.murilonerdx.epictask.api;

import com.murilonerdx.epictask.entities.Login;
import com.murilonerdx.epictask.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthenticationController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    @PostMapping()
    public ResponseEntity<Object> auth(@RequestBody @Valid Login login){
        Map<Object, Object> model = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        try{
            Authentication authenticate = authManager.authenticate(authentication);
            String token = tokenService.getToken(authenticate);

            model.put("email", login.getUsername());
            model.put("token", token);
            return ResponseEntity.ok(model);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
