package com.coa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String certificatePath = uploadPath("./generated-certificates");

        registry.addResourceHandler("/generated-certificates/**")
                .addResourceLocations(String.format("file:/%s/",certificatePath));

    }

    private String uploadPath(String directory){
        Path uploadPathDirectory = Paths.get(directory);
        return uploadPathDirectory.toFile().getAbsolutePath();
    }
}
