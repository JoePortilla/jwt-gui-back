package co.joeportilla.jwtguiback.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * CORS Configuration
 */
@Configuration
@EnableWebMvc
public class WebConfig {

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // Allows the backend to receive the headers which contain the authentication info.
        config.setAllowCredentials(true);
        // URL of the frontend
        config.addAllowedOrigin("http://localhost:4200");
        // Headers that the application must accept
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        // Methods that the application must accept
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
                                              ));
        // Time the CORS configuration is accepted
        config.setMaxAge(MAX_AGE);
        // Apply the configuration to all my routes
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);

        // FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // Put this bean at the lowest position to be executed before any Spring Bean
        // should be set order to -100 because we need to CorsFilter before SpringSecurityFilter
        // bean.setOrder(-102);
        // return bean;

        // CorsFilter corsFilter = new CorsFilter(source);
        // FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        //
        // // Set the order for the CorsFilter
        // bean.setOrder(CORS_FILTER_ORDER);
        //
        // return bean;

    }

}