package org.easymis.easycrm.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalParamInitedConfig {


    @Bean
    public SysServletContextListener sysServletContextListener(){
        return new SysServletContextListener();
    }
}
