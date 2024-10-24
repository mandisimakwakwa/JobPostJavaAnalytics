# JobPostJavaAnalytics

## Description
A collection of microservice based java applications that use job post data from a csv to do data analytics by processing the data via kafka.

### Java Project Setup (linux)
1. Install JAVA openJDK 1.8
    ```
     sudo pacman -S jre8-openjdk-headless jre8-openjdk jdk8-openjdk openjdk8-doc openjdk8-src
    ```
2. Download Maven :
    http://maven.apache.org/download.cgi

3. Extract Maven :
     ```
     sudo tar xvzf apache-maven-x.x.x.tar.gz
     ```
4. Move Extracted Maven Folder :
     ```
     sudo mv apache-maven-x.x.x /opt/
     ```
5. Set MAVEN_HOME var :
     ```
     export MAVEN_HOME=/opt/apache-maven-x.x.x
     ```
6. Set MAVEN PATH var :
     ```
     export PATH=$PATH:$MAVEN_HOME/bin
     ```
7. Check maven installation completed :
     ```
     mvn --version
    ```

### Spring Setup (Linux)
1. Download spring-cli for version 2.1.8 from https://repo.spring.io/ui/native/snapshot/org/springframework/boot/spring-boot-cli/2.1.8.BUILD-SNAPSHOT/
2. Extract tar file
   ```
   sudo tar -xzf spring-boot-cli-2.1.8.BUILD-20190905.074229-53-bin.tar.gz
   ```
3. Move extracted file contents to opt/spring
4. Create SPRING_HOME env var
   ```
   export SPRING_HOME = /opt/spring
   ```
6. Add env var to path var
   ```
   export PATH=$PATH:$SPRING_HOME/bin
   ```
7. Create Service :
    ```
    spring init --build=maven --groupId=com.example.app ServiceName
    ```

### Kafka && Zookeeper Setup (Linux)
1. Download kafka via wget
   ```
   wget https://archive.apache.org/dist/kafka/3.2.1/kafka_2.13-3.2.1.tgz
   ```
2. Extract tar contents via
   ```
   sudo tar -xzf kafka_2.13-3.2.1.tgz
   ```
3. Rename the dir to kafka
   ```
   mv kafka_2.13-3.2.1 kafka
   ```
4. move the dir to /opt
   ```
   mv kafka /opt/
   ```
5. start zookeeper from home dir
   ```
   sudo sh /opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties
   ```
6. start kafka from home dir
   ```
   sudo sh /opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties
   ```