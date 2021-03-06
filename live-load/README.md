Problem with Server Restarts
Typically, we need to restart the server to pick up the changes.
It tooks time to restart.

Thats where Spring Boot Developer Tools comes into picture.

    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-devtools</artifactId>
    	<scope>runtime</scope>
    	<optional>true</optional> 
    </dependency>
    
    Use optional dependency so it doesn't end up in the prod code.
    
These folders will not trigger reload by default

- /META-INF/maven
- /META-INF/resources
- /resources
- /static
- /public
- /templates

You can configure additional folders to scan in application.properties

    spring.devtools.restart.additional-paths = /path-to-folder
and folders to exclude.

    spring.devtools.restart.exclude=resources/**,public/** 

It’s also possible to disable server restarts:

    spring.devtools.restart.enabled=false
    
Demo
    
    # changes to properties and recompile will trigger a reload
    spring.devtools.restart.exclude=resources/**

Change greeting.name to hao deng
    
    curl http://localhost:8080/test
    It should return hao deng

Change the controller and recompile, re curl, changes should also be reloaded.