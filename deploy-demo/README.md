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
    
    
# Deploy spring native with GraalVM
Requires the following dependencies and plugins, checkout pom-spring-native.xml

        <dependency>
            <groupId>org.springframework.experimental</groupId>
            <artifactId>spring-native</artifactId>
            <version>${spring-native.version}</version>
        </dependency>
        
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <image>
                <name>haodeng/${project.artifactId}</name>
                <builder>paketobuildpacks/builder:tiny</builder>
                <env>
                    <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
                </env>
            </image>
            ...
            
        <plugin>
            <groupId>org.springframework.experimental</groupId>
            <artifactId>spring-aot-maven-plugin</artifactId>
            <version>${spring-native.version}</version>

        <plugin>
            <groupId>org.hibernate.orm.tooling</groupId>
            <artifactId>hibernate-enhance-maven-plugin</artifactId>


Build image:
If you are using containers, on Mac, it is recommended to increase the memory allocated to Docker to at least 8G (and potentially to add more CPUs as well) since native-image compiler is a heavy process.

        # took a bit long time to build, 15 mins
        mvn -f pom-spring-native.xml spring-boot:build-image

Run:

    docker run --name deploy-demo-graalvm -p8080:8080 haodeng/deploy-demo-graalvm:latest
    
    # check the log: start took 0.141s only
    Started TestDemoApplication in 0.141 seconds (JVM running for 0.143)
 