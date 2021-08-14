# Spring Boot Executable JAR
Platform like Heroku or Cloud Foundry enable a developer to push a Spring Boot executable JAR, 
and provide any desired configuration settings (or simply accept the defaults), and the rest is handled by the platform.


## Spring Boot Executable JAR (fat jar)

    mvn clean package

Run

    java -jar ./target/deploy-demo-1.0-SNAPSHOT.jar
    
    # with prod profile
    java -jar -Dspring.profiles.active=prod deploy-demo-1.0-SNAPSHOT.jar
    # or
    java -jar deploy-demo-1.0-SNAPSHOT.jar --spring.profiles.active=prod
    
    
## "Fully Executable" Spring Boot JAR
Set executable true

        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <executable>true</executable>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        
        mvn clean package
        
Run

    ./target/deploy-demo-1.0-SNAPSHOT.jar
    
    # with prod profile
    ./target/deploy-demo-1.0-SNAPSHOT.jar --spring.profiles.active=prod

# Deploying Spring Boot Applications to Containers
## Build via IDE
Docker should be running

IntelliJ

Maven panel -> then expand Plugin -> choose spring-boot plug-in -> double-click the spring-boot:build-image

Check image

    docker images|grep deploy-demo

Remove the image

     docker image rm deploy-demo:1.0-SNAPSHOT
     
     deploy-demo                                     1.0-SNAPSHOT        bb10d91f5d09        41 years ago        252MB


## Build by cmd
Add image name to build plug-in, haodeng/${project.artifactId} links to your docker hub account name for later push to docker hub

    <build>
      <plug-ins>
        <plug-in>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plug-in</artifactId>
          <configuration>
            <image>
              <name>haodeng/${project.artifactId}</name>
            </image>
          </configuration>
        </plug-in>
      </plug-ins>
    </build>

Build
    
    mvn spring-boot:build-image
    
    docker images|grep deploy-demo
    haodeng/deploy-demo                             latest              2b85e92e762e        41 years ago        252MB

Run and check:

    docker run --name deploy-demo -p8080:8080 haodeng/deploy-demo:latest
    
    # run with prod profile
    docker run -e spring.profiles.active=prod --name deploy-demo -p8080:8080 haodeng/deploy-demo:latest
    
    curl http://localhost:8080/posts

Push to docker hub

    docker push haodeng/deploy-demo:latest
    
## Build images without docker running
Uses google jib plugin, no docker need to install and running

            <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>3.0.0</version>
                <configuration>
                    <to>haodeng/${project.artifactId}</to>
                </configuration>
            </plugin>

Build and publish to docker hub

    docker login
    
    mvn compile jib:build
This will build a image to local docker and push to a docker container registry (eg: docker hub by default)

Jib also can build your image directly to a Docker daemon. This uses the docker command line tool and requires that you have Docker available on your PATH.


    mvn compile jib:dockerBuild
This will only build a image to local docker

    
## Inspect a image

    brew install buildpacks/tap/pack
    
    pack inspect-image haodeng/deploy-demo:latest
    
    
