package org.example.warmtavern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WarmTavernApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(WarmTavernApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/AdminPage").setViewName("authPages/AdminPage");
        registry.addViewController("/AuthPage").setViewName("authPages/AuthPage");
        registry.addViewController("/ProfilePage").setViewName("authPages/ProfilePage");
        registry.addViewController("/RecoveryPage").setViewName("resetPages/RecoveryPage");
        registry.addViewController("/ResetPage").setViewName("resetPages/ResetPage");
        registry.addViewController("/RegPage").setViewName("authPages/RegPage");

    }
}
