# JobPostJavaAnalytics

## Description
A java application that uses job post data from a csv to do data analytics by processing the data via kafka

### Java Project Setup
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

