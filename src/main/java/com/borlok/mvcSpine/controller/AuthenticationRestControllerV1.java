package com.borlok.mvcSpine.controller;

import com.borlok.mvcSpine.dto.AuthenticationRequestDTO;
import com.borlok.mvcSpine.dto.UserDto;
import com.borlok.mvcSpine.model.Role;
import com.borlok.mvcSpine.model.User;
import com.borlok.mvcSpine.model.UserType;
import com.borlok.mvcSpine.security.JwtAuthenticationException;
import com.borlok.mvcSpine.security.JwtTokenProvider;
import com.borlok.mvcSpine.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Erofeevskiy Yuriy on 29.05.2024
 */



@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Value("${jwt.header}")
    private String tokenHeader;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<Map<Object, Object>> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) {
        try {
            String phoneNumber = authenticationRequest.phoneNumber();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, authenticationRequest.password()));
            User user = userService.findByPhoneNumber(phoneNumber);
            List<Role> roles = user.getRoles();
            String returnRole = roles.stream().map(x -> UserType.valueOf(x.getName().substring(5)).ordinal()).max(Integer::compareTo).map(x -> UserType.values()[x].name()).orElseThrow(() -> {
                throw new JwtAuthenticationException("The role isn't exist");
            });
            String token = jwtTokenProvider.createToken(phoneNumber, new ArrayList<>(user.getRoles()));

            Map<Object, Object> model = new HashMap<>();
            model.put("phoneNumber", "+7" + phoneNumber);
            model.put("token", token);
            model.put("role", returnRole);
            model.put("customerId", user.getCustomerId());

            return ok(model);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid email/password combination");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getAuthenticatedUser(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);

        User user = userService.findByPhoneNumber(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
