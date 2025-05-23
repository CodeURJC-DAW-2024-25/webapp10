package es.codeurjc.backend.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.security.jwt.AuthResponse;
import es.codeurjc.backend.security.jwt.AuthResponse.Status;
import es.codeurjc.backend.security.jwt.LoginRequest;
import es.codeurjc.backend.security.jwt.UserLoginService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private UserLoginService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        return userService.login(response, loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "RefreshToken", required = false) String refreshToken, HttpServletResponse response) {

        return userService.refresh(response, refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(response)));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestParam("username") String username,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("age") int age,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            HttpServletResponse response) {
    
        return userService.register(response, username, fullName, email, password, phone, age, profilePhoto);
    }
    

}