package pm.group01.courseproject;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@SpringBootApplication
public class CourseprojectApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CourseprojectApplication.class);
//        SpringApplication.run(CourseprojectApplication.class, args);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }

}
