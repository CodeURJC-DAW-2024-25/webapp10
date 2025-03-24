package es.codeurjc.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.codeurjc.backend.security.jwt.UnauthorizedHandlerJwt;
import es.codeurjc.backend.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	RepositoryUserDetailsService userDetailsService;

	@Autowired
	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// Verify credentials and determine if a user is valid.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http
				.securityMatcher("/api/v1/**")
				.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

		http.authorizeHttpRequests(authorize -> authorize
				// PRIVATE ENDPOINTS
				.requestMatchers(HttpMethod.POST, "/api/v1/artists").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/artists/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/artists/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/v1/tickets").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/users/me").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/v1/concerts").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/concerts/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/concerts/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/v1/concerts/*/image").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/v1/concerts/*/image").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/concerts/*/image").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/v1/users/me/image").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/image").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.PUT, "/api/v1/users/me/image").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/users/me/image").hasAnyRole("ADMIN", "USER")

				.requestMatchers("/v3/api-docs.yaml", "/swagger-ui/*", "/swagger-ui.html").permitAll()

				// PUBLIC ENDPOINTS
				.anyRequest().permitAll()

		);

		// Disable Form login Authentication
		http.formLogin(formLogin -> formLogin.disable());

		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf(csrf -> csrf.disable());

		// Disable Basic Authentication
		http.httpBasic(httpBasic -> httpBasic.disable());

		// Stateless session
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(authorize -> authorize
				// PUBLIC PAGES
				.requestMatchers("/").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/js/**").permitAll()
				.requestMatchers("/error").permitAll()
				.requestMatchers("/images/**").permitAll()
				.requestMatchers("/register/**").permitAll()
				.requestMatchers("/login/**").permitAll()
				.requestMatchers("/concert/**").permitAll()
				.requestMatchers("/user/new/**").permitAll()
				.requestMatchers("/moreConcerts", "/moreConcerts/**").permitAll()
				.requestMatchers("/concerts/*/image").permitAll()
				.requestMatchers("/download/tickets/**").permitAll()
				.requestMatchers("/infoGraphic/**").permitAll()
				.requestMatchers("/ticketsByConcert").permitAll()
				.requestMatchers("/v3/api-docs.yaml", "/v3/api-docs*", "/swagger-ui/*", "/swagger-ui.html").permitAll()

				// PRIVATE PAGES
				.requestMatchers("/editconcert/**").hasRole("ADMIN")
				.requestMatchers("/editArtist/**").hasRole("ADMIN")
				.requestMatchers("/deleteArtist/**").hasRole("ADMIN")
				.requestMatchers("/concert/delete/**").hasRole("ADMIN")
				.requestMatchers("/concert/purchasePage/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/userPage").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/edituser/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/newartist").hasAnyRole("ADMIN")
				.requestMatchers("/newconcert").hasAnyRole("ADMIN")

		)

				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginerror")
						.defaultSuccessUrl("/")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll())
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.accessDeniedPage("/error"));

		return http.build();
	}
}
