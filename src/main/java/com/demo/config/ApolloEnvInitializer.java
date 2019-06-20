package com.demo.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * Author: Hardy
 * Date:   2019/6/14
 * Description:
 * - 读取配置将设置apollo环境
 **/
public class ApolloEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        initialize(environment);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private void initialize(ConfigurableEnvironment environment) {
        String value = environment.getProperty("apollo.env");
        if (!StringUtils.isEmpty(value))
            System.setProperty("env", value);
    }
}
