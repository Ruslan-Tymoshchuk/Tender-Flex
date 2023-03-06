package pl.com.tenderflex.controller;

import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.AuthenticationDetails;
import pl.com.tenderflex.payload.request.AuthenticationRequest;
import pl.com.tenderflex.payload.response.AuthenticationResponse;
import pl.com.tenderflex.security.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> performAuthenticate(@RequestBody @Valid AuthenticationRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException("Email and password should not be empty");
        }
        AuthenticationDetails authenticationDetails = authenticationService.authenticate(request);
        ResponseCookie jwtCookie = authenticationDetails.getJwtCookie();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(authenticationDetails.getUserId(),
                authenticationDetails.getRole());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(authenticationResponse);
    }
}