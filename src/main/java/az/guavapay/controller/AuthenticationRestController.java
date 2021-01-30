package az.guavapay.controller;

import az.guavapay.model.user.User;
import az.guavapay.security.JwtAuthenticationRequest;
import az.guavapay.security.JwtAuthenticationResponse;
import az.guavapay.security.JwtTokenUtil;
import az.guavapay.service.user.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@CrossOrigin
@Api("endpoint for authenticate user")
public class AuthenticationRestController {
    private final UserServiceImpl userService;
    private final String tokenHeader;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRestController(UserServiceImpl userService,
                                        @Value("${jwt.header}") String tokenHeader,
                                        JwtTokenUtil jwtTokenUtil,
                                        AuthenticationManager authenticationManager,
                                        PasswordEncoder passwordEncoder
    ) {
        this.tokenHeader = tokenHeader;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @ApiOperation("create authenticate token for user")
    @PostMapping("${jwt.route.authentification.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException {
        try {
            String username = authenticationRequest.getUsername();
            String password = passwordEncoder.encode(authenticationRequest.getPassword());
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .build();
            user = userService.createUser(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()
            ));
            final String token = jwtTokenUtil.generateToken(user);
            user.setToken(token);
            userService.updateUser(user);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

    }
}
