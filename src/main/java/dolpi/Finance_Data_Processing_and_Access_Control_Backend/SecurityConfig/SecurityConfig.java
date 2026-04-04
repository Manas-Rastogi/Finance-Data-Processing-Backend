package dolpi.Finance_Data_Processing_and_Access_Control_Backend.SecurityConfig;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.JwtFilter.JwtFilter;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private MyUserDetails userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/transactions")
                        .hasRole("admin")

                        .requestMatchers(HttpMethod.GET, "/transactions")
                        .hasAnyRole("admin", "analyst")

                        .requestMatchers(HttpMethod.GET, "/transactions/*")
                        .hasAnyRole("admin", "analyst")

                        .requestMatchers(HttpMethod.PATCH, "/transactions/*")
                        .hasRole("admin")

                        .requestMatchers(HttpMethod.DELETE, "/transactions/*")
                        .hasRole("admin")

                        .requestMatchers(HttpMethod.GET, "/dashboard/summary")
                        .hasAnyRole("admin", "analyst", "viewer")


                        .requestMatchers(HttpMethod.GET, "/dashboard/category-wise")
                        .hasAnyRole("admin", "analyst")


                        .requestMatchers(HttpMethod.GET, "/dashboard/trends")
                        .hasAnyRole("admin", "analyst")

                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
