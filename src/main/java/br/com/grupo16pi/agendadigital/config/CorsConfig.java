package br.com.grupo16pi.agendadigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS para todos os endpoints
                .allowedOrigins("http://localhost:5500") // Origem permitida (seu front-end)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(true); // Permite credenciais (se necessário)
    }
}