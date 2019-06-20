package com.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * Author: Hardy
 * Date:   2019/6/14
 * Description:
 * - apollo重新初始化日志
 **/
public class LoggingReInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingReInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        reinitialize(environment);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public static void reinitialize(ConfigurableEnvironment environment) {
        log.info("Logging config preparing reinitialize");

        LoggingSystem loggingSystem = LoggingSystem.get(LoggingSystem.class.getClassLoader());
        String logConfig = environment.getProperty("logging.config");
        try {
            LogFile logFile = LogFile.get(environment);

            if (!StringUtils.isEmpty(logConfig))
                ResourceUtils.getURL(logConfig).openStream().close();

            loggingSystem.cleanUp();
            loggingSystem.beforeInitialize();
            loggingSystem.initialize(new LoggingInitializationContext(environment), logConfig, logFile);
        } catch (Exception e) {
            log.error("Logging config reinitialize error: " + e.getMessage());
        }
    }
}
