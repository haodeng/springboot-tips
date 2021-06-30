# Logging
Springboot auto-configuration default logging is Logback

## Inject logger @Slf4j via Lombok
Use @Slf4j, this equals to

    private static final Logger log = LoggerFactory.getLogger(LoggingDemo.class);

Use log.xxx()

    @Slf4j
    @Component
    public class LoggingDemo {
    
        @PostConstruct
        void init() {
            log.info("start logging demo");
        }
        ...
        
Output:
       
    2021-06-30 12:25:36.553  INFO 22131 --- [           main] demo.hao.LoggingDemo                     : start logging demo
    ...
    
## Customize console log
Two ways:
1. via properties

        logging.pattern.console=%d{yyyy-MM-dd HH:mm} - %logger{36} - %msg%n
        logging.level.demo.hao=DEBUG

2. Customize logback.xml
Either overwrite logback.xml, or add this to properties and create logback.console.xml

        logging.config = classpath:logback.console.xml
        
## Logging for active profile
Set profile to dev for example:

    spring.profiles.active=dev
    logging.config = classpath:logback.profile.xml

In logback.profile.xml:

    <springProfile name="local | dev">
        <logger name="org.springframework" level="DEBUG" additivity="false">
            <appender-ref ref="CONSOLE" />
        </logger>
        <root level="DEBUG">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

    <springProfile name="prod">
        <root level="INFO">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

## Logging to multi files

    logging.config = classpath:logback.multi-files.xml

Check the config in logback.multi-files.xml