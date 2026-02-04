package se.jensen.johan.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.johan.springboot.dto.LoginRequestDto;
import se.jensen.johan.springboot.dto.LoginResponseDto;
import se.jensen.johan.springboot.service.TokenService;

/**
 * Controller used for login.
 * Checks username and password and returns a JWT token.
 */
@RestController
@RequestMapping("/request-token")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Creates the controller with needed objects.
     *
     * @param authenticationManager used to check login details
     * @param tokenService          used to create JWT tokens
     */
    public AuthController(AuthenticationManager authenticationManager,
                          TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Logs in a user and returns a JWT token if the login is correct.
     *
     * @param loginRequest username and password from the client
     * @return response that contains a JWT token
     */
    @PostMapping
    public ResponseEntity<LoginResponseDto> token(
            @RequestBody LoginRequestDto loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        String token = tokenService.generateToken(auth);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}