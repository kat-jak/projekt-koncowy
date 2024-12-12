import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // Publiczne strony
                .anyRequest().authenticated() // Wymagaj logowania dla pozostałych stron
                .and()
                .formLogin()
                .loginPage("/login") // Strona logowania
                .defaultSuccessUrl("/patients", true) // Przekierowanie po sukcesie
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // Przekierowanie po wylogowaniu
                .permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
