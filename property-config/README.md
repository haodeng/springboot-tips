
# @Value
GreetingControllerV1 inject values from properties file

    curl http://localhost:8080/greeting/v1
    curl http://localhost:8080/greeting/v1/welcome


problem:
If greeting.name not defined in properties:

java.lang.IllegalArgumentException: Could not resolve placeholder 'greeting.name' in value "greeting.welcome: ${greeting.name} welcome default"

# @ConfigurationProperties

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    
Wrap up properties in Greeting:    

    @ConfigurationProperties(prefix = "greeting")
    public class Greeting {
    ..


Enable property scan

    @ConfigurationPropertiesScan

Test:
    
    curl http://localhost:8080/greeting/v2
    curl http://localhost:8080/greeting/v2/welcome

If greeting.name not defined in properties, the app still can start. 
http://localhost:8080/greeting returns empty.

# Using Profiles to configure environment specific configuration
Profile is a key to identify an environment, eg: prod, dev

application-dev.properties is for dev profile

Two ways to enable the dev profile
* via vm option: -Dspring.profiles.active=dev
* add spring.profiles.active=dev to application.properties

Test:

    curl http://localhost:8080/greeting/v2
    returns: hao dev
    

# Check properties from actuator/env
Enable actuator

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    management.endpoints.web.exposure.include=*
    management.endpoint.health.show-details=always
    
http://localhost:8080/actuator/env

    {
          "name": "Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'",
          "properties": {
            "greeting.name": {
              "value": "hao",
              "origin": "class path resource [application.properties] - 1:15"
            },
            "greeting.welcome": {
              "value": "hao welcome",
              "origin": "class path resource [application.properties] - 2:18"
            },

Run app by adding --greeting.name=test to program arguments

    curl http://localhost:8080/greeting/v2/
    should return "test"

http://localhost:8080/actuator/env

    {
          "name": "commandLineArgs",
          "properties": {
            "greeting.name": {
              "value": "test"
            }
          }
          
# Internationalization (i18n)
Spring boot provides excellent support for message localization using it’s auto-configuration feature. All we have to do is provide locale specific resource bundle properties and MessageSource is automatically configured for us.

Presence of resource bundle with name messages.properties or name configured using spring.messages.basename property.

Resource bundle messages:
    
    message.properties
    message_da.properties
    message_es.properties

Test:

    curl -H "Accept-Language:da-DK" http://localhost:8080/locale-hi
    
    curl -H "Accept-Language:es-ES" http://localhost:8080/locale-hi
