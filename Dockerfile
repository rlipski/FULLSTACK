FROM tomcat:8.0.20-jre8
COPY fs-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/fs.war
