package es.codeurjc.backend.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.dto.user.NewUserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserLoginService {

	private static final Logger log = LoggerFactory.getLogger(UserLoginService.class);

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtTokenProvider jwtTokenProvider;

	public UserLoginService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Autowired
	private es.codeurjc.backend.service.UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public ResponseEntity<AuthResponse> login(HttpServletResponse response, LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String username = loginRequest.getUsername();
		UserDetails user = userDetailsService.loadUserByUsername(username);

		HttpHeaders responseHeaders = new HttpHeaders();
		var newAccessToken = jwtTokenProvider.generateAccessToken(user);
		var newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

		response.addCookie(buildTokenCookie(TokenType.ACCESS, newAccessToken));
		response.addCookie(buildTokenCookie(TokenType.REFRESH, newRefreshToken));

		AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
				"Auth successful. Tokens are created in cookie.");
		return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
	}

	public ResponseEntity<AuthResponse> refresh(HttpServletResponse response, String refreshToken) {
		try {
			var claims = jwtTokenProvider.validateToken(refreshToken);
			UserDetails user = userDetailsService.loadUserByUsername(claims.getSubject());

			var newAccessToken = jwtTokenProvider.generateAccessToken(user);
			response.addCookie(buildTokenCookie(TokenType.ACCESS, newAccessToken));

			AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
					"Auth successful. Tokens are created in cookie.");
			return ResponseEntity.ok().body(loginResponse);

		} catch (Exception e) {
			log.error("Error while processing refresh token", e);
			AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.FAILURE,
					"Failure while processing refresh token");
			return ResponseEntity.ok().body(loginResponse);
		}
	}

	public String logout(HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		response.addCookie(removeTokenCookie(TokenType.ACCESS));
		response.addCookie(removeTokenCookie(TokenType.REFRESH));

		return "logout successfully";
	}

	private Cookie buildTokenCookie(TokenType type, String token) {
		Cookie cookie = new Cookie(type.cookieName, token);
		cookie.setMaxAge((int) type.duration.getSeconds());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		return cookie;
	}

	private Cookie removeTokenCookie(TokenType type) {
		Cookie cookie = new Cookie(type.cookieName, "");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		return cookie;
	}

	public ResponseEntity<AuthResponse> register(HttpServletResponse response, LoginRequest registerRequest) {
    try {
        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(AuthResponse.Status.ERROR, "Missing username or password"));
        }

        if (userService.userExists(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(AuthResponse.Status.ERROR, "User already exists"));
        }

        NewUserDTO newUserDTO = new NewUserDTO(
            null,
            registerRequest.getUsername(),
            null,
            null,
            registerRequest.getPassword(),
            0,
            null
        );

        userService.UserCreationReplacement(null, newUserDTO, false, passwordEncoder);

        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, "User registered successfully"));

    } catch (Exception e) {
        return ResponseEntity.internalServerError()
            .body(new AuthResponse(AuthResponse.Status.ERROR, "Error during registration: " + e.getMessage()));
    }
}

}
