package es.codeurjc.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	RepositoryUserDetailsService userDetailsService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
    } 

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(authorize -> authorize
						// PUBLIC PAGES
						.requestMatchers("/").permitAll()
                        .requestMatchers("/css/**").permitAll()
						.requestMatchers("/images/**").permitAll() 
						.requestMatchers("/register/**").permitAll()
                        .requestMatchers("/concertInfo/**").permitAll()
						// PRIVATE PAGES
						.requestMatchers("/purchasePage").hasAnyRole("USER")
						.requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/userPage").hasAnyRole("USER")
                        .requestMatchers("/newArtist/*").hasAnyRole("ADMIN")
						.requestMatchers("/newConcert/*").hasAnyRole("ADMIN"))
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginerror")
						.defaultSuccessUrl("/",true)
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll());
     

                        
                        return http.build();
	}  
}
