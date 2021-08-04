FROM tomcat:9-jdk16-openjdk-buster
RUN rm -rf /usr/local/tomcat/webapps/*
WORKDIR /usr/local/tomcat/webapps/
COPY ./target/LibraryManagementSystem.war /usr/local/tomcat/webapps/LibraryManagementSystem.war
RUN sh -c 'touch /usr/local/tomcat/webapps/LibraryManagementSystem.war'
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/LibraryManagementSystem.war"]
#CMD ["catalina.sh","run"]
#ADD target/LibraryManagementSystem.war /LibraryManagementSystem.war
#EXPOSE 8080
#ENTRYPOINT ["java","-war","/LibraryManagementSystem.war"]