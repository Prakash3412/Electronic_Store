package com.lcwd.electronics.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean   // now it is bean wa can Autowired where we want
    public ModelMapper mapper()
    {
        return new ModelMapper();
    }
}
