package br.com.grupo16pi.agendadigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS para todos os endpoints
                .allowedOrigins("http://localhost:8080", "http://100.105.137.90:8080", "http://192.168.80.238:8080", "http://100.96.166.111:8080", "http://100.81.23.53:8080") // Origem permitida (seu front-end)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(true) // Permite credenciais (se necessário)
                .maxAge(3600); // Tempo máximo de cache do CORS (em segundos)
    }
}

//.allowedOrigins("http://localhost:5500", "http://192.168.80.238:5500", "http://100.96.166.111:5500", "http://100.81.23.53:5500") // Origem permitida (seu front-end)